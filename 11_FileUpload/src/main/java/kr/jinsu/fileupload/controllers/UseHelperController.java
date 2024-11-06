package kr.jinsu.fileupload.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinsu.fileupload.helpers.FileHelper;
import kr.jinsu.fileupload.helpers.WebHelper;
import kr.jinsu.fileupload.models.UploadItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;


@Slf4j
@Controller
public class UseHelperController {
 
    @Autowired
    private WebHelper webHelper;

    @Autowired
    private FileHelper fileHelper;

    /** 업로드 된 파일이 저장될 경로 (application.properties 로부터 읽어롬) */
    @Value("${upload.dir}")
    private String uploadDir;

    /** 업로드 된 파일이 노출될 URL 경로 (application.properties 로부터 읽어롬) */
    @Value("${upload.url}")
    private String uploadUrl;

    @GetMapping("/use_helper/upload")
    public String upload() {
        return "use_helper/upload";
    }
 
    @PostMapping("/use_helper/upload_ok")
    public String uploadOk(Model model,
    @RequestParam(value ="profile-photo", required=false) MultipartFile profilePhoto){
        
        UploadItem item = null;
        
        try {
            item = fileHelper.saveMultipartFile(profilePhoto);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        // 생성할 썸네일의 크기와 크롭 여부 지정
        int width = 640;
        int height = 640;
        boolean crop = true;

        if(item.getContentType().indexOf("image") > -1) {
            /** 1) 썸네일 생성 정보를 로그로 기록하기 */
            log.debug(String.format("[Thumbnail] path: %s, size: %dx%d, crop: %s",
                    item.getFilePath(), width, height, String.valueOf(crop)));

            /** 2) 저장될 썸네일 이미지의 경로 문자열 만들기 */
            File loadFile = new File(this.uploadDir, item.getFilePath()); // 원본파일의 전체경로 --> 업로드 폴더(상수값) + 파일명
            String dirPath = loadFile.getParent(); // 전체 경로에서 파일이 위치한 폴더 경로 분리
            String fileName = loadFile.getName(); //전체 경로에서 파일 이름만 분리
            int p = fileName.lastIndexOf(".");
            String name = fileName.substring(0, p); //파일명 분리
            String ext = fileName.substring( p + 1 ); //확장자 분리

            // 최종 파일이름을 구성한다. --> 원본이름 + 요청된 사이즈
            String thumbName = name + "_" + width + "x" + height + "." + ext;

            File f = new File(dirPath, thumbName); // 생성될 썸네일 패일 객체 --> 업로드 폴더 + 썸네일 이름
            String saveFile = f.getAbsolutePath(); // 생성될 썸네일 파일 객체로부터 절대경로 추출 ( 리턴할 값 )

            // 생성될 썸네일 이미지의 경로를 로그로 기록
            log.debug(String.format("[Thumbnail] saveFile: %s", saveFile));

            /** 썸네일 이미지 생성하고 최종 경로 리턴 */
            // 해당 경로에 이미지가 없는 경우만 수행
            if (!f.exists()) {
                //원본 이미지 가져오기
                Builder<File> builder = Thumbnails.of(loadFile);
                // 이미지 크롭 여부 파라미터에 따라 크롭 옵션을 지정한다.
                if(crop == true) {
                    builder.crop(Positions.CENTER);
                }

                builder.size(width, height); //축소할 사이즈 지정
                builder.useExifOrientation(true); //세로로 촬영된 사진을 회전시킴
                builder.outputFormat(ext); // 파일의 학장명 지정

                try {
                    builder.toFile(saveFile);
                } catch (IOException e) {
                    log.error("썸네일 이미지 생성에 실패했습니다.", e);
                    webHelper.serverError(e);
                    return null;

                } //저장할 파일경로 지정
            }

            String thumbnailPath = null;
            saveFile = saveFile.replace("\\", "/");
            if(saveFile.substring(0,1).equals("/")) {
                // 맥, 리눅스용 경로처리
                thumbnailPath = saveFile.replace(uploadDir, "");
            } else {
                // 윈도우용 경로 처리 -- > 설정 파일에 명시한 첫 글자 (/)를 제거해야함
                thumbnailPath = saveFile.replace(uploadDir.substring(1), "");
            }

            String thumbnailUrl = String.format("%s%s", uploadUrl, thumbnailPath);

            item.setThumbnailPath(thumbnailPath);
            item.setThumbnailUrl(thumbnailUrl);
        }
        
        // 업로드 정보를 View로 전달한다.
        model.addAttribute("item", item);

        // 뷰 호출
        return "use_helper/upload_ok";
    }
    
}

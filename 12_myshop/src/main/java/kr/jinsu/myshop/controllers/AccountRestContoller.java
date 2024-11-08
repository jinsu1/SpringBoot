package kr.jinsu.myshop.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.jinsu.myshop.helpers.RestHelper;
import kr.jinsu.myshop.helpers.FileHelper;
import kr.jinsu.myshop.models.Members;
import kr.jinsu.myshop.models.UploadItem;
import kr.jinsu.myshop.services.MembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
public class AccountRestContoller {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private MembersService membersService;

    @GetMapping("/api/account/id_unique_check")
    public Map<String, Object> idUniqueCheck( @RequestParam("user_id") String userId) {
        try {
            membersService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        return restHelper.sendJson();
    }
    
    @GetMapping("/api/account/email_unique_check")
    public Map<String, Object> emailUniqueCheck( @RequestParam("email") String email) {
        try {
            membersService.isUniqueEmail(email);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        return restHelper.sendJson();
    }

    @PostMapping("/api/account/join")
    public Map<String, Object> join(
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("birthday") String birthday,
        @RequestParam("gender") String gender,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam(value="photo", required = false) MultipartFile photo
    ) {
        /** 1) 입력값에 대한 유효성 검사 생략 */

        /** 2) 아이디 중복 검사 */
        try {
            membersService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        /** 3) 이메일 중복 검사 */
        try {
            membersService.isUniqueEmail(email);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        /** 3) 업로드 받기 */
        UploadItem uploadItem = null;

        try {
            uploadItem = fileHelper.saveMultipartFile(photo);
        } catch (NullPointerException e) {
            //업로드 항목이 없어도 에러는 아님
        } catch (Exception e) {
            //업로드 항목이 있고 에러발생 시
            return restHelper.serverError(e);
        }

        /** 4) 정보를 Service에 전달하기 위한 객체 구성 */
        Members members = new Members();
        members.setUserId(userId);
        members.setUserPw(userPw);
        members.setUserName(userName);
        members.setEmail(email);
        members.setPhone(phone);
        members.setBirthday(birthday);
        members.setGender(gender);
        members.setPostcode(postcode);
        members.setAddr1(addr1);
        members.setAddr2(addr2);

        // 업로드 된 이미지의 이름을 표시할 필요가 없으면 저장된 경로만 DB에 저장
        if(uploadItem != null) {
            members.setPhoto(uploadItem.getFilePath());
        }

        /** 5) DB에 저장 */
        try {
            membersService.addItem(members);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        return restHelper.sendJson();
    }

    @PostMapping("/api/account/find_id")
    public Map<String, Object> findId(
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email) {
        
        Members input = new Members();
        input.setUserName(userName);
        input.setEmail(email);

        Members output = null;

        try {
            output = membersService.findId(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", output.getUserId());
        
        return restHelper.sendJson(data);
    }
}

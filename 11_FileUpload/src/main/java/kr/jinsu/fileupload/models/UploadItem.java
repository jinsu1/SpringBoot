package kr.jinsu.fileupload.models;

import lombok.Data;

@Data
public class UploadItem {
    private String fieldName;   //input type="file"의 name속성
    private String originName;  //원본 파일 이름
    private String contentType; //파일 형식
    private long fileSize;  //용량
    private String filePath;    //서버상의 파일 경로
    private String fileUrl; // 서버상의 파일 url
    private String thumbnailPath; // 썸네일 이미지 경로
    private String thumbnailUrl; // 썸네일 이미지 URL
}

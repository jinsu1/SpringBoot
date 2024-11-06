package kr.jinsu.fileupload.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AjaxUploadController {
    @GetMapping("/ajax/upload")
    public String upload() {
        return "ajax/upload";
    }
    @GetMapping("/ajax/upload_multi")
    public String uploadMulti(){
        return "ajax/upload_multi";
    }
    
}

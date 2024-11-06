package kr.jinsu.fileupload.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.jinsu.fileupload.helpers.FileHelper;
import kr.jinsu.fileupload.helpers.RestHelper;
import kr.jinsu.fileupload.models.UploadItem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UploadRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @PostMapping("/ajax/upload_ok")
    public Map<String, Object> uploadOK(Model model,
            @RequestParam(value="profile-photo", required=false) MultipartFile profilePhoto){
        
        UploadItem item = null;

        try {
            item = fileHelper.saveMultipartFile(profilePhoto);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", item);

        return restHelper.sendJson(data);
    }
    
    @PostMapping("/ajax/upload_multi_ok")
    public Map<String, Object> uploadMultiOk (Model model,
            @RequestParam(value = "profile-photo", required = false) MultipartFile[] profilePhoto ) {
            
        List<UploadItem> itemList = null;
        
        try {
            itemList = fileHelper.saveMultipartFile(profilePhoto);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", itemList);

        return restHelper.sendJson(data);
    }
    
}

package kr.jinsu.myshop.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import kr.jinsu.myshop.helpers.RestHelper;
import kr.jinsu.myshop.services.MembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AccountRestContoller {
    @Autowired
    private RestHelper restHelper;

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

    
}

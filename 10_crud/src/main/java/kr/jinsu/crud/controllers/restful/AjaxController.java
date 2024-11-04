package kr.jinsu.crud.controllers.restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AjaxController {

    @GetMapping("/dept_item")
    public String deptItem() {
        return "dept_item.html";
    }

    @GetMapping("/dept_list")
    public String deptList() {
        return "dept_list.html";
    }

    @GetMapping("/dept_view")
    public String deptView() {
        return "dept_view.html";
    }
    
}

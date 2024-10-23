package kr.jinsu.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.helpers.WebHelper;
import kr.jinsu.database.models.Department;
import kr.jinsu.database.services.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
public class DepartmentController {
    /** 학과 관리 서비스 객체 주입 */
    @Autowired
    private DepartmentService departmentService;

    /** WebHelper 주입 */
    @Autowired
    private WebHelper webHelper;

    /**
     * 학과 목록 화면
     * @param model 모델
     * @return  학과 목록 화면을 구현한 View 경로
     */
    @GetMapping("/department")
    public String index(Model model) {
        List<Department> departments = null;

        try {
            departments = departmentService.getList(null);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
            return null;
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("departments", departments);
        return "/department/index";
    }

    @GetMapping("/department/detail/{deptno}")
    public String detail(Model model,
        @PathVariable("deptno") int deptno) {
        
        //조회 조건에 사용할 변수를 Beans에 저장
        Department params = new Department();
        params.setDeptno(deptno);

        //조회 결과를 저장할 객체 선언
        Department department = null;

        try {
            //데이터 조회
            department = departmentService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        //View에 데이터 전달
        model.addAttribute("department", department);
        return "/department/detail";
    }
    
}

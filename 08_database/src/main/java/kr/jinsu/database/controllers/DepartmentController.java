package kr.jinsu.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.helpers.Pagination;
import kr.jinsu.database.helpers.WebHelper;
import kr.jinsu.database.models.Department;
import kr.jinsu.database.services.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;







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
    @GetMapping({"/", "/department"})
    public String index(Model model,
        // 검색어 파라미터 (페이지 처음 열릴 때는 값 없음, 필수(required)가 아님)
        @RequestParam(value = "keyword", required = false) String keyword,
        // 페이지 구현에서 사용할 현재 페이지 번호
        @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int totalCount = 0; // 전체 게시글 수
        int listcount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; //한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        //조회 조건에 사용할 객체
        Department input = new Department();
        input.setDname(keyword);
        input.setLoc(keyword);

        List<Department> departments = null;

        try {
            //전체 게시글 수 조회
            totalCount = departmentService.getCount(input);
            //페이지 번호 계산 ==> 계산 결과가 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listcount, pageCount);

            //SQL의 limit절에서 사용될 값을 Beans의 static 변수에 저장
            Department.setOffset(pagination.getOffset());
            Department.setListCount(pagination.getListCount());

            departments = departmentService.getList(input);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
            return null;
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("departments", departments);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

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
    
    /**
     * 학과 등록 화면
     * @return  학과 등록 화면을 구현한 View 경로
     */
    @GetMapping("department/add")
    public String add() {
        return "/department/add";
    }
    
    @ResponseBody
    @PostMapping("/department/add_ok")
    public void addOk(HttpServletRequest request,
        @RequestParam("dname") String dname,
        @RequestParam("loc") String loc) {

        //정상적인 경로로 접근한 경우 이전 페이지 주소는
        //1) http://localhost/department
        //2) http://localhost/department/detail/학과번호
        //두 가지 경우가 있다.
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/department")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        //저장한 값들을 Beans에 담는다.
        Department department = new Department();
        department.setDname(dname);
        department.setLoc(loc);

        try {
            departmentService.addItem(department);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //insert, update, delete 처리를 수행하는 경우에는 리다이렉트로 이동
        //insert 결과를 확인할 수 있는 상세 페이지로 이동
        // 상세 페이지에 조회 대상의 pk값을 전달해야한다.
        webHelper.redirect("/department/detail/" + department.getDeptno(), "등록되었습니다.");
    }

    @ResponseBody
    @GetMapping("/department/delete/{deptno}")
    public void delete ( HttpServletRequest request,
        @PathVariable("deptno") int deptno) {
        
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/department")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        Department department = new Department();
        department.setDeptno(deptno);

        try {
            departmentService.deleteItem(department);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        webHelper.redirect("/department", "삭제되었습니다.");
    }

    /**
     * 학과 수정 페이지
     * @param model - Model 객체
     * @param deptno - 학과 번호
     * @return  View 페이지의 경로
     */
    @GetMapping("/department/edit/{deptno}")
    public String edit(Model model,
        @PathVariable("deptno") int deptno) {
        
        //파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색 조건으로 사용하기 위함
        Department params = new Department();
        params.setDeptno(deptno);

        //수정할 데이터의 현재 값을 조회
        Department department = null;

        try {
            department = departmentService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //View에 데이터 전달
        model.addAttribute("department", department);


        return "/department/edit";
    }

    @ResponseBody
    @PostMapping("/department/edit_ok/{deptno}")
    public void edit_ok(
        @PathVariable("deptno") int deptno,
        @RequestParam("dname") String dname,
        @RequestParam("loc") String loc) {
        
        Department department = new Department();
        department.setDeptno(deptno);
        department.setDname(dname);
        department.setLoc(loc);

        try {
            departmentService.editItem(department);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/department/detail/" + department.getDeptno(), "수정되었습니다.");
    }
}

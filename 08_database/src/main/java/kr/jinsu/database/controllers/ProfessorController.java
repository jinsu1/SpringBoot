package kr.jinsu.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.helpers.WebHelper;
import kr.jinsu.database.models.Professor;
import kr.jinsu.database.services.ProfessorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;




@Controller
public class ProfessorController {
    /** 교수 관리 서비스 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    /** WebHelper 주입 */
    @Autowired
    private WebHelper webHelper;

    /**
     * 교수 목록 화면
     * @param model 모델
     * @return  교수 목록 화면을 구현한 View 경로
     */
    @GetMapping("/professor")
    public String index(Model model) {
        List<Professor> professors = null;

        try {
            professors = professorService.getList(null);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
            return null;
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("professors", professors);
        return "/professor/index";
    }

    @GetMapping("/professor/detail/{profno}")
    public String detail(Model model,
        @PathVariable("profno") int profno) {
        
        //조회 조건에 사용할 변수를 Beans에 저장
        Professor params = new Professor();
        params.setProfno(profno);

        //조회 결과를 저장할 객체 선언
        Professor professor = null;

        try {
            //데이터 조회
            professor = professorService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        //View에 데이터 전달
        model.addAttribute("professor", professor);
        return "/professor/detail";
    }
    
    /**
     * 교수 등록 화면
     * @return  교수 등록 화면을 구현한 View 경로
     */
    @GetMapping("professor/add")
    public String add() {
        return "/professor/add";
    }
    
    @ResponseBody
    @PostMapping("/professor/add_ok")
    public void addOk(HttpServletRequest request,
        @RequestParam("name") String name,
        @RequestParam("userid") String userid,
        @RequestParam("position") String position,
        @RequestParam("sal") int sal,
        @RequestParam("hiredate") String hiredate,
        @RequestParam(value = "comm", required=false) Integer comm,
        @RequestParam("deptno") int deptno
        ) {

        //정상적인 경로로 접근한 경우 이전 페이지 주소는
        //1) http://localhost/professor
        //2) http://localhost/professor/detail/학과번호
        //두 가지 경우가 있다.
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/professor")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        //저장한 값들을 Beans에 담는다.
        Professor professor = new Professor();
        professor.setName(name);
        professor.setUserid(userid);
        professor.setPosition(position);
        professor.setSal(sal);
        professor.setHiredate(hiredate);
        if(comm != null) {
            professor.setComm(comm);
        }
        professor.setComm(null);
        professor.setDeptno(deptno);

        try {
            professorService.addItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //insert, update, delete 처리를 수행하는 경우에는 리다이렉트로 이동
        //insert 결과를 확인할 수 있는 상세 페이지로 이동
        // 상세 페이지에 조회 대상의 pk값을 전달해야한다.
        webHelper.redirect("/professor/detail/" + professor.getProfno(), "등록되었습니다.");
    }

    @ResponseBody
    @GetMapping("/professor/delete/{profno}")
    public void delete ( HttpServletRequest request,
        @PathVariable("profno") int profno) {
        
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/professor")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        Professor professor = new Professor();
        professor.setProfno(profno);

        try {
            professorService.deleteItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        webHelper.redirect("/professor", "삭제되었습니다.");
    }

     /**
     * 교수 수정 페이지
     * @param model - Model 객체
     * @param profno - 교수 번호
     * @return  View 페이지의 경로
     */
    @GetMapping("/professor/edit/{profno}")
    public String edit(Model model,
        @PathVariable("profno") int profno) {
        
        //파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색 조건으로 사용하기 위함
        Professor params = new Professor();
        params.setProfno(profno);

        //수정할 데이터의 현재 값을 조회
        Professor professor = null;

        try {
            professor = professorService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //View에 데이터 전달
        model.addAttribute("professor", professor);


        return "/professor/edit";
    }

    @ResponseBody
    @PostMapping("/professor/edit_ok/{profno}")
    public void edit_ok(
        @PathVariable("profno") int profno,
        @RequestParam("name") String name,
        @RequestParam("userid") String userid,
        @RequestParam("position") String position,
        @RequestParam("sal") int sal,
        @RequestParam("hiredate") String hiredate,
        @RequestParam("comm") int comm,
        @RequestParam("deptno") int deptno) {
        
        Professor professor = new Professor();
        professor.setProfno(profno);
        professor.setName(name);
        professor.setUserid(userid);
        professor.setPosition(position);
        professor.setSal(sal);
        professor.setHiredate(hiredate);
        professor.setComm(comm);
        professor.setDeptno(deptno);

        try {
            professorService.editItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/professor/detail/" + professor.getProfno(), "수정되었습니다.");
    }
}

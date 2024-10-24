package kr.jinsu.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.helpers.WebHelper;
import kr.jinsu.database.models.Student;
import kr.jinsu.database.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;




@Controller
public class StudentController {
    /** 학생 관리 서비스 객체 주입 */
    @Autowired
    private StudentService studentService;

    /** WebHelper 주입 */
    @Autowired
    private WebHelper webHelper;

    /**
     * 학생 목록 화면
     * @param model 모델
     * @return  학생 목록 화면을 구현한 View 경로
     */
    @GetMapping("/student")
    public String index(Model model) {
        List<Student> students = null;

        try {
            students = studentService.getList(null);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
            return null;
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("students", students);
        return "/student/index";
    }

    @GetMapping("/student/detail/{studno}")
    public String detail(Model model,
        @PathVariable("studno") int studno) {
        
        //조회 조건에 사용할 변수를 Beans에 저장
        Student params = new Student();
        params.setStudno(studno);

        //조회 결과를 저장할 객체 선언
        Student student = null;

        try {
            //데이터 조회
            student = studentService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        //View에 데이터 전달
        model.addAttribute("student", student);
        return "/student/detail";
    }
    
     /**
     * 학생 등록 화면
     * @return  학생 등록 화면을 구현한 View 경로
     */
    @GetMapping("student/add")
    public String add() {
        return "/student/add";
    }
    
    @ResponseBody
    @PostMapping("/student/add_ok")
    public void addOk(HttpServletRequest request,
        @RequestParam("name") String name,
        @RequestParam("userid") String userid,
        @RequestParam("grade") int grade,
        @RequestParam("idnum") String idnum,
        @RequestParam("birthdate") String birthdate,
        @RequestParam("tel") String tel,
        @RequestParam("height") int height,
        @RequestParam("weight") int weight,
        @RequestParam("deptno") int deptno,
        @RequestParam("profno") int profno
        ) {

        //정상적인 경로로 접근한 경우 이전 페이지 주소는
        //1) http://localhost/department
        //2) http://localhost/department/detail/학과번호
        //두 가지 경우가 있다.
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/student")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        //저장한 값들을 Beans에 담는다.
        Student student = new Student();
        student.setName(name);
        student.setUserid(userid);
        student.setGrade(grade);
        student.setIdnum(idnum);
        student.setBirthdate(birthdate);
        student.setTel(tel);
        student.setHeight(height);
        student.setWeight(weight);
        student.setDeptno(deptno);
        student.setProfno(profno);

        try {
            studentService.addItem(student);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //insert, update, delete 처리를 수행하는 경우에는 리다이렉트로 이동
        //insert 결과를 확인할 수 있는 상세 페이지로 이동
        // 상세 페이지에 조회 대상의 pk값을 전달해야한다.
        webHelper.redirect("/student/detail/" + student.getStudno(), "등록되었습니다.");
    }

    @ResponseBody
    @GetMapping("/student/delete/{studno}")
    public void delete ( HttpServletRequest request,
        @PathVariable("studno") int studno) {
        
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains("/student")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        Student student = new Student();
        student.setStudno(studno);

        try {
            studentService.deleteItem(student);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        webHelper.redirect("/student", "삭제되었습니다.");
    }

    
     /**
     * 학생 수정 페이지
     * @param model - Model 객체
     * @param studno - 학생 번호
     * @return  View 페이지의 경로
     */
    @GetMapping("/student/edit/{studno}")
    public String edit(Model model,
        @PathVariable("studno") int studno) {
        
        //파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색 조건으로 사용하기 위함
        Student params = new Student();
        params.setStudno(studno);

        //수정할 데이터의 현재 값을 조회
        Student student = null;

        try {
            student = studentService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        //View에 데이터 전달
        model.addAttribute("student", student);


        return "/student/edit";
    }

    @ResponseBody
    @PostMapping("/student/edit_ok/{studno}")
    public void edit_ok(
        @PathVariable("studno") int studno,
        @RequestParam("name") String name,
        @RequestParam("userid") String userid,
        @RequestParam("grade") int grade,
        @RequestParam("idnum") String idnum,
        @RequestParam("birthdate") String birthdate,
        @RequestParam("tel") String tel,
        @RequestParam("height") int height,
        @RequestParam("weight") int weight,
        @RequestParam("deptno") int deptno,
        @RequestParam("profno") int profno) {
        
        Student student = new Student();
        student.setStudno(studno);
        student.setName(name);
        student.setUserid(userid);
        student.setGrade(grade);
        student.setIdnum(idnum);
        student.setBirthdate(birthdate);
        student.setTel(tel);
        student.setHeight(height);
        student.setWeight(weight);
        student.setDeptno(deptno);
        student.setProfno(profno);

        try {
            studentService.editItem(student);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/student/detail/" + student.getStudno(), "수정되었습니다.");
    }
}

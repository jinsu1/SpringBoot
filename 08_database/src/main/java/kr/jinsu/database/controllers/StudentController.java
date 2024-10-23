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
    
}

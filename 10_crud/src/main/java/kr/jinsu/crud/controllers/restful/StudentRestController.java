package kr.jinsu.crud.controllers.restful;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import kr.jinsu.crud.helpers.Pagination;
import kr.jinsu.crud.helpers.RestHelper;
import kr.jinsu.crud.models.Student;
import kr.jinsu.crud.services.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class StudentRestController {
    /** 학생 관리 서비스 객체 주입 */
    @Autowired
    private StudentService studentService;

    /** RestHelper 주입 */
    @Autowired
    private RestHelper restHelper;

    /**
     * 학생 목록 화면
     * @param model 모델
     * @return  학생 목록 화면을 구현한 View 경로
     */
    @GetMapping({"/api/student"})
    public Map<String, Object> getList(
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
        Student input = new Student();
        input.setName(keyword);
        input.setUserid(keyword);

        List<Student> students = null;

        try {
            //전체 게시글 수 조회
            totalCount = studentService.getCount(input);
            //페이지 번호 계산 ==> 계산 결과가 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listcount, pageCount);

            //SQL의 limit절에서 사용될 값을 Beans의 static 변수에 저장
            Student.setOffset(pagination.getOffset());
            Student.setListCount(pagination.getListCount());

            students = studentService.getList(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();

        data.put("item", students);
        data.put("keyword", keyword);
        data.put("pagination", pagination);

        return restHelper.sendJson(data);
    }

    @GetMapping("/api/student/{studno}")
    public Map<String, Object> detail(Model model,
        @PathVariable("studno") int studno) {
        
        //조회 조건에 사용할 변수를 Beans에 저장
        Student input = new Student();
        input.setStudno(studno);

        //조회 결과를 저장할 객체 선언
        Student student = null;

        try {
            //데이터 조회
            student = studentService.getItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", student);

        return restHelper.sendJson(data);
    }
    
    @PostMapping("/api/student")
    public Map<String, Object> addOk(HttpServletRequest request,
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
        @RequestParam("profno") int profno){

        //저장한 값들을 Beans에 담는다.
        Student input = new Student();
        input.setStudno(studno);
        input.setName(name);
        input.setUserid(userid);
        input.setGrade(grade);
        input.setIdnum(idnum);
        input.setBirthdate(birthdate);
        input.setTel(tel);
        input.setHeight(height);
        input.setWeight(weight);
        input.setDeptno(deptno);
        input.setProfno(profno);

        Student student = null;

        try {
            student = studentService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", student);

        return restHelper.sendJson(data);
    }

    @DeleteMapping("/api/student/{studno}")
    public Map<String, Object> delete ( HttpServletRequest request,
        @PathVariable("studno") int studno) {

        Student input = new Student();
        input.setStudno(studno);

        try {
            studentService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }

    @PutMapping("/api/student/{studno}")
    public Map<String, Object> edit_ok(
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
        @RequestParam("profno") Integer profno) {
        
        Student input = new Student();
        input.setStudno(studno);
        input.setName(name);
        input.setUserid(userid);
        input.setGrade(grade);
        input.setIdnum(idnum);
        input.setBirthdate(birthdate);
        input.setTel(tel);
        input.setHeight(height);
        input.setWeight(weight);
        input.setDeptno(deptno);
        input.setProfno(profno);  
        
        Student student = null;

        try {
            student = studentService.editItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", student);

        return restHelper.sendJson(data);
       
    }
}

package kr.jinsu.crud.controllers.restful;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import kr.jinsu.crud.helpers.Pagination;
import kr.jinsu.crud.helpers.RestHelper;
import kr.jinsu.crud.models.Professor;
import kr.jinsu.crud.services.ProfessorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class ProfessorRestController {
    /** 교수 관리 서비스 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    /** RestHelper 주입 */
    @Autowired
    private RestHelper restHelper;

    /**
     * 교수 목록 화면
     * @param model 모델
     * @return  교수 목록 화면을 구현한 View 경로
     */
    @GetMapping({"/api/professor"})
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
        Professor input = new Professor();
        input.setName(keyword);
        input.setUserid(keyword);

        List<Professor> professors = null;

        try {
            //전체 게시글 수 조회
            totalCount = professorService.getCount(input);
            //페이지 번호 계산 ==> 계산 결과가 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listcount, pageCount);

            //SQL의 limit절에서 사용될 값을 Beans의 static 변수에 저장
            Professor.setOffset(pagination.getOffset());
            Professor.setListCount(pagination.getListCount());

            professors = professorService.getList(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();

        data.put("item", professors);
        data.put("keyword", keyword);
        data.put("pagination", pagination);

        return restHelper.sendJson(data);
    }

    @GetMapping("/api/professor/{profno}")
    public Map<String, Object> detail(Model model,
        @PathVariable("profno") int profno) {
        
        //조회 조건에 사용할 변수를 Beans에 저장
        Professor input = new Professor();
        input.setProfno(profno);

        //조회 결과를 저장할 객체 선언
        Professor professor = null;

        try {
            //데이터 조회
            professor = professorService.getItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", professor);

        return restHelper.sendJson(data);
    }
    
    @PostMapping("/api/professor")
    public Map<String, Object> addOk(HttpServletRequest request,
    @RequestParam("name") String name,
    @RequestParam("userid") String userid,
    @RequestParam("position") String position,
    @RequestParam("sal") int sal,
    @RequestParam("hiredate") String hiredate,
    @RequestParam(value = "comm", required=false) Integer comm,
    @RequestParam("deptno") int deptno){

        //저장한 값들을 Beans에 담는다.
        Professor input = new Professor();
        input.setName(name);
        input.setUserid(userid);
        input.setPosition(position);
        input.setSal(sal);
        input.setHiredate(hiredate);
        if(comm != null) {
            input.setComm(comm);
        }
        input.setComm(null);
        input.setDeptno(deptno);

        Professor professor = null;

        try {
            professor = professorService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", professor);

        return restHelper.sendJson(data);
    }

    @DeleteMapping("/api/professor/{profno}")
    public Map<String, Object> delete ( HttpServletRequest request,
        @PathVariable("profno") int profno) {

        Professor input = new Professor();
        input.setProfno(profno);

        try {
            professorService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }

    @PutMapping("/api/professor/{profno}")
    public Map<String, Object> edit_ok(
        @PathVariable("profno") int profno,
        @RequestParam("name") String name,
        @RequestParam("userid") String userid,
        @RequestParam("position") String position,
        @RequestParam("sal") int sal,
        @RequestParam("hiredate") String hiredate,
        @RequestParam("comm") int comm,
        @RequestParam("deptno") int deptno) {
        
        Professor input = new Professor();
        input.setProfno(profno);
        input.setName(name);
        input.setUserid(userid);
        input.setPosition(position);
        input.setSal(sal);
        input.setHiredate(hiredate);
        input.setComm(comm);
        input.setDeptno(deptno);
        
        Professor professor = null;

        try {
            professor = professorService.editItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", professor);

        return restHelper.sendJson(data);
       
    }
}

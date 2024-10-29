package kr.jinsu.database.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import kr.jinsu.database.models.Student;
import java.util.List;

@Mapper
public interface StudentMapper {
    
    @Insert("INSERT INTO student (name, userid, grade, idnum, birthdate, tel, height, weight, deptno, profno) " +
     "values" + 
     "(#{name}, #{userid}, #{grade}, #{idnum}, #{birthdate}, #{tel}, #{height}, #{weight}, #{deptno}, #{profno})")
    @Options(useGeneratedKeys=true, keyProperty="studno", keyColumn="studno")
    int insert(Student input);

    @Update("UPDATE Student SET name = #{name}, userid = #{userid}, grade = #{grade}, idnum = #{idnum}, " + // 줄바꿈시 칸 비우기
                "birthdate = #{birthdate}, tel = #{tel}, height = #{height}, weight = #{weight}, deptno = #{deptno}, profno = #{profno} " + // 줄바꿈시 칸 비우기
                "WHERE studno = #{studno}")
    int update(Student input);

    @Delete("delete from student where studno = #{studno}")
    int delete(Student input);
    
    // 학과를 삭제하기 전에 학과에 소속된 학생 데이터를 삭제
    @Delete("DELETE FROM student where deptno = #{deptno}")
    int deleteByDeptno(Student input);

    // 교수를 삭제하기 전에 교수에게 소속된 학생들과의 연결을 해제
    // --> profno 컬럼이 null 허용으로 설정되어야 함
    @Update("UPDATE student SET profno = null WHERE profno = #{profno}")
    int updateByProfno(Student input);

    @Select("select " + 
    "studno, s.name as name, s.userid as userid, grade, idnum, "+
    "DATE_FORMAT(birthdate, '%Y-%m-%d') as birthdate, " +
    "tel, height, weight, d.deptno as deptno,p.profno as profno, dname, p.name as pname " + 
    "FROM student s " + 
    "inner join department d on s.deptno = d.deptno " + 
    "inner join professor p on s.profno =  p.profno " + 
    "WHERE studno = #{studno}")
    @Results(id = "studentMap", value = {
        @Result(property = "studno", column = "studno"),
        @Result(property = "name", column = "name"),
        @Result(property = "userid", column = "userid"),
        @Result(property = "grade", column = "grade"),
        @Result(property = "idnum", column = "idnum"),
        @Result(property = "birthdate", column = "birthdate"),
        @Result(property = "tel", column = "tel"),
        @Result(property = "height", column = "height"),
        @Result(property = "weight", column = "weight"),
        @Result(property = "deptno", column = "deptno"),
        @Result(property = "profno", column = "profno"),
        @Result(property = "dname", column = "dname"),
        @Result(property = "pname", column = "pname")

    })
    Student selectItem(Student input);



    @Select("<script>" +
            "select " +
            "studno, s.name as name, s.userid as userid, grade, idnum, " +
            "DATE_FORMAT(birthdate, '%Y-%m-%d') as birthdate, " +
            "tel, height, weight,  d.deptno as deptno,p.profno as profno, dname, p.name as pname " +
            "FROM Student s " +
            "inner join department d on s.deptno = d.deptno " + 
            "inner join professor p on s.profno =  p.profno " + 
            "<where>" +
            "<if test='name != null'>s.name like concat('%', #{name}, '%')</if> " +
            "<if test='userid != null'>or s.userid like concat('%', #{userid}, '%')</if> " +
            "</where>" +
            "order by studno desc " +
            "</script>")
    @ResultMap("studentMap")
    List<Student> selectList(Student input);

    /**
     * 검색 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회 조건을 담고 있는 객체
     * @return  조회 결과 수
     */
    @Select("<script>" +
            "select count(*) as cnt from student s " +
            "inner join department d on s.deptno = d.deptno " + 
            "inner join professor p on s.profno =  p.profno " + 
            "<where>" +
            "<if test='name != null'>s.name like concat('%', #{name}, '%')</if>" +
            "<if test='userid != null'>or s.userid like concat('%', #{userid}, '%')</if> " +
            "</where>" +
            "</script>")
    public int selectCount(Student input);

}

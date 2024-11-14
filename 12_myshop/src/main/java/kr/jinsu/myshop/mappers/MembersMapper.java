package kr.jinsu.myshop.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.jinsu.myshop.models.Members;

@Mapper
public interface MembersMapper {
    
    /**
     * 회원 정보를 입력한다.
     * PK값은 파라미터로 전달된 input 객체에 참조로 처리된다.
     * @param input - 입력할 학과 정보에 대한 모델 객체
     * @return  입력된 데이터 수
     */
    @Insert("insert into members (" +
            "user_id, user_pw, user_name, email, phone, " +
            "birthday, gender, postcode, addr1, addr2, " + 
            "photo, is_out, is_admin, login_date, reg_date, " + 
            "edit_date " +
            ") values (" +
            "#{userId}, MD5(#{userPw}), #{userName}, #{email}, #{phone}, " +
            "#{birthday}, #{gender}, #{postcode}, #{addr1}, #{addr2}, " + 
            "#{photo}, 'N', 'N', now(), now(), " + 
            "now())")
    // insert 문에서 필요한 PK에 대한 옵션 정의
    // --> useGeneratedKeys : auto_increment가 적용된 테이블인 경우 사용
    // --> keyProperty : 파라미터로 전달되는 model 객체에서 PK에 대응되는 멤버변수
    // --> keyColumn : 테이블의 PK 컬럼 명
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Members input);

    /**
     * 
     * @param input -   수정할 데이터에 대한 모델 객체
     * @return  수정된 데이터 수
     */
    @Update("update members set " +
            "user_id=#{userId}, " + 
            "user_pw=#{userPw}, " +
            "user_name=#{userName}, " +
            "email=#{email}, " + 
            "phone=#{phone}, " + 
            "birthday=#{birthday}, " + 
            "gender=#{gender}, " + 
            "postcode=#{postcode}, " + 
            "addr1=#{addr1}, " + 
            "addr2=#{addr2}, " + 
            "photo=#{photo}, " + 
            "edit_date=now() " + 
            "where id=#{id}")
    int update(Members input);


    @Delete("delete from members where id=#{id}")
    int delete(Members input);

    @Select("select " + 
            "user_id, user_pw, user_name, email, phone, " + 
            "birthday, gender, postcode, addr1, addr2, " + 
            "photo, is_out, is_admin, login_date, reg_date, " + 
            "edit_date " + 
            "from members " + 
            "where id=#{id}")
    /** 조회 결과와 리턴할 model 객체를 연결하기 위한 규칙 정의
     * --> property : model 객체의 멤버변수 이름
     * --> column : select 문에 명시된 필드 이름 (AS 옵션을 사용할 경우 별칭으로 명시)
     */
    @Results(id="membersMap", value={
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "userPw", column = "user_pw"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "email", column = "email"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "birthday", column = "birthday"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "postcode", column = "postcode"),
        @Result(property = "addr1", column = "addr1"),
        @Result(property = "addr2", column = "addr2"),
        @Result(property = "photo", column = "photo"),
        @Result(property = "isOut", column = "is_out"),
        @Result(property = "isAdmin", column = "is_admin"),
        @Result(property = "loginDate", column = "login_date"),
        @Result(property = "regDate", column = "reg_date"),
        @Result(property = "editDate", column = "edit_date")
    })
    Members selectItem(Members input);

    @Select("select " + 
            "id, " +
            "user_id, user_pw, user_name, email, phone, " + 
            "birthday, gender, postcode, addr1, addr2, " + 
            "photo, is_out, is_admin, login_date, reg_date, " + 
            "edit_date " + 
            "from members " +
            "order by id desc")
    @ResultMap("membersMap")
    List<Members> selectList(Members input);

    @Select("<script>" +
            "select count(*) from members\n" +
            "<where>\n" +
            "<if test='userId != null'>user_id = #{userId}</if>\n" +
            "<if test='email != null'>email = #{email}</if>\n" +
            "</where>\n" +
            "</script>")
    public int selectCount(Members input);

    @Select("select user_id from members " +
            "where user_name = #{userName} and email = #{email}")
    @ResultMap("membersMap")
    public Members findId(Members input);

    @Update("update members set user_pw = MD5(#{userPw})" +
            "where user_id = #{userId} and email = #{email}")
    public int resetPw(Members input);

    @Select("select \n" +
            "id, user_id, user_pw, user_name, email, phone, birthday, gender, \n" + 
            "postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date \n" +
            "from members \n" +
            "where user_id = #{userId} and user_pw = MD5(#{userPw})")
    @ResultMap("membersMap")
    public Members login(Members input);

    @Update("update members set login_date = now() where id = #{id}")
    public int updateLoginDate(Members input);

     // 회원탈퇴
     @Update("UPDATE members \n" +
     "SET is_out = 'Y', edit_date = now() \n" +
     "WHERE id = #{id} AND user_pw = MD5(#{userPw}) AND is_out = 'N'" )
    public int out(Members input);

    // 탈퇴한 회원 중 탈퇴 후 1분이 지난 회원 사진만 검색
    @Select("SELECT photo FROM members \n" +
        "WHERE is_out='Y' AND \n" +
        "edit_date < DATE_ADD(NOW(), interval -1 minute) AND \n" +
        "photo IS NOT NULL")
    @ResultMap("membersMap")
    public List<Members> selectOutMembersPhoto();

    // 탈퇴한 회원 중 탈퇴 후 1분이 지난 회원 삭제
    @Delete("DELETE FROM members \n" +
        "WHERE is_out='Y' AND \n" +
        "edit_date < DATE_ADD(NOW(), interval -1 minute)")
    public int deleteOutMembers();
}

package kr.jinsu.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.models.Members;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MembersMapperTest {
    // 테스트 클래스에서는 객체 주입을 사용해야 함
    @Autowired
    private MembersMapper membersMapper;

    @Test
    @DisplayName("회원 추가 테스트")
    void insertMembers() {
        // given
        Members input = new Members();
        input.setUserId("jinsu");
        input.setUserPw("aaa111");
        input.setUserName("박진수");
        input.setEmail("wlstn4205@naver.com");
        input.setPhone("010-3306-4205");
        input.setBirthday("1996-09-05");
        input.setGender("M");
        input.setPostcode("16125");
        input.setAddr1("경기도 수원시");
        input.setAddr2("우만동");
        input.setPhoto("photo");
        // input.setIsOut("Y");
        // input.setIsAdmin("Y");
        // input.setLoginDate("2024-10-20");
        

        int output = membersMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + output);
        // 생성된 PK값
        log.debug("new Id: " + input.getId());
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMembers() {
        Members input = new Members();
        input.setId(5);
        input.setUserId("수정된 jinsu");
        input.setUserPw("42051234");
        input.setUserName("수정된 진수");
        input.setEmail("수정된4205@naver.com");
        input.setPhone("010-9999-9999");
        input.setBirthday("1996-09-06");
        input.setGender("F");
        input.setPostcode("15026");
        input.setAddr1("수정된 수원시 팔달구1");
        input.setAddr2("수정된 우만1동");
        input.setPhone("photo1");
        // input.setIsOut("N");
        // input.setIsAdmin("N");
        // input.setLoginDate("")

        int output = membersMapper.update(input);

        log.debug("output: " + output);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMembers() {
        Members input = new Members();
        input.setId(3);

        int output = membersMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 회원 조회 테스트")
    void selectOneMembers() {
        Members input = new Members();
        input.setId(5);

        Members output = membersMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("학과 목록 조회 테스트")
    void selectListMembers() {
        List<Members> output = membersMapper.selectList(null);

        for(Members item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
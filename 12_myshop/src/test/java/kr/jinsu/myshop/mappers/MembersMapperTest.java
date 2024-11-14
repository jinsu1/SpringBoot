package kr.jinsu.myshop.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.myshop.models.Members;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MembersMapperTest {

    @Autowired
    private MembersMapper membersMapper;

    @Test
    @DisplayName("비밀번호 수정 테스트")
    void updateUserPw() {
        Members input = new Members();
        input.setUserId("1234");
        input.setEmail("www@q123.com ");
        input.setUserPw("111111111");

        int output = membersMapper.resetPw(input);

        log.debug("output: " + output);
    }

}
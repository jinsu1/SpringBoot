// package kr.jinsu.myshop.services;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import kr.jinsu.myshop.exceptions.ServiceNoResultException;
// import kr.jinsu.myshop.models.Members;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @SpringBootTest
// public class MembersServiceTest {

//     @Autowired
//     private MembersService membersService;

//     @Test
//     @DisplayName("비밀번호 수정 테스트")
//     void updateMembers() throws Exception { 
//         Members input = new Members();
//         input.setUserId("1234");
//         input.setEmail("www@q123.com ");
//         input.setUserPw("11111111");

//         int output = 0;

//         try {
//             output = membersService.resetPw(input);
//         } catch (ServiceNoResultException e) {
//             log.error("SQL문 처리 결과 없음", e);
//         } catch (Exception e) {
//             log.error("Mapper 구현 에러", e);      
//             throw e;  
//         }

//         if (output != 0){
//             log.debug("output: " + output);
//         }
//     }

// }
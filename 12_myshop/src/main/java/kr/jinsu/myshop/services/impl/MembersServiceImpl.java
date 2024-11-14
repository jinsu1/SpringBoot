package kr.jinsu.myshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinsu.myshop.exceptions.ServiceNoResultException;
import kr.jinsu.myshop.mappers.MembersMapper;
import kr.jinsu.myshop.models.Members;
import kr.jinsu.myshop.services.MembersService;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세
 * 
 * (1) 모든 메서드를 재정의 한 직후 리턴값 먼저 정의
 */
@Slf4j
@Service
public class MembersServiceImpl implements MembersService {

    @Autowired
    private MembersMapper membersMapper;

    @Override
    public Members addItem(Members input) throws ServiceNoResultException, Exception {
        int rows = membersMapper.insert(input);
        

        if(rows == 0) {
            throw new ServiceNoResultException("저장된 데이터가 없습니다.");
        }

        return membersMapper.selectItem(input);
    }

    @Override
    public Members editItem(Members input) throws ServiceNoResultException, Exception {
        int rows = membersMapper.update(input);

        if(rows == 0) {
            throw new ServiceNoResultException("수정된 데이터가 없습니다.");
        }

        return membersMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Members input) throws ServiceNoResultException, Exception {
        // delete문 수행 --> 리턴되는 값은 수정된 데이터의 수
        int rows = membersMapper.delete(input);

        if(rows == 0) {
            throw new ServiceNoResultException("삭제된 데이터가 없습니다.");
        }

        return rows;
    }

    @Override
    public Members getItem(Members input) throws ServiceNoResultException, Exception {
        Members output = membersMapper.selectItem(input);

        if(output == null) {
            throw new ServiceNoResultException("조회된 데이터가 없습니다.");
        }

        return output;
    }

    @Override
    public List<Members> getList(Members input) throws ServiceNoResultException, Exception {
        return membersMapper.selectList(input);
    }

    @Override
    public void isUniqueUserId(String userId) throws Exception {
        Members input = new Members();
        input.setUserId(userId);

        int output = 0;

        try{
            output = membersMapper.selectCount(input);

            if (output > 0) {
                throw new Exception("사용할 수 없는 아이디 입니다.");
            }
        } catch (Exception e) {
            log.error("아이디 중복 검사에 실패했습니다.", e);
            throw e;
        }
    }

    @Override
    public void isUniqueEmail(String email) throws Exception {
        Members input = new Members();
        input.setEmail(email);

        int output = 0;

        try{
            output = membersMapper.selectCount(input);

            if (output > 0) {
                throw new Exception("사용할 수 없는 이메일 입니다.");
            }
        } catch (Exception e) {
            log.error("이메일 중복 검사에 실패했습니다.", e);
            throw e;
        }
    }

    @Override
    public Members findId(Members input) throws Exception {
        
        Members output = null;

        try {
            output = membersMapper.findId(input);

            if(output == null) {
                throw new Exception("Members 조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("아이디 검색에 실패했습니다.", e);
            throw e;
        }
        return output;
    }

    @Override
    public int resetPw(Members input) throws Exception {
        int rows = 0;

        try {
            rows = membersMapper.resetPw(input);

            if(rows == 0) {
                throw new Exception("아이디와 이메일을 확인하세요.");
            }
        } catch (Exception e) {
            log.error("Member 데이터 수정에 실패했습니다.", e);
            throw e;
        }
     
        return rows;
    }

    @Override
    public Members login(Members input) throws Exception {
        Members output = null;

        try {
            output = membersMapper.login(input);

            if(output == null) {
                throw new Exception("아이디 혹은 이메일을 확인하세요.");
            }
        } catch (Exception e) {
            log.error("Member 데이터 조회에 실패했습니다.", e);
            throw e;
        }
        // 데이터 조회에 성공했다면 마지막 로그인 시간 갱신
        try {
            int rows = membersMapper.updateLoginDate(output);

            if(rows == 0) {
                throw new Exception("존재하지 않는 회원에 대한 요청 입니다.");
            }
        } catch (Exception e) {
            log.error("Member 데이터 갱신에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    
    @Override
    public int out(Members input) throws Exception {
        int rows = 0;

        try {
            rows = membersMapper.out(input);
            if (rows == 0) {
                throw new Exception("비밀번호 확인이 잘못되었거나 존재하지 않는 회원에 대한 요청입니다");
            }
        } catch (Exception e) {
            log.error("Members 데이터 수정에 실패했습니다.", e);
        }
        

        return rows;
    }

    @Override
    public List<Members> processOutMembers() throws Exception {
        List<Members> output = null;

        try {
            // 1) is_out이 Y인 상태로 특정 시간이 지난 데이터를 조회한다
            output = membersMapper.selectOutMembersPhoto();

            // 2) 탈퇴 요청된 데이터를 삭제한다
            membersMapper.deleteOutMembers();
        } catch (Exception e) {
            throw new Exception("탈퇴 처리에 실패했습니다.");
        }
        
        return output;

    }
}

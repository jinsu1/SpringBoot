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
}

package kr.jinsu.myshop.services;
import kr.jinsu.myshop.exceptions.ServiceNoResultException;
import kr.jinsu.myshop.models.Members;

import java.util.List;

public interface MembersService {
    public Members addItem(Members input) throws ServiceNoResultException, Exception;

    public Members editItem(Members input) throws ServiceNoResultException, Exception;

    public int deleteItem(Members input) throws ServiceNoResultException, Exception;

    public Members getItem(Members input) throws ServiceNoResultException, Exception;

    public List<Members> getList(Members input) throws ServiceNoResultException, Exception;

    public void isUniqueUserId(String userId) throws Exception;

    public void isUniqueEmail(Members input) throws Exception;

    public Members findId(Members input) throws Exception;

    public void resetPw(Members input) throws Exception;

    public Members login(Members input) throws Exception;

    public int out (Members input) throws Exception;

    public List<Members> processOutMembers() throws Exception;
}
        

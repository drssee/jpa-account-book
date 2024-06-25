package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.MemberDao;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SearchCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Override
    public MemberDto getMemberById(Long id) {
        return null;
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        return null;
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        return null;
    }

    @Override
    public List<MemberDto> getMembers(SearchCondition searchCondition) {
        return List.of();
    }
}

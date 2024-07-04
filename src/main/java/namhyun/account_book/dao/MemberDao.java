package namhyun.account_book.dao;

import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface MemberDao {

    MemberDto saveMember(MemberDto memberDto);
    MemberDto updateMember(MemberDto memberDto);
    MemberDto getMemberById(String memberId);
    List<MemberDto> getMembers(SearchCondition searchCondition);
}

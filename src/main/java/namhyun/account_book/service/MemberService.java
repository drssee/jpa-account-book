package namhyun.account_book.service;

import namhyun.account_book.dto.MemberDto;

public interface MemberService {

    MemberDto saveMember(MemberDto memberDto);
    MemberDto getMemberById(String memberId);
}

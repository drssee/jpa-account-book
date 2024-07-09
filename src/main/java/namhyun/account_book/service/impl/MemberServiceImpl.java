package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.MemberDao;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        return memberDao.saveMember(memberDto);
    }

    @Override
    public MemberDto getMemberById(String memberId) {
        return memberDao.getMemberById(memberId);
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        return memberDao.updateMember(memberDto);
    }

    @Override
    public void deleteMember(String memberId) {
        MemberDto findMember = memberDao.getMemberById(memberId);
        if (findMember.getUseYn().equals("N")) throw new RuntimeException(
                "deleteMember() - already deleted member"
        );
        findMember.setUseYn("N");
        memberDao.updateMember(findMember);
    }
}

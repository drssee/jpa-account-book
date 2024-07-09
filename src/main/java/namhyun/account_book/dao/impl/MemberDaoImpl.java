package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.MemberDao;
import namhyun.account_book.domain.Member;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SearchCondition;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {

    @PersistenceContext
    private EntityManager em;

    private final ModelMapper modelMapper;

    @Override
    public MemberDto getMemberById(String memberId) {
        return modelMapper.map(em.find(Member.class, memberId), MemberDto.class);
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        memberDto.setCreatedAt(LocalDateTime.now());
        memberDto.setCreatedBy(memberDto.getId());
        Member member = modelMapper.map(memberDto, Member.class);
        em.persist(member);
        return modelMapper.map(member, MemberDto.class);
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        Member member = modelMapper.map(memberDto, Member.class);
        Member updatedMember = em.merge(member);
        return modelMapper.map(updatedMember, MemberDto.class);
    }

    @Override
    public List<MemberDto> getMembers(SearchCondition searchCondition) {
        return List.of();
    }
}

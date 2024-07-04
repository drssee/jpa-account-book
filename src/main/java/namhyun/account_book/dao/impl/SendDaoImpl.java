package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.SendDao;
import namhyun.account_book.domain.Member;
import namhyun.account_book.domain.Send;
import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.SendDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SendDaoImpl implements SendDao {

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public SendDto getSendById(Long id) {
        String query = "select s from Send s where s.id = :id";
        Send result = em.createQuery(query, Send.class)
                .setParameter("id", id)
                .getSingleResult();
        return modelMapper.map(result, SendDto.class);
    }

    @Override
    public SendDto saveSend(SendDto sendDto) {
        sendDto.setCreatedAt(LocalDateTime.now());
        sendDto.setCreatedBy(sendDto.getMemberDto().getId());
        Send send = modelMapper.map(sendDto, Send.class);
        Member member = em.find(Member.class, sendDto.getMemberDto().getId());
        send.setMember(member);
        em.persist(send);
        return modelMapper.map(send, SendDto.class);
    }

    @Override
    public SendDto updateSend(SendDto sendDto) {
        return null;
    }

    @Override
    public List<SendDto> getSends(SearchCondition searchCondition) {
        return List.of();
    }
}

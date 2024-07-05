package namhyun.account_book.dao;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SendDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SendDaoTest {

    MemberDto memberDto;
    SendDto sendDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    SendDao sendDao;

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    public void init() {
        memberDto = commonInit.initMemberDto();
        sendDto = commonInit.initSendDto();
    }

    @Test
    @DisplayName("SendDao.saveSend()")
    public void saveSend() {
        memberDao.saveMember(memberDto);
        SendDto savedSend = sendDao.saveSend(sendDto);

        assertThat(savedSend).isNotNull();
        assertThat(savedSend.getId()).isNotNull();
        assertThat(savedSend.getCreatedAt()).isNotNull();
        assertThat(savedSend.getCreatedBy()).isEqualTo(savedSend.getMemberDto().getId());
    }

    @Test
    @DisplayName("SendDao.getSendById()")
    public void getSendById() {
        memberDao.saveMember(memberDto);
        SendDto savedSend = sendDao.saveSend(sendDto);

        SendDto findSend = sendDao.getSendById(savedSend.getId());

        assertThat(findSend).isNotNull();
        assertThat(findSend.getId()).isNotNull();
        assertThat(findSend.getId()).isEqualTo(savedSend.getId());
        assertThat(findSend.getCreatedAt()).isNotNull();
        assertThat(findSend.getCreatedBy()).isEqualTo(savedSend.getMemberDto().getId());
    }

    @Test
    @DisplayName("SendDao.getSendListByMemberId()_x1")
    public void getSendListByMemberId_x1() {
        saveSend();

        List<SendDto> result = sendDao.getSendListByMemberId(memberDto.getId());

        assertThat(result.size()).isEqualTo(1);
        commonInit.assertFindSendDto(result.get(0), sendDto);
    }



    @Test
    @DisplayName("SendDao.getSendListByMemberId()_xn")
    public void getSendListByMemberId_xn() {
        memberDao.saveMember(memberDto);
        int n = 3;
        for (int i = 0; i < n; i++) {
            sendDao.saveSend(sendDto);
        }

        List<SendDto> result = sendDao.getSendListByMemberId(memberDto.getId());

        assertThat(result.size()).isEqualTo(n);
        result.forEach(e -> commonInit.assertFindSendDto(e, sendDto));
    }
}

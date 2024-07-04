package namhyun.account_book.dao;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SendDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

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

    }
}

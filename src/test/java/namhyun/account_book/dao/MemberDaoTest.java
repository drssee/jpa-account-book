package namhyun.account_book.dao;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.MemberDto;
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
public class MemberDaoTest {

    MemberDto memberDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("MemberDao.saveMember()")
    void saveMember() {
        MemberDto savedMember = memberDao.saveMember(memberDto);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getCreatedBy()).isNotNull();
        assertThat(savedMember.getCreatedAt()).isNotNull();
    }
}

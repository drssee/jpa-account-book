package namhyun.account_book.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.PayPurposeDto;
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
class AccountBookDaoTest {

    CommonInit commonInit = new CommonInit();
    AccountBookDto accountBookDto;
    MemberDto memberDto;

    @Autowired
    AccountBookDao accountBookDao;

    @Autowired
    MemberDao memberDao;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        accountBookDto = commonInit.initAccountBookDto();
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("AccountBookDao.saveAccountBook()")
    void saveAccountBook() {

        AccountBookDto savedAccountBook = accountBookDao.saveAccountBook(accountBookDto);

        assertThat(savedAccountBook).isNotNull();
        assertThat(savedAccountBook.getId()).isNotNull();
        assertThat(savedAccountBook.getId()).isNotEqualTo(0L);
        assertThat(savedAccountBook.getCreatedAt()).isNotNull();
        assertThat(savedAccountBook.getCreatedBy()).isEqualTo(accountBookDto.getMemberDto().getId());
    }

    @Test
    @DisplayName("AccountBookDao.getAccountBookById()")
    void getAccountBookById() {

        commonInit.saveMember(memberDao, memberDto);
        commonInit.flush(em);
        AccountBookDto savedAccountBook = accountBookDao.saveAccountBook(accountBookDto);
        commonInit.flush(em);

        AccountBookDto findAccountBook = accountBookDao.getAccountBookById(savedAccountBook.getId());
        assertThat(findAccountBook).isNotNull();
        assertThat(findAccountBook).isEqualTo(savedAccountBook);
    }
}
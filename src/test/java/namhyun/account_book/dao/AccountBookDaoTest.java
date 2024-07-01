package namhyun.account_book.dao;


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

@SpringBootTest
@Transactional
class AccountBookDaoTest {

    CommonInit commonInit = new CommonInit();
    AccountBookDto accountBookDto;

    @Autowired
    AccountBookDao accountBookDao;

    @BeforeEach
    void init() {
        accountBookDto = commonInit.initAccountBookDto();
    }

    @Test
    @DisplayName("AccountBookDao.saveAccountBook()")
    void saveAccountBook() {

        AccountBookDto savedAccountBook = accountBookDao.saveAccountBook(accountBookDto);

        Assertions.assertThat(savedAccountBook).isNotNull();
        Assertions.assertThat(savedAccountBook.getId()).isNotNull();
        Assertions.assertThat(savedAccountBook.getId()).isNotEqualTo(0L);
        Assertions.assertThat(savedAccountBook.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedAccountBook.getCreatedBy()).isEqualTo(accountBookDto.getMemberDto().getId());
    }
}
package namhyun.account_book.service;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccountBookServiceTest {

    CommonInit commonInit = new CommonInit();
    AccountBookDto accountBookDto;
    MemberDto memberDto;
    StatisticsDto statisticsDto;
    ConfigDto configDto;
    SendDto sendDto;

    @Autowired
    AccountBookService accountBookService;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void init() {
        accountBookDto = commonInit.initAccountBookDto();
        memberDto = commonInit.initMemberDto();
        statisticsDto = commonInit.initStatisticsDto();
        configDto = commonInit.initConfigDto();
        sendDto = commonInit.initSendDto();
    }

    @Test
    @DisplayName("AccountBookService.pay()")
    void pay() {
        //1. accountbook 이 제대로 저장됐는지
        //2-1. statistics 가 없을 경우 제대로 저장됐는지
        //2-2. statistics 가 있을 경우 제대로 저장됐는지
        //3. config 가 제대로 저장됐는지
        //4-1. send 설정이 있을 경우 제대로 저장이 됐는지
        //4-2. send 설정이 없으면 저장이 안되는지
        memberService.saveMember(memberDto);
        accountBookService.pay(accountBookDto);
    }
}

package namhyun.account_book.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.*;
import org.assertj.core.api.Assertions;
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

    @Autowired
    ConfigService configService;

    @Autowired
    SendService sendService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        accountBookDto = commonInit.initAccountBookDto();
        memberDto = commonInit.initMemberDto();
        statisticsDto = commonInit.initStatisticsDto();
        configDto = commonInit.initConfigDto();
        sendDto = commonInit.initSendDto();
    }

    // pay한 양만큼 통계에 반영됐는지 - 단건, 다중건
    @Test
    @DisplayName("AccountBookService.pay()_x1_sendOff")
    void pay_x1_sendOff() {
        // 멤버 저장
        commonInit.saveMember(memberService, memberDto);
        commonInit.flush(em);
        // 설정 저장(null, default)
        commonInit.saveConfig(configService, configDto);
        commonInit.flush(em);

        // pay 실행
        AccountBookDto savedAccountBookDto = accountBookService.pay(accountBookDto);
        commonInit.flush(em);

        //1. accountbook 이 제대로 저장됐는지
        AccountBookDto findAccountBookDto = accountBookService.getAccountBookById(savedAccountBookDto.getId());
        Assertions.assertThat(findAccountBookDto).isEqualTo(savedAccountBookDto);

        //2. send 설정이 없을 경우 제대로 저장이 됐는지
        ConfigDto findConfig = configService.getConfigByMemberId(memberDto.getId());
        System.out.println("findConfig.isCanSendMessage() = " + findConfig.isCanSendMessage());
        //2-2. send 설정이 없으면 저장이 안되는지
    }

    @Test
    @DisplayName("AccountBookService.pay()_x1_sendOn")
    void pay_x1_sendOn() {
        // 멤버 저장
        commonInit.saveMember(memberService, memberDto);
        commonInit.flush(em);
        // 설정 저장(null, on)
        configDto.setCanSendMessage(true);
        commonInit.saveConfig(configService, configDto);
        commonInit.flush(em);

        // pay 실행
        AccountBookDto savedAccountBookDto = accountBookService.pay(accountBookDto);
        commonInit.flush(em);

        //1. accountbook 이 제대로 저장됐는지
        AccountBookDto findAccountBookDto = accountBookService.getAccountBookById(savedAccountBookDto.getId());
        Assertions.assertThat(findAccountBookDto).isEqualTo(savedAccountBookDto);

        //2. send 설정이 on일 경우 제대로 저장이 됐는지

    }
}

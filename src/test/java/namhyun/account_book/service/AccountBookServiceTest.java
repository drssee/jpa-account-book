package namhyun.account_book.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dto.*;
import namhyun.account_book.enums.SendType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    StatisticsService statisticsService;

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
        assertThat(findAccountBookDto).isEqualTo(savedAccountBookDto);

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
        assertThat(findAccountBookDto).isEqualTo(savedAccountBookDto);

        //2. send 설정이 on일 경우 제대로 저장이 됐는지
        List<SendDto> sendList = sendService.getSendListByMemberId(savedAccountBookDto.getMemberDto().getId());
        ConfigDto findConfigDto = configService.getConfigByMemberId(savedAccountBookDto.getMemberDto().getId());
        SendType sendType =
                findConfigDto.getCustomSendType() != null ?
                        findConfigDto.getCustomSendType() : SendType.MAIL;
        LocalDateTime sendTime =
                findConfigDto.getCustomSendTime() != null ?
                        findConfigDto.getCustomSendTime() : LocalDateTime.now().plusMinutes(5);
        String sendMsg =
                findConfigDto.getCustomMsg() != null ?
                        findConfigDto.getCustomMsg() : createDefaultCustomMsg(memberDto.getId());

        assertThat(sendList.size()).isEqualTo(1);
        SendDto findSendDto = sendList.get(0);
        assertThat(findSendDto.getSendType()).isEqualTo(sendType);
        assertThat(findSendDto.getSendTime().withNano(0)).isEqualTo(sendTime.withNano(0));
        assertThat(findSendDto.getMsg()).isEqualTo(sendMsg);
    }

    @Test
    @DisplayName("AccountBookService.pay()_x1_sendOn_custom")
    void pay_x1_sendOn_custom() {
        // 멤버 저장
        commonInit.saveMember(memberService, memberDto);
        commonInit.flush(em);
        // 설정 저장(null, on)
        configDto.setCanSendMessage(true);
        configDto.setCustomSendType(SendType.KAKAO);
        configDto.setCustomSendTime(LocalDateTime.now().plusMinutes(10));
        configDto.setCustomMsg("CUSTOM_MSG");
        commonInit.saveConfig(configService, configDto);
        commonInit.flush(em);

        // pay 실행
        AccountBookDto savedAccountBookDto = accountBookService.pay(accountBookDto);
        commonInit.flush(em);

        //1. accountbook 이 제대로 저장됐는지
        AccountBookDto findAccountBookDto = accountBookService.getAccountBookById(savedAccountBookDto.getId());
        assertThat(findAccountBookDto).isEqualTo(savedAccountBookDto);

        //2. send 설정이 on일 경우 제대로 저장이 됐는지
        List<SendDto> sendList = sendService.getSendListByMemberId(savedAccountBookDto.getMemberDto().getId());
        ConfigDto findConfigDto = configService.getConfigByMemberId(savedAccountBookDto.getMemberDto().getId());
        SendType sendType =
                findConfigDto.getCustomSendType() != null ?
                        findConfigDto.getCustomSendType() : SendType.MAIL;
        LocalDateTime sendTime =
                findConfigDto.getCustomSendTime() != null ?
                        findConfigDto.getCustomSendTime() : LocalDateTime.now().plusMinutes(5);
        String sendMsg =
                findConfigDto.getCustomMsg() != null ?
                        findConfigDto.getCustomMsg() : createDefaultCustomMsg(memberDto.getId());

        assertThat(sendList.size()).isEqualTo(1);
        SendDto findSendDto = sendList.get(0);
        assertThat(findSendDto.getSendType()).isEqualTo(sendType);
        assertThat(findSendDto.getSendTime().withNano(0)).isEqualTo(sendTime.withNano(0));
        assertThat(findSendDto.getMsg()).isEqualTo(sendMsg);
    }

    private String createDefaultCustomMsg(String memberId) {
        StatisticsDto statisticsDto = new StatisticsDto();
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);
        statisticsDto.setMemberDto(memberDto);
        statisticsDto.setYear(String.valueOf(LocalDateTime.now().getYear()));
        statisticsDto.setMonth(String.valueOf(LocalDateTime.now().getMonth().getValue()));
        StatisticsDto findStatisticsDto = statisticsService.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );
        int payLimit = configService.getConfigByMemberId(memberId).getPayLimit();
        if (payLimit == 0) throw new IllegalArgumentException("createDefaultCustomMsg() - 필수 설정값 누락");

        String result = "";
        int totalPayments = findStatisticsDto.getPayments();
        if (totalPayments >= payLimit) {
            result = memberId + "님의 현재까지 사용하신 금액은 " +
                    findStatisticsDto.getPayments() + "입니다." +
                    " 지정하신 한도(" + payLimit + ")를 " +
                    (statisticsDto.getPayments() - payLimit) + "원 초과하셨습니다.";
        } else {
            result = memberId + "님의 현재까지 사용하신 금액은 " +
                    findStatisticsDto.getPayments() + "입니다." +
                    " 지정하신 한도까지 " + (payLimit - totalPayments) + "원 남았습니다.";
        }
        return result;
    }
}

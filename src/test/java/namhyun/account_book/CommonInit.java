package namhyun.account_book;

import namhyun.account_book.dto.*;
import namhyun.account_book.enums.SendType;
import namhyun.account_book.enums.UserType;

import java.time.LocalDateTime;

public class CommonInit {

    private final String DEFAULT_ID = "TEST";
    private final String DEFAULT_NAME = "NAME";
    private final int DEFAULT_PAY_LIMIT = 100000;
    private final int DEFAULT_SEND_MINUTES = 60;
    private final String DEFAULT_SEND_MSG = "`s Send Msg";

    public MemberDto initMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(DEFAULT_ID);
        memberDto.setName(DEFAULT_NAME);
        memberDto.setAge(20);
        memberDto.setUserType(UserType.USER);

        return memberDto;
    }

    public PayPurposeDto initPayPurposeDto() {
        PayPurposeDto payPurposeDto = new PayPurposeDto();
        payPurposeDto.setName(DEFAULT_NAME);
        return payPurposeDto;
    }

    public StatisticsDto initStatisticsDto() {
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setMemberDto(initMemberDto());
        statisticsDto.setPayments(10000);
        statisticsDto.setYear(String.valueOf(LocalDateTime.now().getYear()));
        statisticsDto.setMonth(String.valueOf(LocalDateTime.now().getMonthValue()));
        return statisticsDto;
    }

    public ConfigDto initConfigDto() {
        ConfigDto configDto = new ConfigDto();
        configDto.setMemberDto(initMemberDto());
        configDto.setPayLimit(DEFAULT_PAY_LIMIT);
        configDto.setCanSendMessage(false);
        return configDto;
    }

    public SendDto initSendDto() {
        SendDto sendDto = new SendDto();
        sendDto.setMemberDto(initMemberDto());
        sendDto.setSendTime(LocalDateTime.now().plusMinutes(DEFAULT_SEND_MINUTES));
        sendDto.setSendType(SendType.KAKAO);
        sendDto.setMsg(sendDto.getMemberDto().getId() + DEFAULT_SEND_MSG);
        return sendDto;
    }

    public AccountBookDto initAccountBookDto() {
        AccountBookDto accountBookDto = new AccountBookDto();
        accountBookDto.setPrice(10000);
        accountBookDto.setMemberDto(initMemberDto());
        accountBookDto.setTitle("AccountBookDaoTest");
        accountBookDto.setPayPurpose(initPayPurposeDto());
        accountBookDto.setNeedSum(true);
        return accountBookDto;
    }
}

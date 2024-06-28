package namhyun.account_book.dao;

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

    MemberDto initMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(DEFAULT_ID);
        memberDto.setName(DEFAULT_NAME);
        memberDto.setAge(20);
        memberDto.setUserType(UserType.USER);

        return memberDto;
    }

    PayPurposeDto initPayPurposeDto() {
        PayPurposeDto payPurposeDto = new PayPurposeDto();
        payPurposeDto.setName(DEFAULT_NAME);
        return payPurposeDto;
    }

    StatisticsDto initStatisticsDto() {
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setMemberDto(initMemberDto());
        statisticsDto.setPayments(10000);
        statisticsDto.setYear(String.valueOf(LocalDateTime.now().getYear()));
        statisticsDto.setMonth(String.valueOf(LocalDateTime.now().getMonthValue()));
        return statisticsDto;
    }

    ConfigDto initConfigDto() {
        ConfigDto configDto = new ConfigDto();
        configDto.setMemberDto(initMemberDto());
        configDto.setPayLimit(DEFAULT_PAY_LIMIT);
        configDto.setCanSendMessage(false);
        return configDto;
    }

    SendDto initSendDto() {
        SendDto sendDto = new SendDto();
        sendDto.setMemberDto(initMemberDto());
        sendDto.setEstimatedSendTime(LocalDateTime.now().plusMinutes(DEFAULT_SEND_MINUTES));
        sendDto.setSendType(SendType.MAIL);
        sendDto.setMsg(sendDto.getMemberDto().getId() + DEFAULT_SEND_MSG);
        return sendDto;
    }
}

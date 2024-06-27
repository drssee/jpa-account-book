package namhyun.account_book.dao;

import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.PayPurposeDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.enums.UserType;

import java.time.LocalDateTime;

public class CommonInit {

    private final String DEFAULT_ID = "TEST";
    private final String DEFAULT_NAME = "NAME";
    private final int DEFAULT_PAY_LIMIT = 100000;

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
}

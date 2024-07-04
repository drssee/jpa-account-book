package namhyun.account_book;

import jakarta.persistence.EntityManager;
import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.dao.MemberDao;
import namhyun.account_book.dto.*;
import namhyun.account_book.enums.SendType;
import namhyun.account_book.enums.UserType;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.service.MemberService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        payPurposeDto.setParent(null);
        payPurposeDto.setChildren(new ArrayList<>());
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
        configDto.setCustomMsg(null);
        configDto.setCustomSendType(null);
        configDto.setCustomSendTime(null);
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
        accountBookDto.setYear(String.valueOf(LocalDateTime.now().getYear()));
        accountBookDto.setMonth(String.valueOf(LocalDateTime.now().getMonthValue()));
        return accountBookDto;
    }

    public void saveMember(
            MemberService memberService,
            MemberDto memberDto
    ) {
        memberService.saveMember(memberDto);
    }

    public void saveConfig(
            ConfigService configService,
            ConfigDto configDto
    ) {
        configService.saveConfig(configDto);
    }

    public void saveMember(
            MemberDao memberDao,
            MemberDto memberDto
    ) {
        memberDao.saveMember(memberDto);
    }

    public void saveConfig(
            ConfigDao configDao,
            ConfigDto configDto
    ) {
        configDao.saveConfig(configDto);
    }

    public void flush(EntityManager em) {
        em.flush();
        em.clear();
    }
}

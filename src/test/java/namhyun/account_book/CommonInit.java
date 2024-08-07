package namhyun.account_book;

import jakarta.persistence.EntityManager;
import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.dao.MemberDao;
import namhyun.account_book.dto.*;
import namhyun.account_book.enums.SendType;
import namhyun.account_book.enums.UserType;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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
        memberDto.setUseYn("Y");

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
        accountBookDto.setYears(String.valueOf(LocalDateTime.now().getYear()));
        accountBookDto.setMonths(String.valueOf(LocalDateTime.now().getMonthValue()));
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

    public void assertFindSendDto(SendDto findSendDto, SendDto sendDto) {
        assertThat(findSendDto.getId()).isNotNull();
        assertThat(findSendDto.getId()).isNotEqualTo(0L);
        assertThat(findSendDto.getCreatedAt()).isNotNull();
        assertThat(findSendDto.getCreatedBy()).isEqualTo(initMemberDto().getId());
        assertThat(findSendDto.getMemberDto().getId()).isEqualTo(initMemberDto().getId());
        assertThat(findSendDto.equals(sendDto)).isTrue();
    }

    public void assertFindStatisticsDto(StatisticsDto findStatisticsDto, StatisticsDto statisticsDto) {
        assertThat(findStatisticsDto.getId()).isNotNull();
        assertThat(findStatisticsDto.getId()).isNotEqualTo(0L);
        assertThat(findStatisticsDto.getCreatedAt()).isNotNull();
        assertThat(findStatisticsDto.getCreatedBy()).isEqualTo(initMemberDto().getId());
        assertThat(findStatisticsDto.getMemberDto().getId()).isEqualTo(initMemberDto().getId());
        assertThat(findStatisticsDto.equals(statisticsDto)).isTrue();
    }

    public void assertFindAccountBookDto(AccountBookDto findAccountBookDto, AccountBookDto accountBookDto) {
        assertThat(findAccountBookDto.getId()).isNotNull();
        assertThat(findAccountBookDto.getId()).isNotEqualTo(0L);
        assertThat(findAccountBookDto.getCreatedAt()).isNotNull();
        assertThat(findAccountBookDto.getCreatedBy()).isEqualTo(initMemberDto().getId());
        assertThat(findAccountBookDto.getMemberDto().getId()).isEqualTo(initMemberDto().getId());
        assertThat(findAccountBookDto.equals(accountBookDto)).isTrue();
    }

    public void assertFindMemberDto(MemberDto findMemberDto, MemberDto memberDto) {
        assertThat(findMemberDto.getId()).isNotNull();
        assertThat(findMemberDto.getCreatedAt()).isNotNull();
        assertThat(findMemberDto.getCreatedBy()).isEqualTo(initMemberDto().getId());
        assertThat(findMemberDto.equals(memberDto)).isTrue();
    }
}

package namhyun.account_book.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.enums.SendType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SendServiceTest {

    CommonInit commonInit = new CommonInit();
    SendDto sendDto;
    StatisticsDto statisticsDto;
    ConfigDto configDto;
    MemberDto memberDto;

    @Autowired
    SendService sendService;

    @Autowired
    MemberService memberService;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    ConfigService configService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        sendDto = commonInit.initSendDto();
        statisticsDto = commonInit.initStatisticsDto();
        configDto = commonInit.initConfigDto();
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("SendService.createSend()_Default")
    void createSendDefault() {
        statisticsService.saveStatistics(statisticsDto);
        configService.saveConfig(configDto);
        SendDto createdSendDto = sendService.createSend(
                null,
                null,
                null,
                sendDto.getMemberDto().getId()
        );
        SendDto savedSendDto = sendService.saveSend(createdSendDto);

        assertThat(savedSendDto).isNotNull();
        assertThat(savedSendDto.getId()).isNotNull();
        assertThat(savedSendDto.getMemberDto().getId()).isEqualTo(createdSendDto.getMemberDto().getId());
        assertThat(savedSendDto.getSendType()).isEqualTo(SendType.MAIL);
        assertThat(savedSendDto.getMsg()).isNotNull();
        assertThat(savedSendDto.getMsg()).isNotEmpty();
        assertThat(savedSendDto.getSendTime()).isNotNull();
    }

    @Test
    @DisplayName("SendService.createSend()_Custom")
    void createSendCustom() {
        statisticsService.saveStatistics(statisticsDto);
        configService.saveConfig(configDto);
        SendDto createdSendDto = sendService.createSend(
                sendDto.getSendType(),
                sendDto.getMsg(),
                sendDto.getSendTime(),
                sendDto.getMemberDto().getId()
        );

        SendDto savedSendDto = sendService.saveSend(createdSendDto);

        assertThat(savedSendDto).isNotNull();
        assertThat(savedSendDto.getId()).isNotNull();
        assertThat(savedSendDto.getMemberDto().getId()).isEqualTo(createdSendDto.getMemberDto().getId());
        assertThat(savedSendDto.getSendType()).isEqualTo(sendDto.getSendType());
        assertThat(savedSendDto.getMsg()).isNotNull();
        assertThat(savedSendDto.getMsg()).isNotEmpty();
        assertThat(savedSendDto.getMsg()).isEqualTo(sendDto.getMsg());
        assertThat(savedSendDto.getSendTime()).isNotNull();
        assertThat(savedSendDto.getSendTime()).isEqualTo(sendDto.getSendTime());
    }

    @Test
    @DisplayName("SendService.getSendListByMemberId()")
    public void getSendListByMemberId() {
        // member, config, statistics 저장
        MemberDto savedMember = memberService.saveMember(memberDto);
        commonInit.flush(em);
        ConfigDto savedConfig = configService.saveConfig(configDto);
        statisticsService.saveStatistics(statisticsDto);

        // send 저장
        SendDto send = sendService.createSend(
                savedConfig.getCustomSendType(),
                savedConfig.getCustomMsg(),
                savedConfig.getCustomSendTime(),
                savedMember.getId()
        );
        SendDto savedSend = sendService.saveSend(send);

        List<SendDto> result = sendService.getSendListByMemberId(savedMember.getId());
        assertThat(result.size()).isEqualTo(1);
        commonInit.assertFindSendDto(result.get(0), savedSend);
    }
}

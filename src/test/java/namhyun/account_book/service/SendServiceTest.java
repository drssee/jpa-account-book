package namhyun.account_book.service;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.enums.SendType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class SendServiceTest {

    CommonInit commonInit = new CommonInit();
    SendDto sendDto;
    StatisticsDto statisticsDto;
    ConfigDto configDto;

    @Autowired
    SendService sendService;

    @Autowired
    MemberService memberService;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    ConfigService configService;

    @BeforeEach
    void init() {
        sendDto = commonInit.initSendDto();
        statisticsDto = commonInit.initStatisticsDto();
        configDto = commonInit.initConfigDto();
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
}

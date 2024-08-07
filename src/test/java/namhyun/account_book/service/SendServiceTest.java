package namhyun.account_book.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dao.SendDao;
import namhyun.account_book.dto.*;
import namhyun.account_book.enums.SendType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
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
    SendDao sendDao;

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
        memberService.saveMember(memberDto);
        commonInit.flush(em);
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
        memberService.saveMember(memberDto);
        commonInit.flush(em);
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

    @Test
    @DisplayName("SendService.doSend()")
    public void doSend() {
        memberService.saveMember(memberDto);
        commonInit.flush(em);
        statisticsService.saveStatistics(statisticsDto);
        configService.saveConfig(configDto);
        SendDto createdSendDto = sendService.createSend(
                null,
                null,
                null,
                sendDto.getMemberDto().getId()
        );
        SendDto savedSendDto = sendService.saveSend(createdSendDto);
        // 미발송목록 조회
        List<SendDto> notYetSendList = sendDao.getNotYetSendList();
        // 발송
        SendResult sendResult = sendService.doSend();

        assertThat(notYetSendList.size()).isEqualTo(sendResult.getSize());
        // 발송시간이 현재시간 ~ +5분 사이에 있는 목록 찾아서 발송했는지
        sendResult.getSendTimes().forEach(e -> {
            if (e.getSendTime().isAfter(LocalDateTime.now().plusMinutes(5))
                    || e.getSendTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("발송시간 검증 실패");
            }
        });
        // 성공했는지
        assertThat(sendResult.getFail()).isEqualTo(0);
    }
}

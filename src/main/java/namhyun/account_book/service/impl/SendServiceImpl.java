package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dao.SendDao;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.enums.SendType;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.common.SendManager;
import namhyun.account_book.service.SendService;
import namhyun.account_book.service.StatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final SendDao sendDao;
    private final StatisticsService statisticsService;
    private final ConfigService configService;
    private final SendType DEFAULT_SEND_TYPE = SendType.MAIL;
    private final int DEFAULT_SEND_MINUTES = 5;
    private final ModelMapper modelMapper;
    private final SendManager sendManager;

    @Override
    public SendDto saveSend(SendDto sendDto) {
        return sendDao.saveSend(sendDto);
    }

    @Override
    public SendDto createSend(
            SendType sendType,
            String customMsg,
            LocalDateTime sendTime,
            String memberId) {
        if (memberId == null) throw new RuntimeException("createSend() - MemberId is null");

        SendDto sendDto = new SendDto();

        if (sendType == null) {
            sendDto.setSendType(DEFAULT_SEND_TYPE);
        } else {
            sendDto.setSendType(sendType);
        }

        if (customMsg == null || customMsg.isEmpty()) {
            sendDto.setMsg(createDefaultCustomMsg(memberId));
        } else {
            sendDto.setMsg(customMsg);
        }

        if (sendTime == null) {
            sendDto.setSendTime(
                    LocalDateTime.now().plusMinutes(DEFAULT_SEND_MINUTES)
            );
        } else {
            sendDto.setSendTime(sendTime);
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);
        sendDto.setMemberDto(memberDto);
        return sendDto;
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

    @Override
    public List<SendDto> getSendListByMemberId(String memberId) {
        return sendDao.getSendListByMemberId(memberId);
    }

    @Override
    public SendResult doSend() {
        // 미발송목록 조회
        List<SendDto> notYetSendList = sendDao.getNotYetSendList();
        List<SendDto> needToSendList = new ArrayList<>();
        notYetSendList.forEach(e -> {
            LocalDateTime sendTime = e.getSendTime();
            // 발송시간이 현재시간 ~ +5분 사이에 있는 목록 찾아서 발송
            if (sendTime.isBefore(LocalDateTime.now().plusMinutes(DEFAULT_SEND_MINUTES))
                    && sendTime.isAfter(LocalDateTime.now())) {

                needToSendList.add(e);
            }
        });

        return sendManager.doSend(needToSendList);
    }
}

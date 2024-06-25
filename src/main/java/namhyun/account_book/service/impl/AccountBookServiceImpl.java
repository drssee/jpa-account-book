package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.service.AccountBookService;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.service.SendService;
import namhyun.account_book.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookDao accountBookDao;
    private final StatisticsService statisticsService;
    private final ConfigService configService;
    private final SendService sendService;

    @Override
    public void pay(AccountBookDto accountBookDto) {
        // 1. account book 저장
        AccountBookDto savedAccountBook = accountBookDao.saveAccountBook(accountBookDto);
        log.debug("savedAccountBook: {}", savedAccountBook.getId());

        // 2. statistics 업데이트
        StatisticsDto statisTicsDto = new StatisticsDto();
        statisTicsDto.setPayments(savedAccountBook.getPrice());
        statisTicsDto.setMemberDto(savedAccountBook.getMemberDto());
        StatisticsDto savedStatistics = statisticsService.saveStatistics(statisTicsDto);
        log.debug("savedStatistics: {}", savedStatistics.getId());

        // 3. config 조회
        ConfigDto findConfig = configService.getConfigByMemberId(savedAccountBook.getMemberDto().getId());
        // 4. 메시지 발송여부 true 일 경우 send 저장
        if (findConfig.isCanSendMessage()) {
            SendDto sendDto = new SendDto();
            sendDto.setMemberDto(savedAccountBook.getMemberDto());
            SendDto savedSend = sendService.saveSend(sendDto);
            log.debug("savedSend: {}", savedSend.getId());

            // 5. 한도를 초과했을 경우 추과 메시지 send
        }
    }
}

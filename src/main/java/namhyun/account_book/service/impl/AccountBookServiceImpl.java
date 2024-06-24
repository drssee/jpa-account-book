package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.dto.AccountBookDto;
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
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookDao accountBookDao;
    private final StatisticsService statisticsService;
    private final ConfigService configService;
    private final SendService sendService;

    @Override
    public void pay(AccountBookDto accountBookDto) {
        // 1. account book 저장
        accountBookDao.saveAccountBook(accountBookDto);
        // 2. statistics 업데이트
        StatisticsDto statisTicsDto = new StatisticsDto();
        statisTicsDto.setPayments(accountBookDto.getPrice());
        statisTicsDto.setMember(accountBookDto.getMember());

        // 3. config 조회
        // 4. 메시지 발송여부 true 일 경우 send 저장
        // 5. 한도 알림여부 true 일 경우 경고메시지 작성
    }
}

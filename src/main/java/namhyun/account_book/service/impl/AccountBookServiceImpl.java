package namhyun.account_book.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookDao accountBookDao;
    private final StatisticsService statisticsService;
    private final MemberService memberService;
    private final ConfigService configService;
    private final SendService sendService;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public AccountBookDto pay(AccountBookDto accountBookDto) {

        // 1. account book 저장
        AccountBookDto savedAccountBookDto = accountBookDao.saveAccountBook(accountBookDto);
        log.debug("savedAccountBook: {}", savedAccountBookDto.getId());

        // 2. statistics 업데이트
        StatisticsDto statisTicsDto = new StatisticsDto();
        statisTicsDto.setPayments(savedAccountBookDto.getPrice());
        statisTicsDto.setMemberDto(savedAccountBookDto.getMemberDto());
        statisTicsDto.setNeedSum(accountBookDto.isNeedSum());
        statisTicsDto.setYear(accountBookDto.getYears());
        statisTicsDto.setMonth(accountBookDto.getMonths());
        StatisticsDto savedStatistics = statisticsService.saveStatistics(statisTicsDto);
        log.debug("savedStatistics: {}", savedStatistics.getId());

        send(savedAccountBookDto.getMemberDto().getId());

        return savedAccountBookDto;
    }

    @Override
    public AccountBookDto getAccountBookById(Long id) {
        return accountBookDao.getAccountBookById(id);
    }

    @Override
    public AccountBookDto updateAccountBook(AccountBookDto accountBookDto) {

        // update Statistics
        try {
            StatisticsDto findStatisticsDto = statisticsService.getStatisticsByDateAndMember(
                    Utils.getSearchCondition(accountBookDto.getYears(), accountBookDto.getMonths(), accountBookDto.getMemberDto())
            );
            AccountBookDto findAccountBook = accountBookDao.getAccountBookById(accountBookDto.getId());

            if (accountBookDto.getYears().equals(findAccountBook.getYears())
                    && accountBookDto.getMonths().equals(findAccountBook.getMonths())) {

                int gapAbs = Math.abs(accountBookDto.getPrice() - findAccountBook.getPrice());
                // 0이면 업데이트 할 필요 없음
                if (gapAbs != 0) {
                    int priceGap = accountBookDto.getPrice() > findAccountBook.getPrice() ? gapAbs : gapAbs * -1;
                    findStatisticsDto.setPayments(priceGap);
                    findStatisticsDto.setNeedSum(true);
                    StatisticsDto savedStatistics = statisticsService.saveStatistics(findStatisticsDto);
                    log.debug("savedStatistics: {}", savedStatistics.getId());
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.debug("updateAccountBook - Years or Months changed -> save new statistics");

            StatisticsDto statisTicsDto = new StatisticsDto();
            statisTicsDto.setPayments(accountBookDto.getPrice());
            statisTicsDto.setMemberDto(accountBookDto.getMemberDto());
            statisTicsDto.setYear(accountBookDto.getYears());
            statisTicsDto.setMonth(accountBookDto.getMonths());
            StatisticsDto savedStatistics = statisticsService.saveStatistics(statisTicsDto);

            log.debug("savedStatistics: {}", savedStatistics.getId());
        }

        // update AccountBook
        AccountBookDto updatedAccountBook = accountBookDao.updateAccountBook(
                accountBookDto.getId(),
                accountBookDto.getPayPurpose(),
                accountBookDto.getTitle(),
                accountBookDto.getPrice(),
                accountBookDto.getYears(),
                accountBookDto.getMonths()
        );
        log.debug("updatedAccountBook: {}", updatedAccountBook.getId());

        // send 작성
        send(accountBookDto.getMemberDto().getId());

        return updatedAccountBook;
    }

    private void send(String memberId) {
        // 3. config 조회
        ConfigDto findConfig = configService.getConfigByMemberId(memberId);
        // 4. 메시지 발송여부 true 일 경우 send 저장
        if (findConfig.isCanSendMessage()) {
            SendDto createdSend = sendService.createSend(
                    findConfig.getCustomSendType(),
                    findConfig.getCustomMsg(),
                    findConfig.getCustomSendTime(),
                    findConfig.getMemberDto().getId()
            );
            SendDto savedSend = sendService.saveSend(createdSend);
            log.debug("savedSend: {}", savedSend.getId());
        }
    }
}

package namhyun.account_book.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.domain.Member;
import namhyun.account_book.dto.*;
import namhyun.account_book.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        statisTicsDto.setYear(accountBookDto.getYear());
        statisTicsDto.setMonth(accountBookDto.getMonth());
        StatisticsDto savedStatistics = statisticsService.saveStatistics(statisTicsDto);
        log.debug("savedStatistics: {}", savedStatistics.getId());

        // 3. config 조회
        ConfigDto findConfig = configService.getConfigByMemberId(savedAccountBookDto.getMemberDto().getId());
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

        return savedAccountBookDto;
    }

    @Override
    public AccountBookDto getAccountBookById(Long id) {
        return accountBookDao.getAccountBookById(id);
    }
}

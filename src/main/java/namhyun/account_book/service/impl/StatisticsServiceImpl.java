package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.StatisticsDao;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDao statisticsDao;

    @Override
    public StatisticsDto saveStatistics(StatisticsDto statisticsDto) {
        return null;
    }
}

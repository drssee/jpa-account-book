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
        // 존재하면 통계 최신화 update 없으면 insert
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(statisticsDto);
        if (findStatisticsDto == null) {
            return statisticsDao.saveStatistics(statisticsDto);
        }
        return statisticsDao.updateStatistics(statisticsDto);
    }

    @Override
    public StatisticsDto getStatisticsByDateAndMember(StatisticsDto statisticsDto) {
        return statisticsDao.getStatisticsByDateAndMember(statisticsDto);
    }
}

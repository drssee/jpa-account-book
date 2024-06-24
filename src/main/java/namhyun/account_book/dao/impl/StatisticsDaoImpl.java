package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.StatisticsDao;
import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.StatisticsDto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {

    @Override
    public List<StatisticsDto> getStatistics(SearchCondition searchCondition) {
        return List.of();
    }

    @Override
    public StatisticsDto saveStatistics(StatisticsDto statisticsDto) {
        return null;
    }

    @Override
    public StatisticsDto updateStatistics(StatisticsDto statisticsDto) {
        return null;
    }

    @Override
    public StatisticsDto getStatisticsById(Long id) {
        return null;
    }
}

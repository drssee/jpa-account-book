package namhyun.account_book.dao;

import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.StatisticsDto;

import java.util.List;

public interface StatisticsDao {

    StatisticsDto saveStatistics(StatisticsDto statisticsDto);
    StatisticsDto updateStatistics(StatisticsDto statisticsDto);
    StatisticsDto getStatisticsById(Long id);
    StatisticsDto getStatisticsByDateAndMember(StatisticsDto statisticsDto);
    List<StatisticsDto> getStatistics(SearchCondition searchCondition);
}

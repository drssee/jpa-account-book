package namhyun.account_book.service;

import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.StatisticsDto;

public interface StatisticsService {

    StatisticsDto saveStatistics(StatisticsDto statisticsDto);
    StatisticsDto getStatisticsByDateAndMember(SearchCondition searchCondition);
}

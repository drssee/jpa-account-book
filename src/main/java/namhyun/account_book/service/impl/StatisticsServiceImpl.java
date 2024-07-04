package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dao.StatisticsDao;
import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.service.StatisticsService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDao statisticsDao;

    @Override
    public StatisticsDto saveStatistics(StatisticsDto statisticsDto) {
        try {
            // 존재하면 통계 최신화 update 없으면 insert
            statisticsDao.getStatisticsByDateAndMember(
                    Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
            );
            return statisticsDao.updateStatistics(statisticsDto);
        } catch (EmptyResultDataAccessException e) {
            return statisticsDao.saveStatistics(statisticsDto);
        }
    }

    @Override
    public StatisticsDto getStatisticsByDateAndMember(SearchCondition searchCondition) {
        return statisticsDao.getStatisticsByDateAndMember(searchCondition);
    }
}

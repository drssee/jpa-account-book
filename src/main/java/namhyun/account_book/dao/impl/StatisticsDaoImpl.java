package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dao.StatisticsDao;
import namhyun.account_book.domain.Member;
import namhyun.account_book.domain.Statistics;
import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.StatisticsDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatisticsDaoImpl implements StatisticsDao {

    @PersistenceContext
    private EntityManager em;

    private final ModelMapper modelMapper;

    @Override
    public List<StatisticsDto> getStatistics(SearchCondition searchCondition) {
        return List.of();
    }

    @Override
    public StatisticsDto saveStatistics(StatisticsDto statisticsDto) {
        statisticsDto.setCreatedAt(LocalDateTime.now());
        statisticsDto.setCreatedBy(statisticsDto.getMemberDto().getId());
        Statistics statistics = modelMapper.map(statisticsDto, Statistics.class);
        em.persist(statistics);
        return modelMapper.map(statistics, StatisticsDto.class);
    }

    @Override
    public StatisticsDto updateStatistics(StatisticsDto statisticsDto) {
        StatisticsDto findStatisticsDto = this.getStatisticsByDateAndMember(
                        Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
                );
        Statistics statistics = em.find(Statistics.class, modelMapper.map(findStatisticsDto, Statistics.class).getId());
        statistics.setUpdatedAt(LocalDateTime.now());
        statistics.setUpdatedBy(statisticsDto.getMemberDto().getId());
        // 부분합 or 전체업데이트 적용
        if (statisticsDto.isNeedSum())  {
            statistics.setPayments(statistics.getPayments() + statisticsDto.getPayments());
        } else {
            statistics.setPayments(statisticsDto.getPayments());
        }
        return modelMapper.map(statistics, StatisticsDto.class);
    }

    @Override
    public StatisticsDto getStatisticsById(Long id) {
        Statistics statistics = em.find(Statistics.class, id);
        return modelMapper.map(statistics, StatisticsDto.class);
    }

    @Override
    public StatisticsDto getStatisticsByDateAndMember(SearchCondition searchCondition) {
        Member member = em.find(Member.class, searchCondition.getMemberDto().getId());
        String query = "select s from Statistics s where s.year = :year and s.month = :month and s.member = :member";
        Statistics result = em.createQuery(query, Statistics.class)
                .setParameter("year", searchCondition.getYear())
                .setParameter("month", searchCondition.getMonth())
                .setParameter("member", member)
                .getSingleResult();
        return modelMapper.map(result, StatisticsDto.class);
    }
}

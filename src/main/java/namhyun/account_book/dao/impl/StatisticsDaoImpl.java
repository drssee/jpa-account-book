package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
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
        em.persist(statistics.getMember());
        return modelMapper.map(statistics, StatisticsDto.class);
    }

    @Override
    public StatisticsDto updateStatistics(StatisticsDto statisticsDto) {
        StatisticsDto findStatisticsDto = this.getStatisticsByDateAndMember(statisticsDto);
        Statistics statistics = em.find(Statistics.class, modelMapper.map(findStatisticsDto, Statistics.class).getId());
        statistics.setUpdatedAt(LocalDateTime.now());
        statistics.setUpdatedBy(statisticsDto.getMemberDto().getId());
        statistics.setPayments(statisticsDto.getPayments());
        return modelMapper.map(statistics, StatisticsDto.class);
    }

    @Override
    public StatisticsDto getStatisticsById(Long id) {
        return null;
    }

    @Override
    public StatisticsDto getStatisticsByDateAndMember(StatisticsDto statisticsDto) {
        String query = "select s from Statistics s where s.year = :year and s.month = :month and s.member = :member";
        Statistics result = em.createQuery(query, Statistics.class)
                .setParameter("year", statisticsDto.getYear())
                .setParameter("month", statisticsDto.getMonth())
                .setParameter("member", modelMapper.map(statisticsDto.getMemberDto(), Member.class))
                .getSingleResult();
        em.persist(result);
        return modelMapper.map(result, StatisticsDto.class);
    }
}

package namhyun.account_book.dao;

import namhyun.account_book.CommonInit;
import namhyun.account_book.Utils;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.StatisticsDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StatisticsDaoTest {

    CommonInit commonInit = new CommonInit();
    MemberDto memberDto;
    StatisticsDto statisticsDto;

    @Autowired
    StatisticsDao statisticsDao;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
        statisticsDto = commonInit.initStatisticsDto();
    }

    @Test
    @DisplayName("StatisticsDao.saveStatistics")
    void saveStatistics() {
        StatisticsDto savedStatistics = statisticsDao.saveStatistics(statisticsDto);

        Assertions.assertThat(savedStatistics).isNotNull();
        Assertions.assertThat(savedStatistics.getId()).isNotNull();
        Assertions.assertThat(savedStatistics.getId()).isNotEqualTo(0L);
        Assertions.assertThat(savedStatistics.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedStatistics.getCreatedBy()).isEqualTo(statisticsDto.getMemberDto().getId());
    }

    @Test
    @DisplayName("StatisticsDao.updateStatistics")
    void updateStatistics() {
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );
        int res = findStatisticsDto.getPayments() + 10000;
        findStatisticsDto.setPayments(res);

        Assertions.assertThat(findStatisticsDto.getId()).isNotNull();

        StatisticsDto updatedStatistics = statisticsDao.updateStatistics(findStatisticsDto);
        Assertions.assertThat(updatedStatistics).isNotNull();
        Assertions.assertThat(updatedStatistics.getId()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedAt()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedBy()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedBy()).isEqualTo(findStatisticsDto.getMemberDto().getId());
        Assertions.assertThat(updatedStatistics.getPayments()).isEqualTo(res);
    }

    @Test
    @DisplayName("StatisticsDao.updateStatistics(needSome)")
    void updateStatisticsWithNeedSum() {
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );
        findStatisticsDto.setNeedSum(true);
        int res = findStatisticsDto.getPayments() + 10000;
        findStatisticsDto.setPayments(10000);

        Assertions.assertThat(findStatisticsDto.getId()).isNotNull();


        StatisticsDto updatedStatistics = statisticsDao.updateStatistics(findStatisticsDto);
        Assertions.assertThat(updatedStatistics).isNotNull();
        Assertions.assertThat(updatedStatistics.getId()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedAt()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedBy()).isNotNull();
        Assertions.assertThat(updatedStatistics.getUpdatedBy()).isEqualTo(findStatisticsDto.getMemberDto().getId());
        Assertions.assertThat(updatedStatistics.getPayments()).isEqualTo(res);
    }

    @Test
    @DisplayName("StatisticsDao.getStatisticsByDateAndMember")
    void getStatisticsByDateAndMember() {
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );

        Assertions.assertThat(findStatisticsDto).isNotNull();
        Assertions.assertThat(findStatisticsDto.getYear()).isEqualTo(statisticsDto.getYear());
        Assertions.assertThat(findStatisticsDto.getMonth()).isEqualTo(statisticsDto.getMonth());
        Assertions.assertThat(findStatisticsDto.getMemberDto().getId()).isEqualTo(statisticsDto.getMemberDto().getId());
    }
}

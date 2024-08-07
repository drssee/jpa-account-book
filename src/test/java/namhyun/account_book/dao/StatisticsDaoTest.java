package namhyun.account_book.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.common.Utils;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.StatisticsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StatisticsDaoTest {

    CommonInit commonInit = new CommonInit();
    MemberDto memberDto;
    StatisticsDto statisticsDto;

    @Autowired
    StatisticsDao statisticsDao;

    @Autowired
    MemberDao memberDao;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
        statisticsDto = commonInit.initStatisticsDto();
    }

    @Test
    @DisplayName("StatisticsDao.saveStatistics")
    void saveStatistics() {
        StatisticsDto savedStatistics = statisticsDao.saveStatistics(statisticsDto);

        assertThat(savedStatistics).isNotNull();
        assertThat(savedStatistics.getId()).isNotNull();
        assertThat(savedStatistics.getId()).isNotEqualTo(0L);
        assertThat(savedStatistics.getCreatedAt()).isNotNull();
        assertThat(savedStatistics.getCreatedBy()).isEqualTo(statisticsDto.getMemberDto().getId());
    }

    @Test
    @DisplayName("StatisticsDao.updateStatistics")
    void updateStatistics() {
        commonInit.saveMember(memberDao, memberDto);
        commonInit.flush(em);
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );
        int res = findStatisticsDto.getPayments() + 10000;
        findStatisticsDto.setPayments(res);

        assertThat(findStatisticsDto.getId()).isNotNull();

        StatisticsDto updatedStatistics = statisticsDao.updateStatistics(findStatisticsDto);
        assertThat(updatedStatistics).isNotNull();
        assertThat(updatedStatistics.getId()).isNotNull();
        assertThat(updatedStatistics.getUpdatedAt()).isNotNull();
        assertThat(updatedStatistics.getUpdatedBy()).isNotNull();
        assertThat(updatedStatistics.getUpdatedBy()).isEqualTo(findStatisticsDto.getMemberDto().getId());
        assertThat(updatedStatistics.getPayments()).isEqualTo(res);
    }

    @Test
    @DisplayName("StatisticsDao.updateStatistics(needSome)")
    void updateStatisticsWithNeedSum() {
        commonInit.saveMember(memberDao, memberDto);
        commonInit.flush(em);
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );
        int res = findStatisticsDto.getPayments() + 10000;
        findStatisticsDto.setPayments(10000);
        findStatisticsDto.setNeedSum(true);

        assertThat(findStatisticsDto.getId()).isNotNull();


        StatisticsDto updatedStatistics = statisticsDao.updateStatistics(findStatisticsDto);
        assertThat(updatedStatistics).isNotNull();
        assertThat(updatedStatistics.getId()).isNotNull();
        assertThat(updatedStatistics.getUpdatedAt()).isNotNull();
        assertThat(updatedStatistics.getUpdatedBy()).isNotNull();
        assertThat(updatedStatistics.getUpdatedBy()).isEqualTo(findStatisticsDto.getMemberDto().getId());
        assertThat(updatedStatistics.getPayments()).isEqualTo(res);
    }

    @Test
    @DisplayName("StatisticsDao.getStatisticsByDateAndMember")
    void getStatisticsByDateAndMember() {
        // 멤버o통계o -> duplicatekey
        // 멤버o통계x -> no result 정상적
        // 멤버x통계o -> 통계 저장시 멤버 영속화 해서 성공함
        // 멤버x통계x -> no result 정상적
        commonInit.saveMember(memberDao, memberDto);
        commonInit.flush(em);
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsDao.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );

        assertThat(findStatisticsDto).isNotNull();
        assertThat(findStatisticsDto.getYear()).isEqualTo(statisticsDto.getYear());
        assertThat(findStatisticsDto.getMonth()).isEqualTo(statisticsDto.getMonth());
        assertThat(findStatisticsDto.getMemberDto().getId()).isEqualTo(statisticsDto.getMemberDto().getId());
    }

    @Test
    @DisplayName("StatisticsDao.getStatisticsById()")
    void getStatisticsById() {
        commonInit.saveMember(memberDao, memberDto);
        commonInit.flush(em);
        StatisticsDto savedStatistics = statisticsDao.saveStatistics(statisticsDto);
        StatisticsDto findStatistics = statisticsDao.getStatisticsById(savedStatistics.getId());
        assertThat(findStatistics).isNotNull();
        commonInit.assertFindStatisticsDto(findStatistics, savedStatistics);
    }
}

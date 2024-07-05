package namhyun.account_book.service;

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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StatisticsServiceTest {

    CommonInit commonInit = new CommonInit();
    StatisticsDto statisticsDto;
    MemberDto memberDto;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        statisticsDto = commonInit.initStatisticsDto();
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("StatisticsService.saveStatistics()")
    void saveStatistics() {
        StatisticsDto savedStatisticsDto = statisticsService.saveStatistics(statisticsDto);

        assertThat(savedStatisticsDto).isNotNull();
        assertThat(savedStatisticsDto.getId()).isNotNull();
        assertThat(savedStatisticsDto.getPayments()).isNotEqualTo(0);
        assertThat(savedStatisticsDto.getPayments()).isEqualTo(statisticsDto.getPayments());
        assertThat(savedStatisticsDto.getMemberDto().getId()).isNotNull();
        assertThat(savedStatisticsDto.getMemberDto().getId()).isEqualTo(statisticsDto.getMemberDto().getId());
    }

    @Test
    @DisplayName("StatisticsService.saveStatistics()_WithUpdate")
    void saveStatisticsWithUpdate() {
        commonInit.saveMember(memberService, memberDto);
        commonInit.flush(em);
        int loop = 10;
        for (int i = 0; i < loop; i++) {
            statisticsDto.setNeedSum(true);
            StatisticsDto savedStatisticsDto = statisticsService.saveStatistics(statisticsDto);
            System.out.println("i = " + i + ", sum = " + statisticsDto.getPayments() * (i + 1));

            assertThat(savedStatisticsDto).isNotNull();
            assertThat(savedStatisticsDto.getId()).isNotNull();
            assertThat(savedStatisticsDto.getPayments()).isNotEqualTo(0);
            assertThat(savedStatisticsDto.getPayments()).isEqualTo(statisticsDto.getPayments() * (i + 1));
            assertThat(savedStatisticsDto.getMemberDto().getId()).isNotNull();
            assertThat(savedStatisticsDto.getMemberDto().getId()).isEqualTo(statisticsDto.getMemberDto().getId());
        }
    }

    @Test
    @DisplayName("StatisticsService.getStatisticsByDateAndMember()")
    void getStatisticsByDateAndMember() {
        commonInit.saveMember(memberService, memberDto);
        commonInit.flush(em);
        saveStatistics();
        StatisticsDto findStatisticsDto = statisticsService.getStatisticsByDateAndMember(
                Utils.getSearchCondition(statisticsDto.getYear(), statisticsDto.getMonth(), statisticsDto.getMemberDto())
        );

        assertThat(findStatisticsDto).isNotNull();
        assertThat(findStatisticsDto.getId()).isNotNull();
        assertThat(findStatisticsDto.getMemberDto().getId()).isEqualTo(statisticsDto.getMemberDto().getId());
        assertThat(findStatisticsDto.getYear()).isEqualTo(String.valueOf(LocalDateTime.now().getYear()));
        assertThat(findStatisticsDto.getMonth()).isEqualTo(String.valueOf(LocalDateTime.now().getMonth().getValue()));
    }
}

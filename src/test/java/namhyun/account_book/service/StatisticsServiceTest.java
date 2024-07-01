package namhyun.account_book.service;

import namhyun.account_book.CommonInit;
import namhyun.account_book.Utils;
import namhyun.account_book.dto.StatisticsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class StatisticsServiceTest {

    CommonInit commonInit = new CommonInit();
    StatisticsDto statisticsDto;

    @Autowired
    StatisticsService statisticsService;

    @BeforeEach
    void init() {
        statisticsDto = commonInit.initStatisticsDto();
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
        int loop = 10;
        for (int i = 0; i < loop; i++) {
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

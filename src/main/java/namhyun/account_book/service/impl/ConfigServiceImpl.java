package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.StatisticsDto;
import namhyun.account_book.enums.SendType;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigDao configDao;


    private final StatisticsService statisticsService;

    @Override
    public ConfigDto getConfigByMemberId(String memberId) {
        return configDao.getConfigByMemberId(memberId);
    }

    @Override
    public ConfigDto saveConfig(ConfigDto configDto) {
        return configDao.saveConfig(configDto);
    }
}

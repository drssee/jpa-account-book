package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigDao configDao;

    @Override
    public ConfigDto getConfigByMemberId(String memberId) {
        return null;
    }
}

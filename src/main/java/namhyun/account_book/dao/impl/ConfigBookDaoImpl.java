package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SearchCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConfigBookDaoImpl implements ConfigDao {

    @Override
    public ConfigDao getConfigById(Long id) {
        return null;
    }

    @Override
    public ConfigDao saveConfig(ConfigDto configDto) {
        return null;
    }

    @Override
    public ConfigDao updateConfig(ConfigDto configDto) {
        return null;
    }

    @Override
    public List<ConfigDto> getConfigs(SearchCondition searchCondition) {
        return List.of();
    }
}

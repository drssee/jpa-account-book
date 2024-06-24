package namhyun.account_book.dao;

import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface ConfigDao {

    ConfigDao saveConfig(ConfigDto configDto);
    ConfigDao updateConfig(ConfigDto configDto);
    ConfigDao getConfigById(Long id);
    List<ConfigDto> getConfigs(SearchCondition searchCondition);
}

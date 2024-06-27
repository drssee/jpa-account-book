package namhyun.account_book.dao;

import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface ConfigDao {

    ConfigDto saveConfig(ConfigDto configDto);
    ConfigDto updateConfig(ConfigDto configDto);
    ConfigDto getConfigById(Long id);
    ConfigDto getConfigByMemberId(String memberId);
    List<ConfigDto> getConfigs(SearchCondition searchCondition);
}

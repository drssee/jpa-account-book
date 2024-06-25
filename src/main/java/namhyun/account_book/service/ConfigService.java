package namhyun.account_book.service;

import namhyun.account_book.dto.ConfigDto;

public interface ConfigService {

    ConfigDto getConfigByMemberId(String memberId);
}

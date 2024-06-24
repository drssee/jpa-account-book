package namhyun.account_book.dao;

import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface AccountBookDao {

    AccountBookDto saveAccountBook(AccountBookDto accountBookDto);
    AccountBookDto updateAccountBook(AccountBookDto accountBookDto);
    AccountBookDto getAccountBookById(Long id);
    List<AccountBookDto> getAccountBooks(SearchCondition searchCondition);
}

package namhyun.account_book.dao;

import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.PayPurposeDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface AccountBookDao {

    AccountBookDto saveAccountBook(AccountBookDto accountBookDto);
    AccountBookDto updateAccountBook(
            Long id,
            PayPurposeDto payPurposeDto,
            String title,
            int price
    );
    AccountBookDto getAccountBookById(Long id);
    List<AccountBookDto> getAccountBooks(SearchCondition searchCondition);
}

package namhyun.account_book.service;

import namhyun.account_book.dto.AccountBookDto;

public interface AccountBookService {

    AccountBookDto pay(AccountBookDto accountBookDto);
    AccountBookDto getAccountBookById(Long id);
    AccountBookDto updateAccountBook(AccountBookDto accountBookDto);
}

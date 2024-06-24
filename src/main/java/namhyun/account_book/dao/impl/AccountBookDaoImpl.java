package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.SearchCondition;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountBookDaoImpl implements AccountBookDao {

    @Override
    public AccountBookDto getAccountBookById(Long id) {
        return null;
    }

    @Override
    public AccountBookDto saveAccountBook(AccountBookDto accountBookDto) {
        return null;
    }

    @Override
    public AccountBookDto updateAccountBook(AccountBookDto accountBookDto) {
        return null;
    }

    @Override
    public List<AccountBookDto> getAccountBooks(SearchCondition searchCondition) {
        return List.of();
    }
}

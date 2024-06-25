package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.domain.AccountBook;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.SearchCondition;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountBookDaoImpl implements AccountBookDao {

    @PersistenceContext
    private EntityManager em;

    private final ModelMapper modelMapper;

    @Override
    public AccountBookDto getAccountBookById(Long id) {
        return null;
    }

    @Override
    public AccountBookDto saveAccountBook(AccountBookDto accountBookDto) {
        accountBookDto.setCreatedAt(LocalDateTime.now());
        accountBookDto.setCreatedBy(accountBookDto.getMemberDto().getId());
        AccountBook accountBook = modelMapper.map(accountBookDto, AccountBook.class);
        em.persist(accountBook);
        return modelMapper.map(accountBook, AccountBookDto.class);
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

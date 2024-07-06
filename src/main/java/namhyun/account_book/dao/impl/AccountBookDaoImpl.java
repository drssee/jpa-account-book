package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.AccountBookDao;
import namhyun.account_book.domain.AccountBook;
import namhyun.account_book.domain.PayPurpose;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.PayPurposeDto;
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
        String query = "select b from AccountBook b where b.id = :id";
        AccountBook result = em.createQuery(query, AccountBook.class)
                .setParameter("id", id)
                .getSingleResult();
        return modelMapper.map(result, AccountBookDto.class);
    }

    @Override
    public AccountBookDto saveAccountBook(AccountBookDto accountBookDto) {
        accountBookDto.setCreatedAt(LocalDateTime.now());
        accountBookDto.setCreatedBy(accountBookDto.getMemberDto().getId());
        AccountBook accountBook = modelMapper.map(accountBookDto, AccountBook.class);
        em.persist(accountBook);
        em.persist(accountBook.getPayPurpose());
        return modelMapper.map(accountBook, AccountBookDto.class);
    }

    @Override
    public AccountBookDto updateAccountBook(
            Long id,
            PayPurposeDto payPurposeDto,
            String title,
            int price
    ) {
        AccountBook accountBook = em.find(AccountBook.class, id);
        accountBook.setPayPurpose(modelMapper.map(payPurposeDto, PayPurpose.class));
        accountBook.setTitle(title);
        accountBook.setPrice(price);
        return modelMapper.map(accountBook, AccountBookDto.class);
    }

    @Override
    public List<AccountBookDto> getAccountBooks(SearchCondition searchCondition) {
        return List.of();
    }
}

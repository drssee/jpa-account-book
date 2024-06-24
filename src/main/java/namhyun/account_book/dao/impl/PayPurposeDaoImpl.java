package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.PayPurposeDao;
import namhyun.account_book.dto.PayPurposeDto;
import namhyun.account_book.dto.SearchCondition;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PayPurposeDaoImpl implements PayPurposeDao {

    @Override
    public PayPurposeDto getPayPurposeById(Long id) {
        return null;
    }

    @Override
    public PayPurposeDto savePayPurpose(PayPurposeDto payPurposeDto) {
        return null;
    }

    @Override
    public PayPurposeDto updatePayPurpose(PayPurposeDto payPurposeDto) {
        return null;
    }

    @Override
    public List<PayPurposeDto> getPayPurposes(SearchCondition searchCondition) {
        return List.of();
    }
}

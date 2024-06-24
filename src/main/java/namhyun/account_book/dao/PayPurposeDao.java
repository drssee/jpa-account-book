package namhyun.account_book.dao;

import namhyun.account_book.dto.PayPurposeDto;
import namhyun.account_book.dto.SearchCondition;

import java.util.List;

public interface PayPurposeDao {

    PayPurposeDto savePayPurpose(PayPurposeDto payPurposeDto);
    PayPurposeDto updatePayPurpose(PayPurposeDto payPurposeDto);
    PayPurposeDto getPayPurposeById(Long id);
    List<PayPurposeDto> getPayPurposes(SearchCondition searchCondition);
}

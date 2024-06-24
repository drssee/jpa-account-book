package namhyun.account_book.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import namhyun.account_book.domain.Member;
import namhyun.account_book.domain.PayPurpose;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBookDto extends BaseDto {

    private Long id;
    private PayPurpose payPurpose;
    private Member member;
    private String title;
    private int price;
}

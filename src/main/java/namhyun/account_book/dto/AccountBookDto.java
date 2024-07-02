package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBookDto extends BaseDto {

    private Long id;
    private PayPurposeDto payPurpose;
    private MemberDto memberDto;
    private String title;
    private int price;

    private boolean isNeedSum;
}

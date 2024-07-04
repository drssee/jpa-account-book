package namhyun.account_book.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountBookDto extends BaseDto {

    private Long id;
    private PayPurposeDto payPurpose;
    private MemberDto memberDto;
    private String title;
    private int price;

    private boolean isNeedSum;
    private String year;
    private String month;
}

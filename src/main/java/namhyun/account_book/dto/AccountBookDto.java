package namhyun.account_book.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"id"})
public class AccountBookDto extends BaseDto {

    private Long id;
    private PayPurposeDto payPurpose;
    private MemberDto memberDto;
    private String title;
    private int price;

    private boolean isNeedSum;
    private String years;
    private String months;
}

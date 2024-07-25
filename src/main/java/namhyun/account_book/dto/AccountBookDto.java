package namhyun.account_book.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"id"})
@ToString
public class AccountBookDto extends BaseDto {

    private Long id;

    @NotNull
    private PayPurposeDto payPurpose;

    @NotNull
    private MemberDto memberDto;

    @NotBlank
    private String title;

    @NotNull
    private int price;

    private boolean isNeedSum;

    @NotNull
    private String years;

    @NotNull
    private String months;
}

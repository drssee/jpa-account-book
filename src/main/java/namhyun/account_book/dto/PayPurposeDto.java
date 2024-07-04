package namhyun.account_book.dto;

import lombok.*;
import namhyun.account_book.domain.PayPurpose;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PayPurposeDto extends BaseDto {

    private Long id;
    private String name;
    private PayPurpose parent;
    private List<PayPurposeDto> children;
}

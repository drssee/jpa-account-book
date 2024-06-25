package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import namhyun.account_book.domain.PayPurpose;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayPurposeDto extends BaseDto {

    private Long id;
    private String name;
    private PayPurpose parent;
    private List<PayPurposeDto> children;
}

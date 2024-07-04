package namhyun.account_book.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatisticsDto extends BaseDto {

    private Long id;
    private String year;
    private String month;
    private int payments;
    private MemberDto memberDto;
    private boolean needSum;
}

package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto extends BaseDto {

    private Long id;
    private String year;
    private String month;
    private int payments;
    private MemberDto memberDto;
    private boolean needSum;
}

package namhyun.account_book.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import namhyun.account_book.domain.Member;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto extends BaseDto {

    private Long id;
    private String year;
    private String month;
    private int payments;
    private Member member;
}

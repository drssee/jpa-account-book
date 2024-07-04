package namhyun.account_book.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SearchCondition {

    private String year;
    private String month;
    private MemberDto memberDto;
}

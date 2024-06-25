package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDto extends BaseDto {

    private Long id;
    private int payLimit;
    private boolean canSendMessage;
    private MemberDto memberDto;
}

package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import namhyun.account_book.enums.SendType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDto extends BaseDto {

    private Long id;
    private int payLimit;
    private boolean canSendMessage;
    private MemberDto memberDto;
    private SendType customSendType;
    private String customMsg;
    private LocalDateTime customSendTime;
}

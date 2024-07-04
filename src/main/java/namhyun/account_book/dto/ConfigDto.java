package namhyun.account_book.dto;

import lombok.*;
import namhyun.account_book.enums.SendType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConfigDto extends BaseDto {

    private Long id;
    private int payLimit;
    private boolean canSendMessage;
    private MemberDto memberDto;
    private SendType customSendType;
    private String customMsg;
    private LocalDateTime customSendTime;
}

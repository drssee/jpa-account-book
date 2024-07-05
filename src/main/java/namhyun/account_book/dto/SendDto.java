package namhyun.account_book.dto;

import lombok.*;
import namhyun.account_book.enums.SendType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"id"})
public class SendDto extends BaseDto {

    private Long id;
    private LocalDateTime sendTime;
    private SendType sendType;
    private boolean isSend;
    private String msg;
    private MemberDto memberDto;
}

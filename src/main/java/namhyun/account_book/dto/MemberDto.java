package namhyun.account_book.dto;

import lombok.*;
import namhyun.account_book.enums.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MemberDto extends BaseDto {

    private String id;
    private String name;
    private int age;
    private UserType userType;
}

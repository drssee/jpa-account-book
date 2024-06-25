package namhyun.account_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import namhyun.account_book.enums.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto extends BaseDto {

    private String id;
    private String name;
    private int age;
    private UserType userType;
}

package namhyun.account_shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import namhyun.account_shop.enums.UserType;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {

    @Id @Column(name = "MEMBER_ID", length = 10)
    private String id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}

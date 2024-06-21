package namhyun.account_shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_CONFIG")
@Getter
@Setter
public class Config extends BaseEntity {

    private int DEFAULT_LIMIT = 500000;

    @Id @GeneratedValue @Column(name = "CONFIG_ID")
    private Long id;

    private int payLimit = DEFAULT_LIMIT;
    private boolean canAlertPayLimit;
    private boolean canSendMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}

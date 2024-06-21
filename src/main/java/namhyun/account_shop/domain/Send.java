package namhyun.account_shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import namhyun.account_shop.enums.SendType;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER_SEND")
@Getter
@Setter
public class Send extends BaseEntity {

    @Id @GeneratedValue @Column(name = "SEND_ID")
    private Long id;

    private LocalDateTime estimatedSendTime;

    @Enumerated(EnumType.STRING)
    private SendType sendType;

    private boolean isSend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}

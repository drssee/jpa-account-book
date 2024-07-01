package namhyun.account_book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import namhyun.account_book.enums.SendType;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER_CONFIG")
@Getter
@Setter
public class Config extends BaseEntity {

    @Id @GeneratedValue @Column(name = "CONFIG_ID")
    private Long id;

    @ColumnDefault("500000")
    private int payLimit;
    private boolean canSendMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private SendType customSendType;
    private String customMsg;
    private LocalDateTime customSendTime;
}

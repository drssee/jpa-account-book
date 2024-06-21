package namhyun.account_book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_STATISTICS")
@Getter
@Setter
public class Statistics extends BaseEntity {

    @Id @GeneratedValue @Column(name = "STATISTICS_ID")
    private Long id;

    private String year;
    private String month;
    private int payments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}

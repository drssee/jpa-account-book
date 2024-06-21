package namhyun.account_book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class PayPurpose extends BaseEntity {

    @Id @GeneratedValue @Column(name = "PAY_PURPOSE_ID")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private PayPurpose parent;

    @OneToMany(mappedBy = "parent")
    private List<PayPurpose> children;
}

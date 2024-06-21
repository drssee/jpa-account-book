package namhyun.account_shop.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initBaseEntity(String createdBy) {
        this.createdBy = createdBy;
        this.updatedBy = "";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public void updateBaseEntity(String updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }
}

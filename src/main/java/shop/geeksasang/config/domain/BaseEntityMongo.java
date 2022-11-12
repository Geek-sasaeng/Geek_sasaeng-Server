package shop.geeksasang.config.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import shop.geeksasang.config.status.BaseStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
public class BaseEntityMongo implements Serializable {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private BaseStatus status;

    protected void setStatus(BaseStatus status) {
        this.status = status;
    }

    public BaseEntityMongo() {
        this.status = BaseStatus.ACTIVE;
    }

    public void delete(){
        this.status = BaseStatus.INACTIVE;
    }
}

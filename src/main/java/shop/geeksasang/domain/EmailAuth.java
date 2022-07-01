package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class EmailAuth extends BaseEntity {
    private static final Long MAX_EXPIRE_TIME = 5L; // 인증만료 시간

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emailAuth_id")
    private int id;

    private String email;
    private String authToken; // 인증번호
    private LocalDateTime expireDate; // 만료 시간

    @Builder
    public EmailAuth(String email, String authToken, String status){
        this.email = email;
        this.authToken = authToken;
        this.status = status;
    }
}
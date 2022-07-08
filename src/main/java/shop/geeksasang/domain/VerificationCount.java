package shop.geeksasang.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class VerificationCount {

    @Id
    @GeneratedValue
    private int id;
    private String clientIp;
    private int smsVerificationCount;
    private int emailVerificationCount;

    public VerificationCount(String clientIp, int count) {
        this.clientIp = clientIp;
        this.emailVerificationCount = count;
    }

    public void increaseSmsVerificationCount(){
        this.smsVerificationCount++;
    }
    public void increaseEmailVerificationCount(){
        this.emailVerificationCount++;
    }
    public void setClientIp(String clientIp){
        this.clientIp = clientIp;
    }
}

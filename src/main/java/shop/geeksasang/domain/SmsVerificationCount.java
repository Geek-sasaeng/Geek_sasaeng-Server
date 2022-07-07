package shop.geeksasang.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class SmsVerificationCount {
    @Id @GeneratedValue
    private int id;
    private String clientIp;
    private int count;

    public SmsVerificationCount(String clientIp, int count) {
        this.clientIp = clientIp;
        this.count = count;
    }

    public void increaseVerificationCount(){
        this.count++;
    }
}

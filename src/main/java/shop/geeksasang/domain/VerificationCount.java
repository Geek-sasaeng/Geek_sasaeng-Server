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
    private String UUID;
    private int smsVerificationCount;
    private int emailVerificationCount;

    public VerificationCount(String UUID) {
        this.UUID = UUID;
    }

    public VerificationCount(String UUID, int count) {
        this.UUID = UUID;
        this.emailVerificationCount = count;
    }

    public void increaseSmsVerificationCount(){
        this.smsVerificationCount++;
    }

    public void increaseEmailVerificationCount(){
        this.emailVerificationCount++;
    }

    public void setUUID(String UUID){
        this.UUID = UUID;
    }

    public void resetVerificationCount(){
        this.smsVerificationCount = 0;
        this.emailVerificationCount = 0;
    }

    public boolean checkSmsVerificationCountIsMoreThan5() {
        return smsVerificationCount >= 4;
    }
}
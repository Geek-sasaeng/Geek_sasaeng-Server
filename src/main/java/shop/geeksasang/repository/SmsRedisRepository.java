package shop.geeksasang.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class SmsRedisRepository {

    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60; //유효시간 설정

    private final StringRedisTemplate stringRedisTemplate;

    //SMS 인증 정보 저장
    public void createSmsCertification(String phone, String certificationNumber) {
        stringRedisTemplate.opsForValue()
                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    //Redis에 있는 값을 리턴
    public String getSmsCertification(String phone) {
        return stringRedisTemplate.opsForValue().get(PREFIX + phone);
    }

    //제거
    public void removeSmsCertification(String phone) {
        stringRedisTemplate.delete(PREFIX + phone);
    }

    //키에 대한 값을 가지고 있는가
    public boolean hasKey(String phone) {
        return stringRedisTemplate.hasKey(PREFIX + phone);
    }
}

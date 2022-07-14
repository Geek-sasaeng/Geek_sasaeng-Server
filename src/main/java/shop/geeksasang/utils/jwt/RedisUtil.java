package shop.geeksasang.utils.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.Member;

import java.time.Duration;

@Service
@RequiredArgsConstructor
/*
   [이메일 인증]
    Redis 설정 클래스
 */
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    // key를 통해 value 리턴
    public String getData(String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        return valueOperations.get(email);
    }

    // 인증번호가 유효한지 체크
    public boolean checkNumber(String email, String value) {
        try {
            String valid_value = getData(email);
            if (valid_value.equals(value)) {
                deleteData(email);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_SMS_VERIFY_NUMBER);
        }
    }
    public void setData(String email, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email, value);
    }

    // 유효 시간 동안 (key, value) 저장
    public void setDataExpire(String email, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(email, value, expireDuration);
    }

    // 삭제
    public void deleteData(String email) {
        redisTemplate.delete(email);
    }
}

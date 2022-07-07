package shop.geeksasang.utils.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;

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
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // 인증번호가 유효한지 체크
    public boolean checkNumber(String email, String key) {
        try {
            String key_email = getData(key);
            if (key_email.equals(email)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_SMS_VERIFY_NUMBER);
        }
    }
    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    // 유효 시간 동안 (key, value) 저장
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}

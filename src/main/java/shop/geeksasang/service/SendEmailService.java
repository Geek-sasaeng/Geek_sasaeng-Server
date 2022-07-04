package shop.geeksasang.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.EmailSenderDto;
import shop.geeksasang.utils.jwt.RedisUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@ComponentScan("shop.geeksasang.config")
public class SendEmailService {
    private final long expireTime = 60 * 5L; // 이메일 유효 기간

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final RedisUtil redisUtil;

    private void send(final String subject, final String content, final List<String> receivers) {
        final EmailSenderDto senderDto = EmailSenderDto.builder() // 1
                .to(receivers)
                .subject(subject)
                .content(content)
                .build();

        final SendEmailResult sendEmailResult = amazonSimpleEmailService // 2
                .sendEmail(senderDto.toSendRequestDto());

        sendingResultMustSuccess(sendEmailResult); // 3
    }

    private void sendingResultMustSuccess(final SendEmailResult sendEmailResult) {
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }
        /*
            이메일 전송
         */
    public void authEmail(EmailReq req){
        // 임의의 authKey 생성
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);

        // 이메일 발송
        sendAuthEmail(req.getEmail(), authKey);
    }

    // 이메일 전송 (Redis에 인증 번호 저장, 5분 유효)
    private void sendAuthEmail(String email, String authKey){
        String subject = "제목";
        String content = "회원 가입을 위한 인증번호는 " + authKey + "입니다.  ";
        List<String> receiver = new ArrayList<>();
        receiver.add(email);
        send(subject, content, receiver); // 이메일 보내기
        // 유효 시간 (5분)동안 {email, authKey} 저장
        redisUtil.setDataExpire(authKey, email, expireTime);
    }
}
package shop.geeksasang.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.University;
import shop.geeksasang.domain.VerificationCount;
import shop.geeksasang.dto.email.PostEmailCertificationReq;
import shop.geeksasang.dto.email.PostEmailReq;
import shop.geeksasang.dto.email.EmailSenderDto;
import shop.geeksasang.repository.UniversityRepository;
import shop.geeksasang.repository.VerificationCountRepository;
import shop.geeksasang.utils.jwt.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@ComponentScan("shop.geeksasang.config")
public class EmailService {
    final UniversityRepository universityRepository;
    final VerificationCountRepository verificationCountRepository;

    private final long expireTime = 60 * 5L; // 이메일 유효 기간

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final RedisUtil redisUtil;

    // 인증번호 이메일 전송
    @Transactional(readOnly = false)
    public void sendEmail(PostEmailReq request) {
        // 대학교 이메일 주소 검증
        String universityName = request.getUniversity();
        University university = universityRepository.findUniversityByName(universityName)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_UNIVERSITY));
        String universityEmailAdress = university.getEmailAddress();
        String email = request.getEmail();
        String UUID = request.getUUID();
        String[] emailAddresses = email.split("@");
        String emailAddress = emailAddresses[1];
        // 대학교 이메일 주소 같지 않으면
        if(!universityEmailAdress.equals(emailAddress)){
            throw new BaseException(BaseResponseStatus.NOT_MATCH_EMAIL);
        }


        // 하루 10번 제한 검증
        Optional<VerificationCount> emailVerificationCount_optional = verificationCountRepository.findEmailVerificationCountByUUID(UUID);

        if (emailVerificationCount_optional.isPresent()){
            VerificationCount emailVerificationCount = emailVerificationCount_optional.get();
            emailVerificationCount.increaseEmailVerificationCount();
            if(emailVerificationCount.getEmailVerificationCount() >= 10){
                throw new BaseException(INVALID_EMAIL_COUNT);
            }
            verificationCountRepository.save(emailVerificationCount);

        }
        else{
            VerificationCount emailVerificationCount = new VerificationCount(UUID);
            verificationCountRepository.save(emailVerificationCount);
        }

        // 난수 생성
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);
        sendAuthEmail(request.getEmail(), authKey);

    }

    // 인증번호가 일치하는지 체크
    public boolean checkEmailCertification(PostEmailCertificationReq request) {
        String email = request.getEmail();
        String key = request.getKey();
        return redisUtil.checkNumber(email, key);
    }

    // AWS SES로 이메일 전송, 비동기
    @Async
    public void sendSES(final String subject, final String content, final List<String> receivers) {
        try {
            final EmailSenderDto senderDto = EmailSenderDto.builder() // 1
                    .to(receivers)
                    .subject(subject)
                    .content(content)
                    .build();

            final SendEmailResult sendEmailResult = amazonSimpleEmailService // 2
                    .sendEmail(senderDto.toSendRequestDto());

            sendingResultMustSuccess(sendEmailResult); // 3
        }catch(TaskRejectedException e){
            throw new BaseException(THREAD_OVER_REQUEST);
        }
    }

    // 이메일 전송 (Redis에 인증 번호 저장, 5분 유효)
    private void sendAuthEmail(String email, String authKey) {
        String subject = "[긱사생] 인증 번호 발송 메일입니다.";
        List<String> receiver = new ArrayList<>();
        receiver.add(email);
        String content = getContent(authKey);
        sendSES(subject, content, receiver); // SES 이메일 보내기
        // 유효 시간 (5분)동안 {email, authKey} 저장
        redisUtil.setDataExpire(email, authKey, expireTime);
        redisUtil.getData(email);
    }

    private void sendingResultMustSuccess(final SendEmailResult sendEmailResult) {
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }

    // html 받아오기
    private String getContent(String authKey) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<!DOCTYPE html>");
        emailContent.append("<html>");
        emailContent.append("<head>");
        emailContent.append("</head>");
        emailContent.append("<body>");
        emailContent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 700px; height: 900px; border-top: 4px solid #29ABE2; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 50px; margin: 0 0 10px 3px;\">긱사생</span><br/>" +
                        "		<span style=\"color: #29ABE2\">학교 메일인증</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "	    학교 메일 인증을 위한 인증번호를 알려드립니다.<br/>" +
                        "		아래 인증 번호를 " + "<strong style=\"color:#29ABE2\">5분이내</strong>에 입력하시면 인증이 완료됩니다. <br/>" +
                        "   <div style=\"width: 576px;height: 90px; margin-top: 50px; padding: 0 27px;color: #242424;font-size: 16px;font-weight: bold;background-color: #F9F9F9;vertical-align: middle;line-height: 90px;\">인증번호 : <strong style=\"font-style: normal;font-weight: bold;color: #29ABE2\">" + authKey + "</strong></div>" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "	    발급 받으신 인증번호를 입력하시면 학교 이메일 인증을 완료하실 수 있습니다.<br/> <br/> <br/>" +
                        "		감사합니다.<br/>" +
                        "	<div style=\"border-top: 4px solid #29ABE2; margin: 40px auto; padding: 30px 0;\"></div>" +
                        " </div>");
        emailContent.append("</body>");
        emailContent.append("</html>");
        String content = emailContent.toString();
        return content;
    }
}
package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import shop.geeksasang.utils.mail.MailUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service("mss")
@RequiredArgsConstructor
public class MailSendService{
    private final JavaMailSenderImpl mailSender;

    private int size;
    // 인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    // 인증코드 난수 발생
    private String getAuthCode(){
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size){
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    // 인증 메일 보내기
    public String sendAuthMail(String email){
        // 6자리 난수 인증번호 생성
        String authKey = getKey(6);

        // 인증메일 보내기
        try{
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("긱사생 회원 가입 인증번호입니다.");
            sendMail.setText(new StringBuffer().append("<h1>[인증번호]</h1>")
                    .append("쉽고 편리한 연결 메이트, 긱사생에 회원가입을 진행해 주셔서 감사합니다." + 	//html 형식으로 작성 !
                            "<br><br>" +
                            "인증 번호는 " + authKey + "입니다." +
                            "<br>" +
                            "해당 인증번호를 인증번호 확인란에 기입하여 주세요.").toString()); //이메일 내용 삽입
            sendMail.setFrom("geeksasaeng@gmail.com", "긱사생");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return authKey;
    }
}

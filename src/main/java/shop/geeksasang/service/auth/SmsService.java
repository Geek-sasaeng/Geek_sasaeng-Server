package shop.geeksasang.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.*;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URISyntaxException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;

import shop.geeksasang.config.status.ValidStatus;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.auth.PhoneNumber;
import shop.geeksasang.domain.auth.VerificationCount;
import shop.geeksasang.dto.sms.MessagesDto;
import shop.geeksasang.dto.sms.NaverApiSmsReq;
import shop.geeksasang.dto.sms.NaverApiSmsRes;
import shop.geeksasang.dto.sms.PostVerifySmsRes;
import shop.geeksasang.repository.auth.PhoneNumberRepository;
import shop.geeksasang.repository.auth.infrastructure.SmsRedisRepository;
import shop.geeksasang.repository.auth.VerificationCountRepository;

import static shop.geeksasang.config.TransactionManagerConfig.JPA_TRANSACTION_MANAGER;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("#{environment['sms.service_id']}") //.yml 파일의 값을 가져온다.
    private String serviceId;

    @Value("#{environment['sms.access_key']}")
    private String accessKey;

    @Value("#{environment['sms.secret_key']}")
    private String secretKey;

    @Value("#{environment['sms.phone_number']}")
    private String fromPhoneNumber;

    private final PhoneNumberRepository phoneNumberRepository;
    private final SmsRedisRepository smsRedisRepository;
    private final VerificationCountRepository smsVerificationCountRepository;

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void sendSms(String recipientPhoneNumber, String uuid) throws URISyntaxException, JsonProcessingException {

        String randomNumber = makeRandomNumber();

        //uuid가 없다면 이메일 인증을 하지 않은 것이므로 오류 발생
        VerificationCount smsVerificationCount = smsVerificationCountRepository.findVerificationCountByUUID(uuid)
                .orElseThrow(() -> new BaseException(INVALID_SMS_UUID));

        // 중복되는 핸드폰 번호 있는지 검사
        if(phoneNumberRepository.findPhoneNumberByNumber(recipientPhoneNumber).isPresent()){
            PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByNumber(recipientPhoneNumber).get();
            if(phoneNumber.getMember() == null){
                phoneNumberRepository.delete(phoneNumber);
            }else{
                throw new BaseException(DUPLICATE_USER_PHONENUMBER);
            }
        }

        //하루에 5번 넘었는지 검사
        if(smsVerificationCount.checkSmsVerificationCountIsMoreThan5()){
            throw new BaseException(INVALID_SMS_COUNT);
        }

        //기존에 요청을 했다면 남아 있는 키 삭제
        if(smsRedisRepository.hasKey(recipientPhoneNumber)){
            smsRedisRepository.removeSmsCertification(recipientPhoneNumber);
        }

        userSmsApi(recipientPhoneNumber, randomNumber);

        smsRedisRepository.createSmsCertification(recipientPhoneNumber, randomNumber);
        smsVerificationCount.increaseSmsVerificationCount();
    }

    @Async
    public void userSmsApi(String recipientPhoneNumber,String randomNumber) throws JsonProcessingException, URISyntaxException {
        List<MessagesDto> messages = new ArrayList<>();
        String smsContentMessage = makeSmsContentMessage(randomNumber);
        messages.add(new MessagesDto(recipientPhoneNumber, smsContentMessage));

        //바디와 헤더 생성
        NaverApiSmsReq smsRequest = new NaverApiSmsReq("SMS", "COMM", "82", fromPhoneNumber, "내용", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);
        HttpHeaders headers = makeHeaders();

        //요청 보낼 준비
        HttpEntity<String> body = new HttpEntity<>(jsonBody,headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        //네이버 api에 요청을 보낸다. //나중에 비동기로 바꾸면 좋을 듯
        restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, NaverApiSmsRes.class);

    }

    private HttpHeaders makeHeaders() {
            Long time = System.currentTimeMillis();
            HttpHeaders headers = new HttpHeaders();

            //암호화
            String signature = makeSignature(time);

            //헤더 설정
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", time.toString());
            headers.set("x-ncp-iam-access-key", this.accessKey);
            headers.set("x-ncp-apigw-signature-v2", signature);

            return headers;
    }

    private String makeSignature(Long time){
        try{
            String space = " ";
            String newLine = "\n";
            String method = "POST";
            String url = "/sms/v2/services/"+ this.serviceId+"/messages";
            String timestamp = time.toString();
            String accessKey = this.accessKey;
            String secretKey = this.secretKey;

            String message = new StringBuilder()
                    .append(method)
                    .append(space)
                    .append(url)
                    .append(newLine)
                    .append(timestamp)
                    .append(newLine)
                    .append(accessKey)
                    .toString();

            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            String encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;
        }
        catch(UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e){
            throw new BaseException(FAIL_MAKE_SIGNATURE);
        }
    }

    private String makeRandomNumber(){
        return String.valueOf((int) (Math.random() * 899999) + 100000);
    }

    private String makeSmsContentMessage(String randomNumber){
        return "인증번호[" + randomNumber + "]를 입력해주세요";
    }


    // 인증 후 핸드폰 번호 등록
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PostVerifySmsRes verifySms(String verifyRandomNumber, String phoneNumber) {
        if(!isVerify(verifyRandomNumber, phoneNumber)){
            throw new BaseException(INVALID_SMS_VERIFY_NUMBER);
        }
        smsRedisRepository.removeSmsCertification(phoneNumber);
        // 인증된 핸드폰 번호 등록
        PhoneNumber phoneNumberEntity = new PhoneNumber(phoneNumber);
        phoneNumberRepository.save(phoneNumberEntity);
        return new PostVerifySmsRes(phoneNumberEntity.getId());
    }

    private boolean isVerify(String verifyRandomNumber, String phoneNumber){
        return smsRedisRepository.hasKey(phoneNumber) && smsRedisRepository.getSmsCertification(phoneNumber).equals(verifyRandomNumber);
    }

    // 소셜 로그인 시 DB에 핸드폰 번호 저장
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PhoneNumber savePhoneNumber(String phoneNumber){
        if(phoneNumberRepository.findPhoneNumberByNumber(phoneNumber).isPresent())
            throw new BaseException(DUPLICATE_USER_PHONENUMBER);
        PhoneNumber phoneNumberEntity = PhoneNumber.builder().number(phoneNumber).phoneValidStatus(ValidStatus.SUCCESS).build();
        return phoneNumberRepository.save(phoneNumberEntity);
    }
}

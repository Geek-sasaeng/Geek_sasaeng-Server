package shop.geeksasang.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.domain.VerificationCount;
import shop.geeksasang.dto.sms.MessagesDto;
import shop.geeksasang.dto.sms.NaverApiSmsReq;
import shop.geeksasang.dto.sms.NaverApiSmsRes;
import shop.geeksasang.dto.sms.PostVerifySmsRes;
import shop.geeksasang.repository.SmsRedisRepository;
import shop.geeksasang.repository.SmsVerificationCountRepository;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

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

    private final SmsRedisRepository smsRedisRepository;
    private final SmsVerificationCountRepository smsVerificationCountRepository;

    @Transactional(readOnly = false)
    public NaverApiSmsRes firstSendSms(String recipientPhoneNumber, String clientIp) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        String randomNumber = makeRandomNumber();

        //아이피가 이미 있다면 재전송을 요청
        //if(!smsVerificationCountRepository.findSmsVerificationCountByClientIp(clientIp).isEmpty()){
        //    throw new BaseException(INVALID_SMS_CLIENT_IP);
        //}

        //전화번호 인증
        NaverApiSmsRes naverApiSmsRes = sendSms(recipientPhoneNumber, randomNumber);

        //redis에 저장
        smsRedisRepository.createSmsCertification(recipientPhoneNumber, randomNumber);

        //새로운 클라이언트 아이디 생성
        smsVerificationCountRepository.save(new VerificationCount(clientIp,0));

        return naverApiSmsRes;
    }

    @Transactional(readOnly = false)
    public NaverApiSmsRes repeatSendSms(String recipientPhoneNumber, String clientIp) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        String randomNumber = makeRandomNumber();

        //하루에 5번 넘었는지 검사
        VerificationCount smsVerificationCount = smsVerificationCountRepository.findSmsVerificationCountByClientIp(clientIp)
                .orElseThrow(() -> new BaseException(INVALID_SMS_PHONE_NUMBER));

        if(smsVerificationCount.getSmsVerificationCount() >= 4 ){
            throw new BaseException(INVALID_SMS_COUNT);
        }

        //기존에 키 삭제
        smsRedisRepository.removeSmsCertification(recipientPhoneNumber);

        //전화번호 인증
        NaverApiSmsRes naverApiSmsRes = sendSms(recipientPhoneNumber, randomNumber);

        //redis에 저장
        smsRedisRepository.createSmsCertification(recipientPhoneNumber, randomNumber);

        //count + 1
        smsVerificationCount.increaseSmsVerificationCount();

        return naverApiSmsRes;
    }

    public NaverApiSmsRes sendSms(String recipientPhoneNumber,String randomNumber) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        //기초 세팅
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
        NaverApiSmsRes smsResponse = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, NaverApiSmsRes.class);

        return smsResponse;
    }

    private HttpHeaders makeHeaders() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
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

    private String makeSignature(Long time) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
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

    private String makeRandomNumber(){
        return String.valueOf((int) (Math.random() * 899999) + 100000);
    }

    private String makeSmsContentMessage(String randomNumber){
        return "인증번호[" + randomNumber + "]를 입력해주세요";
    }


    @Transactional(readOnly = true)
    public PostVerifySmsRes verifySms(String verifyRandomNumber, String phoneNumber) {
        if(!isVerify(verifyRandomNumber, phoneNumber)){
            throw new BaseException(INVALID_SMS_VERIFY_NUMBER);
        }
        smsRedisRepository.removeSmsCertification(phoneNumber);
        return new PostVerifySmsRes("Verification Success");
    }

    private boolean isVerify(String verifyRandomNumber, String phoneNumber){
        return smsRedisRepository.hasKey(phoneNumber) && smsRedisRepository.getSmsCertification(phoneNumber).equals(verifyRandomNumber);
    }
}

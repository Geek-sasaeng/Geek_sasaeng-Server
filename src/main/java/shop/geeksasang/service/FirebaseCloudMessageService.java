package shop.geeksasang.service;



import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.firebase.FcmMessage;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.*;

import java.io.IOException;
import java.util.List;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.DIFFERENT_CHIEF_ID;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.NOT_EXISTS_PARTY;

@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/geeksasaeng-473bf/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    public void sendDeliveryComplicatedMessage(String uuid, int userId) throws IOException {

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByUuidFinish(uuid).orElseThrow(
                () -> new BaseException(NOT_EXISTS_PARTY));

        // 방장이 아닌 경우
        if(deliveryParty.getChief().getId() != userId){
            throw new BaseException(DIFFERENT_CHIEF_ID);
        }

        List<Member> members = memberRepository.findMemberFcmTockenById(deliveryParty.getId());
        String title = "배달이 완료되었습니다!";
        String body = "수령장소에서 받아가세요(❁´◡`❁)";


        OkHttpClient client = new OkHttpClient();
        int i = 0;
        for( Member member : members ){
            String token = member.getFcmToken();

            if(!StringUtils.hasText(token)){
                continue;
            }

            String message = makeMessage(members.get(i).getFcmToken(), title, body);

            RequestBody requestBody = RequestBody.create(message,
                    MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response.body().string());
            i++;
            ResponseEntity.ok().build();
        }
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}




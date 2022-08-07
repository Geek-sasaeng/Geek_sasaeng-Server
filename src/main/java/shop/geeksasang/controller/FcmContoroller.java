package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.dto.firebase.PostFcmReq;
import shop.geeksasang.service.FirebaseCloudMessageService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FcmContoroller {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @NoIntercept
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody PostFcmReq requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }

}

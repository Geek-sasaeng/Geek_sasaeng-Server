package shop.geeksasang.controller.applelogin.service;



import shop.geeksasang.controller.applelogin.model.DeleteUserReq;
import shop.geeksasang.controller.applelogin.model.ServicesResponse;
import shop.geeksasang.controller.applelogin.model.TokenResponse;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface AppleService {

    String getAppleClientSecret(String id_token) throws NoSuchAlgorithmException;

    TokenResponse requestCodeValidations(ServicesResponse serviceResponse, String refresh_token) throws NoSuchAlgorithmException;

    Map<String, String> getLoginMetaInfo();

    String getPayload(String id_token);

    void deleteUser(DeleteUserReq deleteUserReq) throws NoSuchAlgorithmException;

//    Void validateRefreshToken(Long userId, String refreshToken);
}

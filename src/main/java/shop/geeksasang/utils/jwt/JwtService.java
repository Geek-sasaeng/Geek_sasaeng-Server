package shop.geeksasang.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.secret.Secret;
import shop.geeksasang.dto.login.JwtInfo;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;


@Service
@NoArgsConstructor
public class JwtService {
    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(JwtInfo jwtInfo){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("jwtInfo", jwtInfo)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*60)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public LinkedHashMap getJwtInfo() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Claims body;
        try{
            body = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        //3. JWT 유효기간 확인
        if(!validateToken(accessToken))
            throw new BaseException(EXPIRED_JWT);

        System.out.println("body = " + body.get("jwtInfo"));
        return body.get("jwtInfo", LinkedHashMap.class);
    }

    //토큰 유효기간 확인
    public boolean validateToken(String token){
         boolean expiration= Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
        return !expiration;
    }

}

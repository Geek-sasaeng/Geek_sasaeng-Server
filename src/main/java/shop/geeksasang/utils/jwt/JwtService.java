package shop.geeksasang.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.dto.login.JwtInfo;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;


@Service
@NoArgsConstructor
public class JwtService {

    @Value("${secret.jwt_secret_key}")
    private String secretKey;

    public static final String AUTHORIZATION = "Authorization";


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
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /*
    Header에서 Authorization 으로 JWT 추출
    Authorization(키) : "Bearer" + jwtToken
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> headers=request.getHeaders(AUTHORIZATION);
        while(headers.hasMoreElements()){
            String value = headers.nextElement();
            if(value.toLowerCase().startsWith("Bearer".toLowerCase())){
                return value.substring("Bearer".length()).trim();
            }
        }
        return Strings.EMPTY;
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public LinkedHashMap getJwtInfo() throws BaseException {
        // 1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Claims body;
        try{
            body = Jwts.parser()
                    .setSigningKey(secretKey)
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
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
        return !expiration;
    }
}

package shop.geeksasang.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.utils.jwt.JwtService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //@NoIntercept 이면 interceptor 종료

        boolean check=checkAnnotation(handler, NoIntercept.class);
        if(check) return true;

        //LinkedHashMap 타입 jwtInfo parsings
        LinkedHashMap jwtInfo = jwtService.getJwtInfo();

        //LinkedHashMap 타입 jwtInfo값을 JwtInfo.class 타입으로 convert
        JwtInfo convertJwtInfo = objectMapper.convertValue(jwtInfo,JwtInfo.class);

        //request에 convertJwtInfo값 세팅
        request.setAttribute("jwtInfo", convertJwtInfo);

        return true;
    }

    private boolean checkAnnotation(Object handler,Class c){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(c)!=null){
            return true;
        }
        return false;
    }
}

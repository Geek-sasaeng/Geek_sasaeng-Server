package shop.geeksasang.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
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

        try{
            //parsing 후 jwtInfo(userId, dormitoryId0 request에 넘김
            LinkedHashMap jwtInfo = jwtService.getJwtInfo();
            request.setAttribute("jwtInfo",jwtInfo);

        }
        catch(BaseException exception){
            //URL로 이동되도록 & 정확한 exception 보여주기
            throw new BaseException(BaseResponseStatus.INACTIVE_STATUS);

        }

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

package shop.geeksasang.utils.clientip;

import javax.servlet.http.HttpServletRequest;

public class ClientIpUtils {

    public static String getClientIp(HttpServletRequest servletRequest){
        String ip = servletRequest.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.length() == 0) {
            ip = servletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = servletRequest.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = servletRequest.getRemoteAddr() ;
        }

        return ip;
    }
}

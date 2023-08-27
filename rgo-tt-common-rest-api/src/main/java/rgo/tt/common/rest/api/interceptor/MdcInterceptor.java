package rgo.tt.common.rest.api.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import rgo.tt.common.logging.MdcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MdcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object h) {
        MdcUtils.init();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object h, Exception e) {
        MdcUtils.clear();
    }
}

package vn.rts.customs.eclare.configuration.dbrouting;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String orgCode = request.getHeader("orgCode");
        DataSourceContextHolder.setOrgCodeContext(orgCode);
        return super.preHandle(request, response, handler);
    }
}

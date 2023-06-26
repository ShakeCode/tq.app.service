package com.org.dream.interceptor;

import com.org.dream.domain.dto.HeaderInfo;
import com.org.dream.util.UserContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AppInterceptor implements HandlerInterceptor {
    /**
     * 目标方法执行前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        if (StringUtils.isNotBlank(request.getHeader("token"))) {
            UserContextUtil.setHeaderInfo(new HeaderInfo());
        } else {
            UserContextUtil.setHeaderInfo(new HeaderInfo());
        }
        return true;
    }

    /**
     * 目标方法执行后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("请求:{}, 耗时: {} ms", request.getRequestURI(), System.currentTimeMillis() - (long) request.getAttribute("startTime"));
        UserContextUtil.clear();
    }

    /**
     * 所有逻辑处理完成后执行  异常也会执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion....");
    }
}

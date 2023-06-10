package com.org.dream;

import com.org.dream.domain.vo.rsp.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Runtime exception handler map.
     * @param e       the e
     * @param request the request
     * @return the map
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResultVO runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        log(e, request);
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * Exception handler map.
     * @param e       the e
     * @param request the request
     * @return the map
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVO exceptionHandler(Exception e, HttpServletRequest request) {
        log(e, request);
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");
        logger.error("请求异常了:", ex);
        logger.error("请求地址：" + request.getRequestURL());
       /* Enumeration enumeration = request.getParameterNames();
        logger.error("请求参数");
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            logger.error(name + "---" + request.getParameter(name));
        }
        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }*/
        logger.error("************************异常结束*******************************");
    }
}
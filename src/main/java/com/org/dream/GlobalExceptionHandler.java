package com.org.dream;

import com.org.dream.domain.vo.rsp.ResultVO;
import com.org.dream.exception.DAServiceException;
import com.org.dream.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle validation exception result vo.
     * @param request the request
     * @param ex      the ex
     * @return the result vo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    protected ResultVO handleValidationException(HttpServletRequest request, ValidationException ex) {
        logger.error("================HTTP-400-PARAM-ERROR================");
        logger.error("ValidationException,path:[{}],message:[{}]", request.getServletPath(), ex.getMessage());
        logger.error(request.getServletPath());
        return ResultVO.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * Data service exception handler result vo.
     * @param re the re
     * @return the result vo
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DAServiceException.class)
    protected ResultVO dataServiceExceptionHandler(DAServiceException re) {
        logger.error("-----------data service Exception:{}", re.getMessage());
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), re.getMessage());
    }

    /**
     * Handle permission exception result vo.
     * @param request the request
     * @param ex      the ex
     * @return the result vo
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionException.class)
    protected ResultVO handlePermissionException(HttpServletRequest request, PermissionException ex) {
        logger.error("================HTTP-403-FORBIDDEN================");
        logger.error("PermissionException,path:[{}],message:[{}]", request.getServletPath(), ex.getMessage());
        return ResultVO.fail(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }


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
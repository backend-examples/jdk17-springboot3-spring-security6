package com.example.exception;

import com.example.constant.MessagesConstant;
import com.example.enums.CodeEnum;
import com.example.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * 自定义全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseResult bizExceptionHandler(HttpServletRequest request, HttpServletResponse response, BizException e) {
        logger.error("发生业务异常！原因是：" + e.getLocalizedMessage());
        e.printStackTrace();

        response.setStatus(CodeEnum.BODY_NOT_MATCH.getCode());

        return ResponseResult.builder().code(CodeEnum.BODY_NOT_MATCH.getCode()).message(e.getLocalizedMessage()).build();
    }

    /**
     * 处理空指针的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, NullPointerException e) {
        logger.error("发生空指针异常！原因是: " + e.getLocalizedMessage());
        e.printStackTrace();

        response.setStatus(CodeEnum.BODY_NOT_MATCH.getCode());

        return ResponseResult.builder().code(CodeEnum.BODY_NOT_MATCH.getCode()).message(MessagesConstant.NULL_EXCEPTION).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult requestInvalid(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e) {
        logger.error("Http 消息不可读异常: " + e.getLocalizedMessage());
        e.printStackTrace();

        response.setStatus(CodeEnum.INTERNAL_SERVER_ERROR.getCode());

        return ResponseResult.builder().error(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage()).build();
    }

    /**
     * 参数校验的异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        logger.error("参数校验异常！原因是: " + e.getLocalizedMessage());
        e.printStackTrace();

        response.setStatus(CodeEnum.INTERNAL_SERVER_ERROR.getCode());

        return ResponseResult.builder().code(CodeEnum.INTERNAL_SERVER_ERROR.getCode()).message(e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "))).build();
    }


    /**
     * 处理其他异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("未知异常！原因是: " + e.getLocalizedMessage());
        e.printStackTrace();

        response.setStatus(CodeEnum.INTERNAL_SERVER_ERROR.getCode());

        return ResponseResult.builder().code(CodeEnum.INTERNAL_SERVER_ERROR.getCode()).message(CodeEnum.INTERNAL_SERVER_ERROR.getMessage()).build();
    }
}

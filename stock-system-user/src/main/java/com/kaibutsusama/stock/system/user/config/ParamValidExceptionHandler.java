package com.kaibutsusama.stock.system.user.config;

import com.kaibutsusama.system.common.web.ApiRespResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:38
 */
@ControllerAdvice
@Log4j2
public class ParamValidExceptionHandler {


    /**
     * 捕获所有校验异常信息,  进行封装, 返回给客户端, 捕获的是BindException
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiRespResult handlerException(BindException ex) {
        log.warn("enter handlerException method.");
        // 1. 获取所有的校验错误提示
        StringBuffer stringBuffer = new StringBuffer();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        // 2. 遍历属性的校验结果
        for(FieldError fieldError : errors) {
            stringBuffer.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage());
        }

        // 3. 封装校验异常返回信息
        ApiRespResult errorWebResult = ApiRespResult.validError(stringBuffer.toString());

        return errorWebResult;

    }


    /**
     * 拦截约束性异常的处理, 比如@NotBlank, 非空的必要性约束等, 捕获的是ConstraintViolationException
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiRespResult handlerConstraintException(ConstraintViolationException ex) {
        // 1. 获取所有校验错误提示
        StringBuffer stringBuffer = new StringBuffer();
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        // 2. 遍历属性校验结果集
        for(ConstraintViolation<?> constraintViolation : errors) {
            stringBuffer.append(constraintViolation.getMessage());
        }
        // 3. 封装校验异常返回信息
        ApiRespResult errorWebResult = ApiRespResult.validError(stringBuffer.toString());
        return errorWebResult;
    }

}
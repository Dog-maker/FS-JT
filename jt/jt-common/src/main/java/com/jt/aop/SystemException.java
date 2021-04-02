package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
@Slf4j
public class SystemException {

    @ExceptionHandler({RuntimeException.class})
    public Object exception(Exception e, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        log.error(e.getMessage(),e);
        e.printStackTrace();

        String callback = httpServletRequest.getParameter("callback");
        if(StringUtils.hasLength(callback)){
            JSONPObject jsonpObject = new JSONPObject(callback, SysResult.fail());
            return jsonpObject;
        }

        return SysResult.fail();
    }
}

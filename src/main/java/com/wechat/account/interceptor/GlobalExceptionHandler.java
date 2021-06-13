package com.wechat.account.interceptor;

import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * 捕获系统异常 全局处理器
 * Created by yuejun on 21-06-05
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获业务类异常
     * @param bizException
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BizException.class)
    public RedirectView handleBizException(BizException bizException, HttpServletRequest request) {
        LOGGER.error("BizException: " + bizException.getResultCodeEnum(), bizException);

        RedirectView rw = new RedirectView("/error.html");
        rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // you might not need this
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("message", bizException.getResultCodeEnum().getReasonPhrase());
        return rw;
    }

    /**
     * 捕获除业务异常的系统未知异常
     * @param ex
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        LOGGER.error("GlobalExceptionHandler Exception: " + ex.getMessage(), ex);

        redirectAttributes.addFlashAttribute("message", ResultCodeEnum.SYSTEM_ERROR.getReasonPhrase());
        return "redirect:/error.html";
    }
}

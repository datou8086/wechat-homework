package com.wechat.account.exception;

import com.wechat.account.modules.base.eums.ResultCodeEnum;

/**
 * 业务异常类
 * Created by yuejun on 21-06-11
 */
public class BizException extends RuntimeException {

    /**
     * 用来抛出对应错误的ResultCodeEnum
     */
    private ResultCodeEnum resultCodeEnum;

    public BizException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }

    public void setResultCodeEnum(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }
}

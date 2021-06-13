package com.wechat.account.modules.base.model.entity;

import com.wechat.account.modules.base.eums.ResultCodeEnum;
import lombok.Data;

/**
 * API接口返回的ResultDTO
 * Created by yuejun on 21-06-05
 */
@Data
public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public static ResultDTO successOf(String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ResultCodeEnum.OK.getCode());
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T>ResultDTO successOf(String message,T data){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ResultCodeEnum.OK.getCode());
        resultDTO.setMessage(message);
        resultDTO.setData(data);
        return resultDTO;
    }

    public static ResultDTO errorOf(Integer code,String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(ResultCodeEnum resultCodeEnum) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(resultCodeEnum.getCode());
        resultDTO.setMessage(resultCodeEnum.getReasonPhrase());
        return resultDTO;
    }
}

package com.megamusic.findshow.common.utils;


import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.domain.Response;

/**
 * Created by maita on 17/8/20.
 */
public class ResponseUtils {
    public static <T> Response<T> getSuccessResponse(T data) {
        return new Response<T>(SystemConstantsEnum.SUCCESS.getCode(), data);
    }

    public static <T> Response<T> getResponse(boolean result, T data) {
        if (result) {
            return new Response<T>(SystemConstantsEnum.SUCCESS.getCode(), data);
        }
        return new Response<T>(SystemConstantsEnum.FAIL.getCode(), SystemConstantsEnum.FAIL.getDescription());
    }

    public static <T> Response<T> getErrorResponse(SystemConstantsEnum e) {
        return new Response<T>(e.getCode(), e.getDescription());
    }

    public static <T> Response<T> getSuccessResponseByEnum(SystemConstantsEnum e) {
        return new Response<T>(e.getCode(), e.getDescription());
    }

}

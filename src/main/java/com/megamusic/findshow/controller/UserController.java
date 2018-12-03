package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.UserDto;
import com.megamusic.findshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chengchao on 2018/9/26.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("wxLogin")
    @ResponseBody
    public Response wxLogin(String code) {
        if(StringUtils.isEmpty(code))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        UserDto userDto = userService.wxLogin(code);
        if(userDto==null)
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
        return ResponseUtils.getSuccessResponse(userDto);
    }



}

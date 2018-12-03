package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chengchao on 2018/10/9.
 */
@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {



    @RequestMapping("wxPay")
    @ResponseBody
    public Response wxPay(String userId) {
        return ResponseUtils.getSuccessResponse("");
    }

}

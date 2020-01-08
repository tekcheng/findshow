package com.megamusic.findshow.controller.op;

import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chengchao on 2019/3/18.
 */
@Controller("opOrder")
@RequestMapping("/op/order")
public class OrderController {


    /**
     * 查看待审核列表
     * @return
     */
    @RequestMapping("list/audited")
    @ResponseBody
    public Response audited() {
        return ResponseUtils.getSuccessResponse("");
    }


    /**
     * 审核
     * @return
     */
    @RequestMapping("check")
    @ResponseBody
    public Response check() {
        return ResponseUtils.getSuccessResponse("");
    }

}

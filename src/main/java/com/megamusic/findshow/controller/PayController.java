package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.UserRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.WXPayRespDto;
import com.megamusic.findshow.domain.entity.User;
import com.megamusic.findshow.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * Created by chengchao on 2018/9/29.
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {


    @Autowired
    private WXPayService wxPayService;


    @Autowired
    private UserRepository userRepository;

    //测试用
    @RequestMapping("wxPay")
    @ResponseBody
    public Response wxPay(String userId) {

        User user = userRepository.findById(Long.valueOf(userId));
        if (user == null) {
            log.error("用户不存在");
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
        }

        WXPayRespDto wxPayRespDto;
        String openId = user.getOpenid();
        String body = "找演出商演平台-联系艺人";
        String orderNo = "yl_zyc_pay_01_" + System.currentTimeMillis();
        try {
            wxPayRespDto = wxPayService.wxPayProcess(openId, body, "125.34.4.2", 1L, orderNo);
        } catch (Exception e) {
            log.error("[微信支付] 失败！", e);
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
        }

        return ResponseUtils.getSuccessResponse(wxPayRespDto);
    }

    /**
     * 微信支付-查看联系方式
     *
     * @param userId
     * @param artistId
     * @param fee
     * @return
     */
    @RequestMapping("wxPayArtistContact")
    @ResponseBody
    public Response wxPayArtist(String userId,String artistId, Long fee,HttpServletRequest request) {

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(artistId))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);

        Long uid = Long.valueOf(userId);
        Long aid = Long.valueOf(artistId);
        String ip = CommonUtils.getIpAddr(request);


        WXPayRespDto wxPayRespDto;
        try {
            wxPayRespDto = wxPayService.wxPayArtistContact(uid, ip, aid);
        } catch (Exception e) {
            log.error("[微信支付] 获取艺人联系方式，失败！", e);
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
        }

        return ResponseUtils.getSuccessResponse(wxPayRespDto);
    }


    @RequestMapping("wxCallBack")
    @ResponseBody
    public String wxCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader reader = null;

        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        log.info("[微信支付]支付信息回调，xmlData:{}", xmlString);

        //执行回调处理
        wxPayService.processNotify(xmlString);

        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

}

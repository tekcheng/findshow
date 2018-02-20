package com.megamusic.findshow.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.megamusic.findshow.common.utils.AliyunOssUtils;
import com.megamusic.findshow.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;


/**
 * Created by maita on 17/8/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ArtistService artistService;


    @RequestMapping("get")
    @ResponseBody
    public Object findArtBYId(Long id){
        return artistService.getArtistById(id);
    }

    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        LOG.info("test~!!!!!!!");
        return "yeah~!";
    }

    @RequestMapping("tpl")
    public ModelAndView tpl(){

        ModelAndView mv = new ModelAndView("list");
        mv.addObject("msg", "啊啊啊啊啊啊啊啊");
        mv.setViewName("list");
        return mv;
    }



    @RequestMapping("send")
    @ResponseBody
    public Object sendsms() throws ClientException {
        return sendSms();
    }

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIO3aygVkKcnaJ";
    static final String accessKeySecret = "WWHvsi72TKvBlhyznQkVBCyj3DZr0J";

    public static SendSmsResponse sendSms() throws ClientException {
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers("13920390935");
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("麦塔");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_91875009");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\"12345\"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            return sendSmsResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }









}

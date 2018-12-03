package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.utils.RedisUtil;
import com.megamusic.findshow.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by maita on 17/8/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private ArtistService artistService;

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

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("setCache")
    @ResponseBody
    public Object setCache() {
        redisUtil.set("test1","123",30);
        return "";
    }

    @RequestMapping("getCache")
    @ResponseBody
    public Object getCache() {
        return redisUtil.get("test1");
    }

}

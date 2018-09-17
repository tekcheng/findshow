package com.megamusic.findshow.controller;

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
    public Object sendsms() {
        //return sendSms();
        return "";
    }

}

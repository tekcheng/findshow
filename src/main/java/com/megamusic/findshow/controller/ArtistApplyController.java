package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.ArtistApplyRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.ArtistApplyDto;
import com.megamusic.findshow.domain.entity.ArtistApplyInfo;
import com.megamusic.findshow.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chengchao on 2018/11/4.
 */
@Controller
@RequestMapping("/artistApply")
@Slf4j
public class ArtistApplyController {

    @Autowired
    private ArtistApplyRepository artistApplyRepository;

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "addDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response addDo(@RequestBody ArtistApplyDto artistApplyDto){

        //验证验证码
        if(StringUtils.isEmpty(artistApplyDto.getVerifyCode()))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        if(StringUtils.isEmpty(artistApplyDto.getPhone()))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);

        boolean checkResult = smsService.checkVerifyCode(artistApplyDto.getPhone(),artistApplyDto.getVerifyCode());
        if(!checkResult)
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.VERIFY_CODE_WRONG);


        ArtistApplyInfo artistApplyInfo = new ArtistApplyInfo();
        artistApplyInfo.setAddress(artistApplyDto.getAddress());
        artistApplyInfo.setIntro(artistApplyDto.getIntro());
        artistApplyInfo.setName(artistApplyDto.getName());
        artistApplyInfo.setPhone(artistApplyDto.getPhone());
        artistApplyInfo.setPhotos(artistApplyDto.getPhotos());
        artistApplyInfo.setExperience(artistApplyDto.getExperience());
        artistApplyInfo.setPrice(artistApplyDto.getPrice());
        artistApplyInfo.setType(artistApplyDto.getType());
        artistApplyInfo.setVideos(artistApplyDto.getVideos());
        artistApplyInfo.setWeixin(artistApplyDto.getWeixin());
        artistApplyInfo.setCreated(System.currentTimeMillis());
        artistApplyInfo.setUpdated(System.currentTimeMillis());
        artistApplyInfo.setSimpleTag(artistApplyDto.getSimpleTag());

        log.info("[艺人入驻申请]--- param:{}",artistApplyDto.toString());

        artistApplyRepository.save(artistApplyInfo);
        return ResponseUtils.getSuccessResponse("success");

    }

}

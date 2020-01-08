package com.megamusic.findshow.controller;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.AreaApplyRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.AreaApplyDto;
import com.megamusic.findshow.domain.dto.ArtistApplyDto;
import com.megamusic.findshow.domain.entity.AreaApply;
import com.megamusic.findshow.domain.entity.ArtistApplyInfo;
import com.megamusic.findshow.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chengchao on 2019/12/13.
 */
@Controller
@RequestMapping("/areaApply")
@Slf4j
public class AreaApplyController {

    @Autowired
    private AreaApplyRepository areaApplyRepository;

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "addDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response addDo(@RequestBody AreaApplyDto artistApplyDto){

        //验证验证码
        if(StringUtils.isEmpty(artistApplyDto.getVerifyCode()))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        if(StringUtils.isEmpty(artistApplyDto.getPhone()))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);

        //手机号校验
//        List<AreaApply> res = areaApplyRepository.findByPhone(artistApplyDto.getPhone().trim());
//        if(!CollectionUtils.isEmpty(res))
//            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PHONE_APPLY_IS_EXISTS);

        boolean checkResult = smsService.checkVerifyCode(artistApplyDto.getPhone().trim(),artistApplyDto.getVerifyCode().trim());
        if(!checkResult)
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.VERIFY_CODE_WRONG);


        AreaApply areaApply = new AreaApply();

        areaApply.setAttribute(artistApplyDto.getAttribute());
        areaApply.setBusinessImg(artistApplyDto.getBusinessImg());
        areaApply.setDescription(artistApplyDto.getDescription());
        areaApply.setDinner(artistApplyDto.getDinner());
        areaApply.setEntertainment( JSON.toJSON(artistApplyDto.getEntertainment()).toString() );
        areaApply.setEquipment( JSON.toJSON(artistApplyDto.getEquipment()).toString() );
        areaApply.setHallArea(artistApplyDto.getHallArea());
        areaApply.setHostingHistory(artistApplyDto.getHostingHistory());
        areaApply.setIdCardImg(artistApplyDto.getIdCardImg());
        areaApply.setJob(artistApplyDto.getJob());
        areaApply.setMeetingSquare(artistApplyDto.getMeetingSquare());
        areaApply.setName(artistApplyDto.getName());
        areaApply.setPhone(artistApplyDto.getPhone());
        areaApply.setPlaceCount(artistApplyDto.getPlaceCount());
        areaApply.setPlacePosition(artistApplyDto.getPlacePosition());
        areaApply.setPlaceName(artistApplyDto.getPlaceName());
        areaApply.setPlaceType(artistApplyDto.getPlaceType());
        areaApply.setRestaurantSquare(artistApplyDto.getRestaurantSquare());


        areaApply.setUpdated(System.currentTimeMillis());
        areaApply.setCreated(System.currentTimeMillis());


        log.info("[场地入驻申请]--- param:{}",artistApplyDto.toString());

        areaApplyRepository.save(areaApply);
        return ResponseUtils.getSuccessResponse("success");

    }

//    public static void main(String[] args) {
//        AreaApplyDto areaApplyDto = new AreaApplyDto();
//
//        areaApplyDto.setAttribute("场地属性");
//        areaApplyDto.setBusinessImg("营业执照");
//        areaApplyDto.setDescription("描述");
//        areaApplyDto.setDinner("晚餐");
//        areaApplyDto.setEntertainment(Arrays.asList("娱乐设施1"));
//        areaApplyDto.setEquipment(Arrays.asList("设备1"));
//        areaApplyDto.setHallArea("123");
//        areaApplyDto.setHostingHistory("曾举办");
//        areaApplyDto.setIdCardImg("身份证照片");
//        areaApplyDto.setJob("工作");
//        areaApplyDto.setMeetingSquare("3333");
//        areaApplyDto.setName("申请人");
//        areaApplyDto.setPhone("电话");
//        areaApplyDto.setPlaceCount("3333");
//        areaApplyDto.setPlacePosition("12121");
//        areaApplyDto.setPlaceName("场地名");
//        areaApplyDto.setPlaceType("场地类型");
//        areaApplyDto.setRestaurantSquare("餐厅面积");
//        System.out.println(JSON.toJSON(areaApplyDto).toString());
//    }
}

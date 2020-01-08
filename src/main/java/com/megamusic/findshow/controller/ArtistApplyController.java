package com.megamusic.findshow.controller;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.ArtistApplyRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.ArtistApplyDto;
import com.megamusic.findshow.domain.entity.Area;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.ArtistApplyInfo;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.ImageVo;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import com.megamusic.findshow.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        //手机号校验
        List<ArtistApplyInfo> res = artistApplyRepository.findByPhone(artistApplyDto.getPhone().trim());
        if(!CollectionUtils.isEmpty(res))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PHONE_APPLY_IS_EXISTS);

        boolean checkResult = smsService.checkVerifyCode(artistApplyDto.getPhone().trim(),artistApplyDto.getVerifyCode().trim());
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

    /**
     * 列表页
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(Integer pageNum){
        if(pageNum==null){
            pageNum = 0;
        }else{
            pageNum = pageNum-1;
        }

        Page<ArtistApplyInfo> page = getOpArtistList(pageNum,20);
        ModelAndView mv = new ModelAndView("list");
        mv.addObject("dataList", page.getContent());
        mv.addObject("curPage",page.getNumber()+1);
        mv.addObject("total",page.getTotalPages());
        mv.addObject("active","artistApplyList");
        mv.setViewName("artistApplyList");
        return mv;
    }


    @RequestMapping("detail")
    public ModelAndView detail(String aid) {

        ArtistApplyInfo artistApplyInfo = artistApplyRepository.findOne(Long.valueOf(aid));

        ModelAndView mv = new ModelAndView("artistApplyInfo");
        mv.addObject("submenu","artistApplyInfo");
        mv.addObject("artistApplyInfo",artistApplyInfo);



        List<ImageVo> imageVoList = new ArrayList<ImageVo>();
        if( artistApplyInfo!=null &&
                !StringUtils.isEmpty(artistApplyInfo.getPhotos()) ){
            String[] strImgs = artistApplyInfo.getPhotos().split(",");
            for( String is:strImgs ) {
                ImageVo imageVo = new ImageVo();
                imageVo.setUrl(is);
                imageVo.setId(CommonUtils.MD5(is));
                imageVoList.add(imageVo);
            }
        }
        mv.addObject("imgList",imageVoList);

        List<String> videoList = new ArrayList<String>();
        if( artistApplyInfo!=null &&
                !StringUtils.isEmpty(artistApplyInfo.getVideos()) ){
            String[] strVideos = artistApplyInfo.getVideos().split(",");
            videoList = Arrays.asList(strVideos);
        }
        mv.addObject("videoList",videoList);

        mv.addObject("active","edit");
        mv.setViewName("artistApplyInfo");
        return mv;
    }


    public Page<ArtistApplyInfo> getOpArtistList(Integer pageNum, Integer pageSize) {
        if(pageNum==null || pageNum<1)
            pageNum = 0;
        if(pageSize==null || pageSize<1 || pageSize>100 )
            pageSize = 20;

        Sort.Order sortEntity = new Sort.Order(Sort.Direction.DESC,"created");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(sortEntity);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);
        Page<ArtistApplyInfo> artistList = artistApplyRepository.findAll(pageable);
        if(artistList==null || CollectionUtils.isEmpty(artistList.getContent()) )
            return null;
        return new PageImpl<ArtistApplyInfo>(artistList.getContent(),pageable,artistList.getTotalElements());
    }

}

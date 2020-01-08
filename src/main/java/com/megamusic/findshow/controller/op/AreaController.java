package com.megamusic.findshow.controller.op;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.AreaRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.OpAreaRequestDto;
import com.megamusic.findshow.domain.entity.Area;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.ImageVo;
import com.megamusic.findshow.domain.op.OpAreaSimpleVo;
import com.megamusic.findshow.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2019/8/2.
 */
@Controller("opArea")
@RequestMapping("/op/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaRepository areaRepository;

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
        Page<OpAreaSimpleVo> page = areaService.getOpArtistList(pageNum,20);
        ModelAndView mv = new ModelAndView("list");
        mv.addObject("dataList", page.getContent());
        mv.addObject("curPage",page.getNumber()+1);
        mv.addObject("total",page.getTotalPages());
        mv.addObject("active","areaList");
        mv.setViewName("areaList");
        return mv;
    }

    @RequestMapping("list/getData")
    @ResponseBody
    public Page<OpAreaSimpleVo> ajaxList(Integer pageNum){
        if(pageNum==null){
            pageNum = 0;
        } else {
            pageNum = pageNum-1;
        }
        return areaService.getOpArtistList(pageNum,20);
    }

    /**
     * 场地添加
     * @return
     */
    @RequestMapping("add")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView("addArea");
        mv.addObject("active","areaAdd");
        mv.setViewName("addArea");
        return mv;
    }

    /**
     * 保存艺人信息
     * @return
     */
    @RequestMapping(value = "addDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response addDo(@RequestBody OpAreaRequestDto opAreaRequestDto){
        Area area = new Area();
        area.setCityId(opAreaRequestDto.getCityId());
        area.setName(opAreaRequestDto.getName());
        area.setTag(opAreaRequestDto.getTag());
        area.setSpTag(opAreaRequestDto.getSpTag());
        area.setAddrDistrict(opAreaRequestDto.getDistrict());
        area.setAddrStreet(opAreaRequestDto.getStreet());
        area.setAddrDesc(opAreaRequestDto.getAddrDesc());
        area.setAddrTag(opAreaRequestDto.getAddrTag());
        area.setAreaDescription(opAreaRequestDto.getDescription());
        area.setLaunchedActivities(opAreaRequestDto.getLaunchedActivities());
        area.setConferenceNumber(opAreaRequestDto.getConferenceNumber());
        area.setNumberOfPersons(opAreaRequestDto.getNumberOfPersons());
        area.setGuestRoomNumber(opAreaRequestDto.getGuestRoomNumber());
        area.setSquare(opAreaRequestDto.getSquare());
        area.setCover(opAreaRequestDto.getAvatar());
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getEquipment()) ){
            area.setEquipment(JSON.toJSONString(opAreaRequestDto.getEquipment()));
        }
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getService()) ){
            area.setService(JSON.toJSONString(opAreaRequestDto.getService()));
        }
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getImages()) ){
            area.setImages(JSON.toJSONString(opAreaRequestDto.getImages()));
        }
        area.setStatus(DeleteCodeEnum.NOT_DELETEED.getCode());
        area.setSort(opAreaRequestDto.getSort());
        area.setCreated(System.currentTimeMillis());
        area.setUpdated(System.currentTimeMillis());

        areaRepository.save(area);

        //return "redirect:/op/artist/list";
        return ResponseUtils.getSuccessResponse("success");
    }

    /**
     * 场地编辑
     * @return
     */
    @RequestMapping("edit")
    public ModelAndView edit(String aid) {

        Area area = areaRepository.findOne(Long.valueOf(aid));


        ModelAndView mv = new ModelAndView("editArtist");
        mv.addObject("submenu","editArtist");
        mv.addObject("area",area);
        mv.addObject("equipment",JSON.parseObject(area.getEquipment(),List.class));
        mv.addObject("service",JSON.parseObject(area.getService(),List.class));


        List<ImageVo> imageVoList = new ArrayList<ImageVo>();
        List<String> images = JSON.parseObject(area.getImages(),List.class);
        if( !CollectionUtils.isEmpty(images) ) {
            for( String is:images ) {
                ImageVo imageVo = new ImageVo();
                imageVo.setUrl(is);
                imageVo.setId(CommonUtils.MD5(is));
                imageVoList.add(imageVo);
            }
        }
        mv.addObject("imgList",imageVoList);
        mv.addObject("active","edit");
        mv.setViewName("editArea");
        return mv;
    }

    @RequestMapping(value = "editDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response editDo(@RequestBody OpAreaRequestDto opAreaRequestDto){
        //先查出来
        Area area = areaRepository.findOne(opAreaRequestDto.getId());
        area.setCityId(opAreaRequestDto.getCityId());
        area.setName(opAreaRequestDto.getName());
        area.setTag(opAreaRequestDto.getTag());
        area.setSpTag(opAreaRequestDto.getSpTag());
        area.setAddrDistrict(opAreaRequestDto.getDistrict());
        area.setAddrStreet(opAreaRequestDto.getStreet());
        area.setAddrDesc(opAreaRequestDto.getAddrDesc());
        area.setAddrTag(opAreaRequestDto.getAddrTag());
        area.setAreaDescription(opAreaRequestDto.getDescription());
        area.setLaunchedActivities(opAreaRequestDto.getLaunchedActivities());
        area.setConferenceNumber(opAreaRequestDto.getConferenceNumber());
        area.setNumberOfPersons(opAreaRequestDto.getNumberOfPersons());
        area.setGuestRoomNumber(opAreaRequestDto.getGuestRoomNumber());
        area.setSquare(opAreaRequestDto.getSquare());
        area.setCover(opAreaRequestDto.getAvatar());
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getEquipment()) ){
            area.setEquipment(JSON.toJSONString(opAreaRequestDto.getEquipment()));
        }
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getService()) ){
            area.setService(JSON.toJSONString(opAreaRequestDto.getService()));
        }
        if( !CollectionUtils.isEmpty(opAreaRequestDto.getImages()) ){
            area.setImages(JSON.toJSONString(opAreaRequestDto.getImages()));
        }
        area.setSort(opAreaRequestDto.getSort());
        area.setUpdated(System.currentTimeMillis());
        areaRepository.save(area);
        return ResponseUtils.getSuccessResponse("success");
    }


    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Response delete(String aid){
        if(StringUtils.isEmpty(aid))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        areaRepository.delete(Long.valueOf(aid));
        return ResponseUtils.getSuccessResponse("success");
    }



}

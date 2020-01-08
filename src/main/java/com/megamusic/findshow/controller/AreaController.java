package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chengchao on 2019/8/1.
 */
@Controller
@RequestMapping("/area")
@Slf4j
public class AreaController {

    @Autowired
    private AreaService areaService;


    /**
     * 场地列表
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Response areaList(Long userId){
        return ResponseUtils.getSuccessResponse(areaService.getAreaList(0,100));
    }

    /**
     * 场地详情页
     * @param aid
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Response artistDetail(String aid, Long userId){
        if(StringUtils.isEmpty(aid))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.CALL_REMOTE_FAIL);
        return ResponseUtils.getSuccessResponse(areaService.getAreaDetail(Long.valueOf(aid)));
    }
}

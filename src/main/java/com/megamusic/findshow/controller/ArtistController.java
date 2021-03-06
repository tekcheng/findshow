package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by maita on 17/8/17.
 */
@Controller
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("category/list")
    @ResponseBody
    public Response categoryList(@RequestParam(defaultValue = "0") String fpage,
                                @RequestParam(defaultValue = "0") Long cateId,
                                @RequestParam(defaultValue = "1") Integer cityId){
        Integer pageNum = Integer.valueOf(fpage);
        Integer pageSize= 10;
        DataCollectionVo<ArtistVo> dataCollectionVo = new DataCollectionVo<ArtistVo>();

        List<ArtistVo> rcmDataList = artistService.getArtistByCateIdAndCityId(cityId,cateId,pageNum,pageSize);

        Integer nextPage = pageNum + 1;
        dataCollectionVo.setFpage(nextPage+"");
        dataCollectionVo.setList(rcmDataList);
        if(CollectionUtils.isEmpty(rcmDataList))
            dataCollectionVo.setEnd(true);
        return ResponseUtils.getSuccessResponse(dataCollectionVo);

    }

    /**
     * 艺人详情页
     * @param aid
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Response artistDetail(String aid,Long userId){
        if(StringUtils.isEmpty(aid))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.CALL_REMOTE_FAIL);
        ArtistDetailVo artistDetailVo = artistService.getArtistDetail(Long.valueOf(aid),userId);
        return ResponseUtils.getSuccessResponse(artistDetailVo);
    }


}

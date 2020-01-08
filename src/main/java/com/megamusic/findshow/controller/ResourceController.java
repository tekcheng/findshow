package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.ResourceConstant;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import com.megamusic.findshow.domain.vo.ResourceVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.CategoryService;
import com.megamusic.findshow.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maita on 17/8/16.
 */
@Controller
@RequestMapping("/res")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArtistService artistService;




    @RequestMapping("index")
    @ResponseBody
    public Response index(String cityId) {
        //获取banner
        List<ResourceVo> bannerList = resourceService.getResContentById(Long.valueOf(cityId),ResourceConstant.INDEX_BANNER_CATEGORY_ID,0,20);
        List<ResourceVo> rcmNewsList = resourceService.getResContentById(Long.valueOf(cityId),ResourceConstant.INDEX_RECOMMEND_NEW_CATEGORY_ID,0,20);
        List<ResourceVo> rcmArtistList = resourceService.getResContentById(Long.valueOf(cityId),ResourceConstant.INDEX_RECOMMEND_ARTIST_CATEGORY_ID,0,20);
        List<ResourceVo> rcmFieldList = resourceService.getResContentById(Long.valueOf(cityId),ResourceConstant.INDEX_RECOMMEND_FIELD_CATEGORY_ID,0,20);

        //当前城市下艺人列表
        List<ArtistVo> rcmDataList = artistService.getArtistByCateIdAndCityId(Integer.valueOf(cityId),0L,0,100);


        Map<String,List> result = new HashMap<String,List>();
        result.put("banner",bannerList); //轮播图
        result.put("rcmNew",rcmNewsList); //每日推新
        result.put("rcmArtist",rcmArtistList); //推荐艺人
        result.put("rcmField",rcmFieldList); //推荐场地
        result.put("rcmDataList",rcmDataList); //推荐列表
        return ResponseUtils.getSuccessResponse(result);
    }

    @RequestMapping("index/recdata")
    @ResponseBody
    public Response indexRecdata(@RequestParam(defaultValue = "0") String fpage,String cityId){
        Integer pageNum = Integer.valueOf(fpage);
        Integer pageSize = 10;
        List<ResourceVo> result = resourceService.getResContentById(Long.valueOf(cityId),ResourceConstant.INDEX_RECOMMEND_NEW_CATEGORY_ID,pageNum,pageSize);
        DataCollectionVo<ResourceVo> dataCollectionVo = new DataCollectionVo<ResourceVo>();
        dataCollectionVo.setList(result);
        Integer nextPage = pageNum + 1;
        dataCollectionVo.setFpage(nextPage+"");
        if(CollectionUtils.isEmpty(result) || result.size()<pageSize ){
            dataCollectionVo.setEnd(true);
        }
        return ResponseUtils.getSuccessResponse(dataCollectionVo);
    }

}

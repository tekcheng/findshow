package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.ResourceConstant;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import com.megamusic.findshow.domain.vo.ResourceVo;
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


    @RequestMapping("index")
    @ResponseBody
    public Response index(){
        //获取banner
        List<ResourceVo> resourceVoList = resourceService.getResContentById(ResourceConstant.INDEX_BANNER_RESOURCE_ID,0,10);
        //获取分类信息
        List<CategoryVo> categoryVoList = categoryService.getAllCategory();

        Map<String,List> result = new HashMap<String,List>();
        result.put("banner",resourceVoList);
        result.put("category",categoryVoList);
        return ResponseUtils.getSuccessResponse(result);
    }

    @RequestMapping("index/banner")
    @ResponseBody
    public Response indexBanner(){
        List<ResourceVo> result = resourceService.getResContentById(ResourceConstant.INDEX_BANNER_RESOURCE_ID,0,10);
        DataCollectionVo<ResourceVo> dataCollectionVo = new DataCollectionVo<ResourceVo>();
        dataCollectionVo.setList(result);
        return ResponseUtils.getSuccessResponse(dataCollectionVo);
    }

    @RequestMapping("index/recdata")
    @ResponseBody
    public Response indexRecdata(@RequestParam(defaultValue = "0") String fpage){
        Integer pageNum = Integer.valueOf(fpage);
        Integer pageSize = 10;
        List<ResourceVo> result = resourceService.getResContentById(ResourceConstant.INDEX_RECOMMEND_DATA_RESCOURE_ID,pageNum,pageSize);
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

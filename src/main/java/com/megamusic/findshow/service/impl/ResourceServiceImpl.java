package com.megamusic.findshow.service.impl;

import com.megamusic.findshow.dao.ResContentRepository;
import com.megamusic.findshow.domain.entity.ResContent;
import com.megamusic.findshow.domain.vo.ResourceVo;
import com.megamusic.findshow.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maita on 17/8/16.
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private ResContentRepository resContentRepository;

    @Override
    public List<ResourceVo> getResContentById(Long cateId, Integer pageNum, Integer pageSize) {
        if( pageNum==null || pageNum<0 )
            pageNum=0;
        if(pageSize==null || pageSize>50)
            pageSize=20;

        List<ResourceVo> resultList = new ArrayList<ResourceVo>();

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"created");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);

        Page<ResContent> pageResult =resContentRepository.findByCategoryId(cateId,pageable);
        List<ResContent> resContentList = pageResult.getContent();

        if(CollectionUtils.isEmpty(resContentList)){
            return resultList;
        }

        for( ResContent resContent:resContentList  ){
            ResourceVo<String> resourceVo = new ResourceVo<String>();
            resourceVo.setContent(resContent.getContent());
            resourceVo.setContentId(resContent.getContentId()+"");
            resourceVo.setContentType(resContent.getContentType());
            resourceVo.setImage(resContent.getImage());
            resourceVo.setTitle(resContent.getTitle());
            resultList.add(resourceVo);
        }

        return resultList;
    }
}

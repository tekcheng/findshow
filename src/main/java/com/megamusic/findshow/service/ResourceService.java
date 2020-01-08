package com.megamusic.findshow.service;

import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.ResCategoryRepository;
import com.megamusic.findshow.dao.ResContentRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.ResCategory;
import com.megamusic.findshow.domain.entity.ResContent;
import com.megamusic.findshow.domain.entity.constant.ResContentTypeEnum;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.domain.vo.ResourceVo;
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
public class ResourceService {

    @Autowired
    private ResContentRepository resContentRepository;

    @Autowired
    private ResCategoryRepository resCategoryRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<ResourceVo> getResContentById(Long cityId,Long cateId, Integer pageNum, Integer pageSize) {
        //获取资源位
//        ResCategory resCategory = resCategoryRepository.findOne(cateId);
//        if(resCategory==null)
//            return null;

        //获取资源位下数据
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

        Page<ResContent> pageResult = resContentRepository.findByCityIdAndCategoryId(cityId,cateId,pageable);
        List<ResContent> resContentList = pageResult.getContent();

        if(CollectionUtils.isEmpty(resContentList)){
            return resultList;
        }

        for( ResContent resContent:resContentList  ){
            ResourceVo<String> resourceVo = new ResourceVo<String>();
            if(resContent.getContentType().equals(ResContentTypeEnum.LINK.getCode())){
                resourceVo.setContentType(ResContentTypeEnum.LINK.getCode());
                resourceVo.setImage(resContent.getImage());
                resourceVo.setTitle(resContent.getTitle());
                resourceVo.setContent(resContent.getContent());
            } else if(resContent.getContentType().equals(ResContentTypeEnum.ARTIST_ENTITY.getCode())) {
                resourceVo.setContentId(resContent.getContentId().toString());
                resourceVo.setContentType(ResContentTypeEnum.ARTIST_ENTITY.getCode());
                resourceVo.setImage(resContent.getImage());
                resourceVo.setTitle(resContent.getTitle());
            }
            resultList.add(resourceVo);
        }

        return resultList;
    }

    private ArtistVo generateArtistVo(Long artistId) {
        Artist artist = artistRepository.findOne(artistId);
        if(artist==null)
            return null;
        ArtistVo artistVo = new ArtistVo();
        artistVo.setId(artist.getId().toString());
        artistVo.setName(artist.getName());
        artistVo.setShortDesc(artist.getShortDesc());
        artistVo.setShortDesc2(artist.getShortDesc2());
        artistVo.setTipTag(artist.getTipTag());
        artistVo.setPopularity(artist.getPopularity());
        artistVo.setCover(artist.getCover());
        return artistVo;
    }
}

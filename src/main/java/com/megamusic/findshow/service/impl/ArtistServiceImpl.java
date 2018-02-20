package com.megamusic.findshow.service.impl;

import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.CategoryRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by maita on 17/8/14.
 */
@Service("artistService")
public class ArtistServiceImpl implements ArtistService{

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * test
     * @param id
     * @return
     */
    @Override
    public ArtistVo getArtistById(Long id) {
        Artist artist = artistRepository.findOne(id);
        ArtistVo artistVo = new ArtistVo();
        BeanUtils.copyProperties(artist,artistVo);
        return artistVo;
    }

    @Override
    public List<ArtistVo> getArtistByCateId(Long cateId, Integer pageNum, Integer pageSize) {
        if(pageSize==null) pageSize=20;
        if(pageNum==null || pageNum<0) pageNum=0;

        List<ArtistVo> resultList = new ArrayList<ArtistVo>();
        if (cateId == null || cateId<0)
            return resultList;

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"created");
        Sort.Order order3 = new Sort.Order(Sort.Direction.DESC,"id");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);

        /**
         * cateId 为0 拉去全部
         */
        List<Artist> artistList = new ArrayList<Artist>();
        if( cateId==0L ){
            artistList = artistRepository.findByIsDeleted(DeleteCodeEnum.NOT_DELETEED.getCode(),pageable).getContent();
        } else {
            artistList = artistRepository.findByCategoryIdAndIsDeleted(cateId, DeleteCodeEnum.NOT_DELETEED.getCode(),pageable).getContent();
        }


        if(CollectionUtils.isEmpty(artistList))
            return resultList;

        List<Long> cids = new ArrayList<Long>();
        for( Artist artist:artistList ) {
            cids.add(artist.getCategoryId());
        }
        Map<Long,Category> categoryMap = batchGetCategory(cids);

        for( Artist artist:artistList ) {
            ArtistVo artistVo = new ArtistVo();
            artistVo.setId(artist.getId()+"");
            artistVo.setName(artist.getName());
            artistVo.setType(artist.getType());
            artistVo.setDescription(artist.getDescription());
            artistVo.setCover(artist.getCover());
            if( artist.getCategoryId()!=null ){
                artistVo.setCategoryId(artist.getCategoryId().toString());
                Category category = categoryMap.get(artist.getCategoryId());
                if(category!=null)
                    artistVo.setCategoryName( category.getName() );
            }
            resultList.add(artistVo);
        }

        return resultList;
    }

    @Override
    public ArtistDetailVo getArtistDetail(Long id) {
        if( id==null || id<0L )
            return null;
        ArtistDetailVo artistDetailVo = new ArtistDetailVo();
        Artist artist = artistRepository.findByIdAndIsDeleted(id,DeleteCodeEnum.NOT_DELETEED.getCode());
        if(artist==null)
            return null;
        artistDetailVo.setId(artist.getId().toString());
        artistDetailVo.setDescription(artist.getDescription());
        artistDetailVo.setName(artist.getName());
        artistDetailVo.setCateId(artist.getCategoryId().toString());
        if( !StringUtils.isEmpty(artist.getImages()) ){
            List<String> imgs = Arrays.asList(artist.getImages().split(","));
            artistDetailVo.setImages(imgs);
        }
        if( !StringUtils.isEmpty(artist.getVideoInfo()) ){
            List<String> videos = Arrays.asList(artist.getVideoInfo().split(","));
            artistDetailVo.setVideoInfo(videos);
        }
        return artistDetailVo;
    }



    @Override
    public Page<OpArtistVo> getOpArtistList(Integer pageNum, Integer pageSize) {
        if(pageNum==null || pageNum<1)
            pageNum = 0;
        if(pageSize==null || pageSize<1 || pageSize>100 )
            pageSize = 20;

        List<OpArtistVo> resultList = new ArrayList<OpArtistVo>();
        Sort.Order sortEntity = new Sort.Order(Sort.Direction.DESC,"created");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(sortEntity);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);
        Page<Artist> artistList = artistRepository.findByIsDeleted(DeleteCodeEnum.NOT_DELETEED.getCode(),pageable);
        if(artistList==null || CollectionUtils.isEmpty(artistList.getContent()) )
            return null;

        List<Long> cids = new ArrayList<Long>();
        for( Artist artist:artistList ) {
            cids.add(artist.getCategoryId());
        }

        Map<Long,Category> categoryMap = batchGetCategory(cids);
        for( Artist artist:artistList ) {
            OpArtistVo opArtistVo = new OpArtistVo();
            opArtistVo.setId(artist.getId().toString());
            opArtistVo.setName(artist.getName());
            opArtistVo.setDescription(artist.getDescription());
            if(artist.getSort()!=null){
                opArtistVo.setSort(artist.getSort().toString());
            }
            opArtistVo.setCover(artist.getCover());
            opArtistVo.setCreated(artist.getCreated());
            opArtistVo.setUpdated(artist.getUpdated());
            if(artist.getCategoryId()!=null){
                opArtistVo.setCategoryId(artist.getCategoryId().toString());
                Category category = categoryMap.get(artist.getCategoryId());
                if(category!=null){
                    opArtistVo.setCategoryName( category.getName() );
                }
            }
            resultList.add(opArtistVo);
        }

        Page<OpArtistVo> result = new PageImpl<OpArtistVo>(resultList,pageable,artistList.getTotalElements());
        return result;
    }


    /**
     * 批量获取栏目信息
     * @param cateIds
     * @return
     */
    private Map<Long,Category> batchGetCategory( List<Long> cateIds ){
        Map<Long,Category> resultMap = new HashMap<Long, Category>();
        if( CollectionUtils.isEmpty(cateIds) ){
            return resultMap;
        }
        List<Category> categoryList = categoryRepository.findByIdIn(cateIds);
        for( Category category:categoryList ){
            resultMap.put(category.getId(),category);
        }
        return resultMap;
    }

    @Override
    public List<ArtistVo> getRecommendArtist( Integer pageNum, Integer pageSize ) {
        List<ArtistVo> resultList = new ArrayList<ArtistVo>();
        return null;
    }
}

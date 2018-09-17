package com.megamusic.findshow.service.op;

import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.CategoryRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.OpArtistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chengchao on 2018/9/12.
 */
@Service("opArtistService")
public class OpArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

}

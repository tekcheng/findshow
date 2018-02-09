package com.megamusic.findshow.service.impl;

import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maita on 17/8/14.
 */
@Service("artistService")
public class ArtistServiceImpl implements ArtistService{

    @Autowired
    private ArtistRepository artistRepository;

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
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"created");
        Sort.Order order3 = new Sort.Order(Sort.Direction.DESC,"id");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);
        List<Artist> artistList = artistRepository.findByCategoryId(cateId,pageable).getContent();
        if(CollectionUtils.isEmpty(artistList))
            return resultList;
        for( Artist artist:artistList ){
            ArtistVo artistVo = new ArtistVo();
            artistVo.setId(artist.getId()+"");
            artistVo.setName(artist.getName());
            artistVo.setType(artist.getType());
            artistVo.setDescription(artist.getDescription());
            artistVo.setCover(artist.getCover());
            resultList.add(artistVo);
        }

        return resultList;
    }

    @Override
    public ArtistDetailVo getArtistDetail(Long id) {
        if( id==null || id<0L )
            return null;
        ArtistDetailVo artistDetailVo = new ArtistDetailVo();
        Artist artist = artistRepository.findOne(id);
        if(artist==null)
            return null;
        artistDetailVo.setId(artist.getId().toString());
        artistDetailVo.setDescription(artist.getDescription());
        artistDetailVo.setName(artist.getName());
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
    public List<ArtistVo> getRecommendArtist() {
        List<ArtistVo> resultList = new ArrayList<ArtistVo>();



        return null;
    }
}

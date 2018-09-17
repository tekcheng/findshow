package com.megamusic.findshow.service;

import com.megamusic.findshow.dao.*;
import com.megamusic.findshow.domain.entity.*;
import com.megamusic.findshow.domain.entity.constant.CityEnum;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistExpVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import com.megamusic.findshow.domain.vo.RelateNewsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by maita on 17/8/14.
 */
@Service("artistService")
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArtistInfoRepository artistInfoRepository;

    @Autowired
    private ArtistExperienceRepository artistExperienceRepository;

    @Autowired
    private ArtistNewsRepository artistNewsRepository;

    /**
     * test
     *
     * @param id
     * @return
     */
    public ArtistVo getArtistById(Long id) {
        Artist artist = artistRepository.findOne(id);
        ArtistVo artistVo = new ArtistVo();
        BeanUtils.copyProperties(artist, artistVo);
        return artistVo;
    }

    public List<ArtistVo> getArtistByCateId(Long cateId, Integer pageNum, Integer pageSize) {
        if (pageSize == null) pageSize = 20;
        if (pageNum == null || pageNum < 0) pageNum = 0;

        List<ArtistVo> resultList = new ArrayList<ArtistVo>();

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "created");
        Sort.Order order3 = new Sort.Order(Sort.Direction.DESC, "id");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum, pageSize, sort);

        /**
         * cateId 为0 拉去全部
         */
        List<Artist> artistList = new ArrayList<Artist>();
        if (cateId == null || cateId == 0L) {
            artistList = artistRepository.findByIsDeleted(DeleteCodeEnum.NOT_DELETEED.getCode(), pageable).getContent();
        } else {
            artistList = artistRepository.findByCategoryIdAndIsDeleted(cateId, DeleteCodeEnum.NOT_DELETEED.getCode(), pageable).getContent();
        }


        if (CollectionUtils.isEmpty(artistList))
            return resultList;

        List<Long> cids = new ArrayList<Long>();
        for (Artist artist : artistList) {
            cids.add(artist.getCategoryId());
        }
        Map<Long, Category> categoryMap = batchGetCategory(cids);

        for (Artist artist : artistList) {
            ArtistVo artistVo = new ArtistVo();
            artistVo.setId(artist.getId() + "");
            artistVo.setName(artist.getName());
            artistVo.setType(artist.getType());
            artistVo.setDescription(artist.getDescription());
            artistVo.setCover(artist.getCover());
            if (artist.getCategoryId() != null) {
                artistVo.setCategoryId(artist.getCategoryId().toString());
                Category category = categoryMap.get(artist.getCategoryId());
                if (category != null)
                    artistVo.setCategoryName(category.getName());
            }
            resultList.add(artistVo);
        }

        return resultList;
    }

    /**
     * 艺人详情页
     *
     * @param id
     * @return
     */
    public ArtistDetailVo getArtistDetail(Long id) {
        if (id == null || id < 0L)
            return null;

        ArtistDetailVo artistDetailVo = new ArtistDetailVo();
        Artist artist = artistRepository.findByIdAndIsDeleted(id, DeleteCodeEnum.NOT_DELETEED.getCode());
        if (artist == null)
            return null;

        artistDetailVo.setId(artist.getId().toString());
        artistDetailVo.setType(artist.getType().getCode());
        artistDetailVo.setName(artist.getName());
        artistDetailVo.setAge(artist.getAge());
        artistDetailVo.setAvatar(artist.getCover());
        artistDetailVo.setPopularity(artist.getPopularity());
        artistDetailVo.setDescription(artist.getDescription());
        artistDetailVo.setPrice(artist.getPrice());

        //获取城市 和 栏目
        Category category = categoryRepository.findById(artist.getCategoryId());
        if (category != null) {
            artistDetailVo.setCategory(category.getName());
        }
        CityEnum cityEnum = CityEnum.getCityById(artist.getCityId());
        if (cityEnum != null) {
            artistDetailVo.setCityName(cityEnum.getCityName());
        }

        //视频和图片
        if (!StringUtils.isEmpty(artist.getImages())) {
            List<String> imgs = Arrays.asList(artist.getImages().split(","));
            artistDetailVo.setImages(imgs);
        }
        if (!StringUtils.isEmpty(artist.getVideoInfo())) {
            List<String> videos = Arrays.asList(artist.getVideoInfo().split(","));
            artistDetailVo.setVideoInfo(videos);
        }

        //获取艺人资料信息
        generateArtistDetailVo(artistDetailVo);

        //获取艺人经历&新闻
        generateArtistExpAndNews(artistDetailVo);

        return artistDetailVo;
    }

    //获取艺人资料信息
    private ArtistDetailVo generateArtistDetailVo(ArtistDetailVo artistDetailVo) {
        ArtistInfo artistInfo = artistInfoRepository.findByArtistId(Long.valueOf(artistDetailVo.getId()));
        if (artistInfo == null)
            return artistDetailVo;

        artistDetailVo.setMajor(artistInfo.getMajor());
        artistDetailVo.setStyle(artistInfo.getStyle());
        artistDetailVo.setHeight(artistInfo.getHeight());
        artistDetailVo.setWeight(artistInfo.getWeight());
        artistDetailVo.setFans(artistInfo.getFans());

        //TODO 是否可见
        artistDetailVo.setWeibo(artistInfo.getWeibo());
        artistDetailVo.setWeixin(artistInfo.getWeixin());
        artistDetailVo.setDouyin(artistInfo.getDouyin());
        artistDetailVo.setMobile(artistInfo.getMobile());

        return artistDetailVo;
    }

    //获取经历和新闻
    private ArtistDetailVo generateArtistExpAndNews(ArtistDetailVo artistDetailVo) {
        List<ArtistExperience> artistExperienceList = artistExperienceRepository.findByArtistId(Long.valueOf(artistDetailVo.getId()));
        List<ArtistNews> artistNewsList = artistNewsRepository.findByArtistId(Long.valueOf(artistDetailVo.getId()));
        if (!CollectionUtils.isEmpty(artistExperienceList)) {
            List<ArtistExpVo> artistExpVoList = new ArrayList<ArtistExpVo>();
            for( ArtistExperience ae:artistExperienceList ){
                ArtistExpVo artistExpVo = new ArtistExpVo();
                artistExpVo.setDetail(ae.getDetail());
                artistExpVo.setType(ae.getType().toString());
                artistExpVoList.add(artistExpVo);
            }
            artistDetailVo.setExperience(artistExpVoList);
        }
        if (!CollectionUtils.isEmpty(artistNewsList)) {
            List<RelateNewsVo> relateNewsVoList = new ArrayList<RelateNewsVo>();
            for( ArtistNews an:artistNewsList ){
                RelateNewsVo relateNewsVo = new RelateNewsVo();
                relateNewsVo.setTitle(an.getTitle());
                relateNewsVo.setLinkUrl(an.getLinkUrl());
                relateNewsVoList.add(relateNewsVo);
            }
            artistDetailVo.setNews(relateNewsVoList);
        }
        return artistDetailVo;
    }

    //批量获取栏目信息
    private Map<Long, Category> batchGetCategory(List<Long> cateIds) {
        Map<Long, Category> resultMap = new HashMap<Long, Category>();
        if (CollectionUtils.isEmpty(cateIds)) {
            return resultMap;
        }
        List<Category> categoryList = categoryRepository.findByIdIn(cateIds);
        for (Category category : categoryList) {
            resultMap.put(category.getId(), category);
        }
        return resultMap;
    }


    public List<ArtistVo> getRecommendArtist(Integer pageNum, Integer pageSize) {
        List<ArtistVo> resultList = new ArrayList<ArtistVo>();
        return null;
    }
}

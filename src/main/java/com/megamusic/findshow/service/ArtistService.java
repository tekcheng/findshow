package com.megamusic.findshow.service;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.dao.ArtistInfoRepository;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.CategoryRepository;
import com.megamusic.findshow.dao.OrderRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.ArtistInfo;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.Order;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import com.megamusic.findshow.domain.entity.constant.CityEnum;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private OrderRepository orderRepository;


    public List<ArtistVo> getArtistByCateIdAndCityId(Integer cityId,Long cateId, Integer pageNum, Integer pageSize) {
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
            artistList = artistRepository.findByCityIdAndIsDeleted(cityId,DeleteCodeEnum.NOT_DELETEED.getCode(), pageable).getContent();
        } else {
            artistList = artistRepository.findByCityIdAndCategoryIdAndIsDeleted(cityId,cateId, DeleteCodeEnum.NOT_DELETEED.getCode(), pageable).getContent();
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
            artistVo.setType(ArtistTypeEnum.getEnumByCode(artist.getType()));
            artistVo.setShortDesc(artist.getShortDesc());
            artistVo.setShortDesc2(artist.getShortDesc2());
            artistVo.setCover(artist.getCover());
            artistVo.setPopularity(artist.getPopularity());
            artistVo.setTipTag(artist.getTipTag());
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
    public ArtistDetailVo getArtistDetail(Long id, Long userId) {
        if (id == null || id < 0L)
            return null;

        ArtistDetailVo artistDetailVo = new ArtistDetailVo();
        Artist artist = artistRepository.findByIdAndIsDeleted(id, DeleteCodeEnum.NOT_DELETEED.getCode());
        if (artist == null)
            return null;

        artistDetailVo.setId(artist.getId().toString());
        artistDetailVo.setType(artist.getType());
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
        generateArtistDetailVo(artistDetailVo, id, userId);

        return artistDetailVo;
    }

    //获取艺人资料信息
    private ArtistDetailVo generateArtistDetailVo(ArtistDetailVo artistDetailVo, Long artistId, Long userId) {
        ArtistInfo artistInfo = artistInfoRepository.findByArtistId(artistId);
        if (artistInfo == null)
            return artistDetailVo;

        artistDetailVo.setMajor(artistInfo.getMajor());
        artistDetailVo.setStyle(artistInfo.getStyle());
        artistDetailVo.setHeight(artistInfo.getHeight());
        artistDetailVo.setWeight(artistInfo.getWeight());
        artistDetailVo.setFans(artistInfo.getFans());

        artistDetailVo.setShowContact(false);
        //判断联系方式是否可见
        if (userId != null) {
            Order order = orderRepository.getByUserIdAndArtistIdAndPayStatus(userId, artistId, Order.PayStatus.CALLBACK.getCode());
            if(order!=null){
                artistDetailVo.setShowContact(true);
                artistDetailVo.setWeibo(artistInfo.getWeibo());
                artistDetailVo.setWeixin(artistInfo.getWeixin());
                artistDetailVo.setDouyin(artistInfo.getDouyin());
                artistDetailVo.setMobile(artistInfo.getMobile());
            }
        }


        if (!StringUtils.isEmpty(artistInfo.getArtistExperience()))
            artistDetailVo.setExperience(JSON.parseObject(artistInfo.getArtistExperience(), List.class));
        if (!StringUtils.isEmpty(artistInfo.getArtistNews()))
            artistDetailVo.setNews(JSON.parseObject(artistInfo.getArtistNews(), List.class));
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

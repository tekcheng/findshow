package com.megamusic.findshow.service;

import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;

import java.util.List;

/**
 * Created by maita on 17/8/14.
 */
public interface ArtistService {
    ArtistVo getArtistById(Long id);
    List<ArtistVo> getArtistByCateId(Long cateId,Integer pageNum,Integer pageSize);
    ArtistDetailVo getArtistDetail(Long id);
    List<ArtistVo> getRecommendArtist();
}

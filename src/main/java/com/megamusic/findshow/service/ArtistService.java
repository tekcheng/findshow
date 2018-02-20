package com.megamusic.findshow.service;

import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.ArtistDetailVo;
import com.megamusic.findshow.domain.vo.ArtistVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by maita on 17/8/14.
 */
public interface ArtistService {
    /**
     * test
     * @param id
     * @return
     */
    ArtistVo getArtistById(Long id);
    List<ArtistVo> getArtistByCateId(Long cateId,Integer pageNum,Integer pageSize);
    ArtistDetailVo getArtistDetail(Long id);
    /**
     * 后台查询获取所有艺人列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<OpArtistVo> getOpArtistList(Integer pageNum, Integer pageSize);
    List<ArtistVo> getRecommendArtist(Integer pageNum, Integer pageSize);
}

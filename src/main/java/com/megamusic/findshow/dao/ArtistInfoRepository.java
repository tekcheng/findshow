package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.ArtistInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2018/9/12.
 */
public interface ArtistInfoRepository  extends JpaRepository<ArtistInfo,Long> {

    ArtistInfo findByArtistId(Long artistId);

}

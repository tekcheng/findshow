package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.ArtistApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2018/11/4.
 */
public interface ArtistApplyRepository extends JpaRepository<ArtistApplyInfo,Long> {
}

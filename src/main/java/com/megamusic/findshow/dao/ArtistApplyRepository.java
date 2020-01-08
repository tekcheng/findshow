package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.ArtistApplyInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2018/11/4.
 */
public interface ArtistApplyRepository extends JpaRepository<ArtistApplyInfo,Long> {

    List<ArtistApplyInfo> findByPhone(String phone);
    Page<ArtistApplyInfo> findAll(Pageable pageable);
}

package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.ArtistNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2018/9/12.
 */
public interface ArtistNewsRepository extends JpaRepository<ArtistNews,Long>{
    List<ArtistNews> findByArtistId(Long artistId);
}

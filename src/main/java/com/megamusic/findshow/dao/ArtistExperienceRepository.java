package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.ArtistExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2018/9/12.
 */
public interface ArtistExperienceRepository extends JpaRepository<ArtistExperience,Long> {
    List<ArtistExperience> findByArtistId(Long artistId);
}

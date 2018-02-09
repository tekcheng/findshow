package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by maita on 17/8/14.
 */
public interface ArtistRepository extends JpaRepository<Artist,Long> {
    Page<Artist> findByCategoryId(Long cateId, Pageable pageable);
}

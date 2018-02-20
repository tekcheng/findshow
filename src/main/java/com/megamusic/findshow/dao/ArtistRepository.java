package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.service.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by maita on 17/8/14.
 */
public interface ArtistRepository extends JpaRepository<Artist,Long> {
    Page<Artist> findByCategoryIdAndIsDeleted(Long cateId,Integer isDeleted,Pageable pageable);
    Artist findByIdAndIsDeleted(Long id,Integer isDeleted);
    Page<Artist> findByIsDeleted(Integer isDeleted,Pageable pageable);

}

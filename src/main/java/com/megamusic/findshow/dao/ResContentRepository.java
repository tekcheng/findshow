package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.ResContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by maita on 17/8/16.
 */
public interface ResContentRepository extends JpaRepository<ResContent,Long>{
    Page<ResContent> findByCityIdAndCategoryId(Long cityId,Long cateId, Pageable pageable);

}

package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2019/8/1.
 */
public interface AreaRepository extends JpaRepository<Area,Long> {

    Page<Area> findByCityId(Long cityId,Pageable pageable);

    Area findByIdAndStatus(Long id,Integer status);
}

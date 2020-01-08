package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2019/7/25.
 */
public interface CityRepository extends JpaRepository<City,Long> {

    List<City> findByStatus(Pageable pageable, Integer status);

    List<City> findByIdIn(List<Long> ids);

    City findById(Long id);
}

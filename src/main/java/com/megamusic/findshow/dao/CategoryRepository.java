package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by maita on 17/8/16.
 */
public interface CategoryRepository extends JpaRepository<Category,Long>{
    List<Category> findByStatus(Pageable pageable,Integer status);
    List<Category> findByIdIn(List<Long> ids);
    Category findById(Long id);
}

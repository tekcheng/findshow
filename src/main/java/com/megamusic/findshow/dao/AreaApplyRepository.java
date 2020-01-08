package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.AreaApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2019/12/13.
 */
public interface AreaApplyRepository extends JpaRepository<AreaApply,Long> {

    List<AreaApply> findByPhone(String phone);
    Page<AreaApply> findAll(Pageable pageable);

}

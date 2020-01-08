package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chengchao on 2018/10/8.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order getByOrderNo(String orderNo);

    Order getByUserIdAndArtistIdAndPayStatusAndOrderType(Long userId, Long artistId, Integer payStatus,Integer orderType);

    List<Order> getByUserIdAndAndPayStatusAndOrderType(Long userId, Integer payStatus, Integer orderType, Pageable pageable);

}

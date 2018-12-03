package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.Order;
import com.megamusic.findshow.domain.entity.constant.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2018/10/8.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order getByOrderNo(String orderNo);

    Order getByUserIdAndArtistIdAndPayStatus(Long userId, Long artistId, PayStatus payStatus);

}

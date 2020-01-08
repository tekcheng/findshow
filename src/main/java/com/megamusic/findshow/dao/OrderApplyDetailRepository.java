package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.OrderApplyDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2019/3/18.
 */
public interface OrderApplyDetailRepository  extends JpaRepository<OrderApplyDetail,Long> {

    OrderApplyDetail findByOrderNo(String orderNo);

}

package com.megamusic.findshow.domain.entity;

import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.entity.constant.OrderType;
import com.megamusic.findshow.domain.entity.constant.PayStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by chengchao on 2018/9/29.
 */
@Data
@Entity
@Table(name = "FSOrder")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String orderNo;

    @Column
    private String transactionId; //微信支付订单号

    //订单标题
    @Column
    private String orderTitle;

    //订单备注描述
    @Column
    private String orderInfo;

    //订单金额
    @Column
    private BigDecimal orderAmount;

    //支付金额
    @Column
    private BigDecimal payAmount;

    //商品id
    @Column
    private Long artistId;

    //商品类型
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private ArtistTypeEnum artistType;

    //订单类型
    @Column(nullable = false,columnDefinition="tinyint default 1")
    private OrderType orderType;

    //支付状态
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private PayStatus payStatus;

    //删除 1 删除
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private DeleteCodeEnum isDeleted;

    @Column
    private Long created;

    @Column
    private Long updated;
}

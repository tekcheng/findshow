package com.megamusic.findshow.domain.entity;

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
    private Integer artistType;

    //订单类型
    @Column(nullable = false,columnDefinition="tinyint default 1")
    private Integer orderType;

    //支付状态
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer payStatus;

    //删除 1 删除
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer isDeleted;

    @Column
    private Long created;

    @Column
    private Long updated;


    public enum OrderType {
        CONTACT(1,"查看联系方式"),RESERVE(2,"预定");

        private Integer code;
        private String name;

        OrderType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static OrderType getOrderTypeByCode(Integer code){
            for( OrderType orderType:OrderType.values() ){
                if(orderType.getCode().equals(code)){
                    return orderType;
                }
            }
            return null;
        }
    }


    public enum PayStatus {
        INIT(0,"初始化"),
        AUDITING(1,"审核中"),
        PASS(2,"审核通过待支付"),
        REJECT(3,"审核拒绝-已关闭"),
        PAY_SUCCESS(4,"支付成功-待确认"),
        FAIL(5,"支付失败"),
        CALLBACK(6,"回调成功"),
        DONE(7,"已确认完成")
        ;

        private Integer code;
        private String name;

        PayStatus(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static PayStatus getPayStatusByCode(Integer code){
            for( PayStatus payStatus:PayStatus.values() ){
                if(payStatus.getCode().equals(code)){
                    return payStatus;
                }
            }
            return null;
        }
    }
}

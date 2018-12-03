package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/10/8.
 */
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
}

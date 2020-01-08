package com.megamusic.findshow.domain.op;

import lombok.Data;

/**
 * Created by chengchao on 2019/8/2.
 */
@Data
public class OpAreaSimpleVo {

    private Long id;

    private String name;

    private String address; //地址

    private Integer sort=0;//权重

    private Integer status=0; //状态 0 正常 1 下线

    private Long created;

    private Long updated;
}

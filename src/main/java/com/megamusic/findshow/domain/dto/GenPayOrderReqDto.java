package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by chengchao on 2019/3/18.
 */
@Data
@ToString
public class GenPayOrderReqDto {

    private Long userId;

    private String orderNo;

}

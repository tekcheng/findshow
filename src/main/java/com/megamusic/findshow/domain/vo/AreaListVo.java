package com.megamusic.findshow.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chengchao on 2019/8/1.
 */
@Data
@ToString
public class AreaListVo {

    private Long id;

    private String name;

    private String cover;//封面图

    private String tag;  //场地标签

    private String spTag; //特性标签

    private String addrTag; //地址标志

    private Long square; //面积

    private Long numberOfPersons = 0L; //容纳人数
}

package com.megamusic.findshow.service;

import com.megamusic.findshow.domain.vo.ResourceVo;

import java.util.List;

/**
 * Created by maita on 17/8/16.
 */
public interface ResourceService {

    List<ResourceVo> getResContentById(Long cateId,Integer pageNum,Integer pageSize);

}

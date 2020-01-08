package com.megamusic.findshow.service;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.dao.AreaRepository;
import com.megamusic.findshow.domain.entity.Area;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.op.OpAreaSimpleVo;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.AreaListVo;
import com.megamusic.findshow.domain.vo.AreaVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by chengchao on 2019/8/1.
 */
@Service("areaService")
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    /**
     * 场地详情
     * @param id
     * @return
     */
    public AreaVo getAreaDetail(Long id) {
        AreaVo areaVo = new AreaVo();
        Area area = areaRepository.findByIdAndStatus(id, DeleteCodeEnum.NOT_DELETEED.getCode());
        if (area == null) {
            return null;
        }
        BeanUtils.copyProperties(area,areaVo);
        if(!StringUtils.isEmpty(area.getImages())){
            List<String> imgList = JSON.parseObject(area.getImages(), List.class);
            areaVo.setImageList(imgList);
        }
        if(!StringUtils.isEmpty(area.getEquipment())){
            List<String> eqList = JSON.parseObject(area.getEquipment(), List.class);
            areaVo.setEquipmentList(eqList);
        }
        if(!StringUtils.isEmpty(area.getService())){
            List<String> serviceList = JSON.parseObject(area.getService(), List.class);
            areaVo.setServiceList(serviceList);
        }
        return areaVo;
    }

    public Page<OpAreaSimpleVo> getOpArtistList(Integer pageNum, Integer pageSize) {
        if(pageNum==null || pageNum<1)
            pageNum = 0;
        if(pageSize==null || pageSize<1 || pageSize>100 )
            pageSize = 20;

        List<OpAreaSimpleVo> resultList = new ArrayList<OpAreaSimpleVo>();
        Sort.Order sortEntity = new Sort.Order(Sort.Direction.DESC,"created");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(sortEntity);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);
        Page<Area> areaList = areaRepository.findAll(pageable);
        if(areaList==null || CollectionUtils.isEmpty(areaList.getContent()) )
            return null;

        for( Area area:areaList ) {
            OpAreaSimpleVo opArtistVo = new OpAreaSimpleVo();
            BeanUtils.copyProperties(area,opArtistVo);
            opArtistVo.setAddress(area.getAddrDistrict()+area.getAddrStreet()+area.getAddrTag());
            resultList.add(opArtistVo);
        }

        Page<OpAreaSimpleVo> result = new PageImpl<OpAreaSimpleVo>(resultList,pageable,areaList.getTotalElements());
        return result;
    }

    public List<AreaListVo> getAreaList(Integer pageNum, Integer pageSize) {
        if(pageNum==null || pageNum<1)
            pageNum = 0;
        if(pageSize==null || pageSize<1 || pageSize>100 )
            pageSize = 20;

        List<AreaListVo> resultList = new ArrayList<AreaListVo>();
        Sort.Order sortEntity = new Sort.Order(Sort.Direction.DESC,"created");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(sortEntity);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(pageNum,pageSize,sort);
        Page<Area> areaList = areaRepository.findAll(pageable);
        if(areaList==null || CollectionUtils.isEmpty(areaList.getContent()) )
            return null;

        for( Area area:areaList ) {
            AreaListVo areaListVo = new AreaListVo();
            BeanUtils.copyProperties(area,areaListVo);
            resultList.add(areaListVo);
        }

        Page<AreaListVo> result = new PageImpl<AreaListVo>(resultList,pageable,areaList.getTotalElements());
        return result.getContent();
    }


}

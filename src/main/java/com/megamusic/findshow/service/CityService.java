package com.megamusic.findshow.service;

import com.megamusic.findshow.dao.CityRepository;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.City;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.domain.vo.CityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2019/7/25.
 */
@Service("cityService")
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityVo> getAllCity() {

        List<CityVo> cityVoArrayList = new ArrayList<CityVo>();

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"id");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(0,100,sort);
        List<City> cityList = cityRepository.findByStatus(pageable,0);
        if(CollectionUtils.isEmpty(cityList))
            return cityVoArrayList;

        for ( City item:cityList ) {
            CityVo cityVo = new CityVo();
            cityVo.setId(item.getId());
            cityVo.setName(item.getName());
            cityVoArrayList.add(cityVo);
        }

        return cityVoArrayList;
    }
}

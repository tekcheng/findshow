package com.megamusic.findshow.service.impl;

import com.megamusic.findshow.dao.CategoryRepository;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maita on 17/8/23.
 */
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<CategoryVo> getAllCategory() {

        List<CategoryVo> categoryVoList = new ArrayList<CategoryVo>();

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"sort");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC,"id");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(order1);
        orderList.add(order2);
        Sort sort = new Sort(orderList);
        Pageable pageable = new PageRequest(0,100,sort);
        List<Category> categoryList = categoryRepository.findByStatus(pageable,0);
        if(CollectionUtils.isEmpty(categoryList))
            return categoryVoList;

        for ( Category item:categoryList ) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(item.getId()+"");
            categoryVo.setName(item.getName());
            categoryVo.setDescription(item.getDescription());
            categoryVo.setImage(item.getImage());
            categoryVoList.add(categoryVo);
        }

        return categoryVoList;
    }
}

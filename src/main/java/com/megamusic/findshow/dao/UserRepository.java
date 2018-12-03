package com.megamusic.findshow.dao;

import com.megamusic.findshow.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chengchao on 2018/9/26.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByOpenid(String openid);
    User findById(Long id);
}

package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by chengchao on 2018/9/26.
 */
@Data
@Entity
@Table(name = "FSUser")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String mobile;//手机号
    @Column
    private String password;//密码
    @Column
    private String openid;//第三方登录唯一标识
    @Column
    private String avatar; //头像
    @Column
    private String nickName;//昵称
    @Column
    private Long created;
    @Column
    private Long updated;

}

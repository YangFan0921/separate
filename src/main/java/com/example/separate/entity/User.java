package com.example.separate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@ToString
public class User{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    //转json时忽略
    @JsonIgnore
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("address")
    private String address;

    @TableField("createtime")
    private Date createtime;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("role")
    private String role;

    @TableField(exist = false)
    private List<Course> courses;

    @TableField(exist = false)
    private List<Course> stuCourses;







}

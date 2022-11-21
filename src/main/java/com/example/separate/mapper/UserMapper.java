package com.example.separate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    void updateUser(User user);


    @Select("select * from sys_user limit #{pageNum}, #{pageSize}")
    List<User> selectUserPage(Integer pageNum, Integer pageSize);


    IPage<User> findPage(Page<User> page, String username, String email, String address);
}

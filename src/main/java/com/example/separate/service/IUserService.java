package com.example.separate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.separate.common.Result;
import com.example.separate.dto.UserDto;
import com.example.separate.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface IUserService extends IService<User> {

    List<User> findAll();

    Integer saveUser(User user);

    Integer deleteUserById(Integer id);

    void updateUser(User user);

//    IPage<User> selectUserPage(Integer pageNum, Integer pageSize,String username,String email,String address);

    Integer selectTotal();

    Integer deleteBatch(List<Integer> ids);

    void exportUser(HttpServletResponse response) throws IOException;

    Boolean importUser(MultipartFile file) throws IOException;

    UserDto login(UserDto userDto);

    User register(UserDto userDto);

    User me(Integer id);

    List<User> findUsersByRole(String role);

    IPage<User> selectUserPage(Page<User> page, String username, String email, String address);
}

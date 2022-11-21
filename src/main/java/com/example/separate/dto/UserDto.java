package com.example.separate.dto;

import com.example.separate.entity.Menu;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * 接收前端请求的数据
 */

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menuList;
}

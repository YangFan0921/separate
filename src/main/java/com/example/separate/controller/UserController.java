package com.example.separate.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.common.Constants;
import com.example.separate.common.Result;
import com.example.separate.dto.UserDto;
import com.example.separate.entity.User;
import com.example.separate.service.IUserService;
import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDto dto = userService.login(userDto);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        User register = userService.register(userDto);
        return Result.success(register);
    }

    @GetMapping
    public List<User> userList(){
        return userService.findAll();
    }

//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,){
//        pageNum = (pageNum-1)*pageSize;
//        Integer total = userService.selectTotal();
//        Map<String,Object> res = new HashMap<>();
//        List<User> data = userService.selectUserPage(pageNum, pageSize);
//        res.put("total",total);
//        res.put("data",data);
//        return res;
//    }
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address){
        return userService.selectUserPage(new Page<>(pageNum,pageSize),username,email,address);
    }

    @GetMapping("/me/{id}")
    public User me(@PathVariable Integer id){
        return userService.me(id);
    }


    @PostMapping("/save")
    public Integer saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteUserById(@PathVariable Integer id){
        return userService.deleteUserById(id);
    }

    @PostMapping("/delete/batch")
    public Integer deleteBatch( @RequestBody List<Integer> ids){
        return userService.deleteBatch(ids);
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) throws IOException {
        userService.exportUser(response);
    }

    @PostMapping("/import")
    public Boolean importUser(MultipartFile file) throws IOException {
        return userService.importUser(file);
    }

    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role){
        List<User> usersByRole = userService.findUsersByRole(role);
        return Result.success(usersByRole);
    }




}

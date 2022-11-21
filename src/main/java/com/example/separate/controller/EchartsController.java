package com.example.separate.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.separate.common.Constants;
import com.example.separate.common.Result;
import com.example.separate.config.AuthAccess;
import com.example.separate.entity.File;
import com.example.separate.entity.User;
import com.example.separate.mapper.FileMapper;
import com.example.separate.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("周一","周二","周三","周四","周五","周六","周日"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members(){
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list) {
            Date createtime = user.getCreatetime();
            Quarter quarter = DateUtil.quarterEnum(createtime);
            switch (quarter){
                case Q1 : q1 += 1;break;
                case Q2 : q2 += 1;break;
                case Q3 : q3 += 1;break;
                case Q4 : q4 += 1;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }

    @AuthAccess
    @GetMapping("/file/front/all")
    public Result frontAll() {

        // 1. 从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
        List<File> fileList;
        if (StrUtil.isBlank(jsonStr)) {  // 2. 取出来的json是空的
            QueryWrapper<File> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enable",true);
            queryWrapper.eq("is_delete",false);
            fileList = fileMapper.selectList(queryWrapper);// 3. 从数据库取出数据
//            files = fileMapper.selectList(null);
            // 4. 再去缓存到redis
            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(fileList));
        } else {
            // 减轻数据库的压力
            // 5. 如果有, 从redis缓存中获取数据
            fileList = JSONUtil.toBean(jsonStr, new TypeReference<List<File>>() {
            }, true);
        }
        return Result.success(fileList);
    }

}

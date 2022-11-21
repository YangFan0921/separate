package com.example.separate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.separate.common.Constants;
import com.example.separate.entity.Dict;
import com.example.separate.service.IDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    @GetMapping("/icons")
    public List<Dict> getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return dictService.list(queryWrapper);
    }

}

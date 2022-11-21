package com.example.separate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.common.Result;
import com.example.separate.entity.Menu;
import com.example.separate.service.IMenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequestMapping("/menu")
public class MenuController {
    
    @Resource
    private IMenuService menuService;

    @GetMapping("")
    public List<Menu> menuList(@RequestParam(defaultValue = "") String name){
        return menuService.findAll(name);
    }

    @PostMapping("/save")
    public Integer saveUser(@RequestBody Menu Menu){
        return menuService.saveMenu(Menu);
    }

    @GetMapping("/page")
    public IPage<Menu> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        return menuService.selectMenuPage(pageNum,pageSize,name);
    }

    @GetMapping("/me/{id}")
    public Menu me(@PathVariable Integer id){
        return menuService.me(id);
    }

    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @GetMapping("/delete/{id}")
    public Integer deleteMenuById(@PathVariable Integer id){
        return menuService.deleteMenuById(id);
    }

    @PostMapping("/delete/batch")
    public Integer deleteBatch( @RequestBody List<Integer> ids){
        return menuService.deleteBatch(ids);
    }

    @PostMapping("/update")
    public void updateMenu(@RequestBody Menu Menu){
        menuService.updateMenu(Menu);
    }

    @GetMapping("/export")
    public void exportMenu(HttpServletResponse response) throws IOException {
        menuService.exportMenu(response);
    }

    @PostMapping("/import")
    public Boolean importMenu(MultipartFile file) throws IOException {
        return menuService.importMenu(file);
    }

}

package com.example.separate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.common.Result;
import com.example.separate.entity.Menu;
import com.example.separate.entity.Role;
import com.example.separate.entity.User;
import com.example.separate.service.IRoleService;
import io.swagger.models.auth.In;
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
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping
    public List<Role> roleList(){
        return roleService.findAll();
    }

    @PostMapping("/save")
    public Integer saveUser(@RequestBody Role role){
        return roleService.saveRole(role);
    }

    @GetMapping("/page")
    public IPage<Role> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        return roleService.selectRolePage(pageNum,pageSize,name);
    }

    @GetMapping("/me/{id}")
    public Role me(@PathVariable Integer id){
        return roleService.me(id);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteRoleById(@PathVariable Integer id){
        return roleService.deleteRoleById(id);
    }

    @PostMapping("/delete/batch")
    public Integer deleteBatch( @RequestBody List<Integer> ids){
        return roleService.deleteBatch(ids);
    }

    @PostMapping("/update")
    public void updateRole(@RequestBody Role role){
        roleService.updateRole(role);
    }

    @GetMapping("/export")
    public void exportRole(HttpServletResponse response) throws IOException {
        roleService.exportRole(response);
    }

    @PostMapping("/import")
    public Boolean importRole(MultipartFile file) throws IOException {
        return roleService.importRole(file);
    }

    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        return Result.success(roleService.setRoleMenu(roleId,menuIds));

    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getRoleMenu(roleId));

    }


}

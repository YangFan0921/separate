package com.example.separate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.common.Result;
import com.example.separate.entity.Menu;
import com.example.separate.entity.Role;
import com.example.separate.entity.RoleMenu;
import com.example.separate.entity.User;
import com.example.separate.mapper.MenuMapper;
import com.example.separate.mapper.RoleMapper;
import com.example.separate.mapper.RoleMenuMapper;
import com.example.separate.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.separate.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    @Override
    public Integer saveRole(Role role) {
        int num;
        if (role.getId() == null){
            num = roleMapper.insert(role);
        }else {
            num = roleMapper.updateById(role);
        }
        return num;
    }

    @Override
    public IPage<Role> selectRolePage(Integer pageNum, Integer pageSize, String name) {
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if ( !"".equals(name) ){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        IPage<Role> rolePage = page(page, queryWrapper);
        return rolePage;
    }

    @Override
    public Role me(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Integer deleteRoleById(Integer id) {
        int num = roleMapper.deleteById(id);
        return num;
    }

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        int num = roleMapper.deleteBatchIds(ids);
        return num;
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateById(role);
    }

    @Override
    public void exportRole(HttpServletResponse response) throws IOException {
        List<Role> list = roleMapper.selectList(null);
        //在内存操作，再写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("description","描述");
        //一次性将list对象写出到Excel
        writer.write(list,true);
        //设置浏览器的响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("角色信息","utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @Override
    public Boolean importRole(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        //通过JavaBean的方式读取Excel内的对象，但要求表头必须是中文，跟JavaBean的属性对应
//        List<User> list = excelReader.readAll(User.class);
        //忽略表头的中文，直接读取表的内容，但代码内容是写死的
        List<List<Object>> list = excelReader.read(1);
        List<Role> roles = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Role role = new Role();
            role.setName(row.get(0).toString());
            role.setDescription(row.get(1).toString());
            roles.add(role);
        }
        System.out.println(roles);
        saveBatch(roles);
        return true;
    }

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    @Transactional
    public Result setRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 删除当前角色id所有的绑定菜单关系
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        List<Integer> menuIdCopy = CollUtil.newArrayList(menuIds);
        // 把前端传递来的菜单id绑定到当前角色id上
        for (Integer menuId : menuIds) {
            Menu menu = menuMapper.selectById(menuId);
            // 二级菜单 且 传过来的menuId数组里没有它的父级id
            if (menu.getPid() != null && !menuIdCopy.contains(menu.getPid())){
                // 补上父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
        return Result.success();

    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        List<Integer> list = roleMenuMapper.selectMenuIdByRoleId(roleId);
        return list;
    }


}

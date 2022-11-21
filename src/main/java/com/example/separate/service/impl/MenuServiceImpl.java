package com.example.separate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.entity.Menu;
import com.example.separate.entity.Menu;
import com.example.separate.mapper.MenuMapper;
import com.example.separate.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if ( StrUtil.isNotBlank(name) ){
            queryWrapper.like("name",name);
        }
//        queryWrapper.orderByDesc("id");
        List<Menu> list = menuMapper.selectList(null);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu: parentNodes){
            // 筛选所有数据中pid=父级id的数据 就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }

    @Override
    public Integer saveMenu(Menu menu) {
        int num;
        if (menu.getId() == null){
            num = menuMapper.insert(menu);
        }else {
            num = menuMapper.updateById(menu);
        }
        return num;
    }

    @Override
    public IPage<Menu> selectMenuPage(Integer pageNum, Integer pageSize, String name) {
        IPage<Menu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if ( !"".equals(name) ){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        IPage<Menu> menuPage = page(page, queryWrapper);
        return menuPage;
    }

    @Override
    public Menu me(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public Integer deleteMenuById(Integer id) {
        int num = menuMapper.deleteById(id);
        return num;
    }

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        int num = menuMapper.deleteBatchIds(ids);
        return num;
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public void exportMenu(HttpServletResponse response) throws IOException {
        List<Menu> list = menuMapper.selectList(null);
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
    public Boolean importMenu(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        //通过JavaBean的方式读取Excel内的对象，但要求表头必须是中文，跟JavaBean的属性对应
//        List<User> list = excelReader.readAll(User.class);
        //忽略表头的中文，直接读取表的内容，但代码内容是写死的
        List<List<Object>> list = excelReader.read(1);
        List<Menu> menus = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Menu menu = new Menu();
            menu.setName(row.get(0).toString());
            menu.setDescription(row.get(1).toString());
            menus.add(menu);
        }
        System.out.println(menus);
        saveBatch(menus);
        return true;
    }
}

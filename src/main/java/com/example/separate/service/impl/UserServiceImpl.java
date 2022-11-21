package com.example.separate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.separate.common.Constants;
import com.example.separate.common.Result;
import com.example.separate.dto.UserDto;
import com.example.separate.entity.Course;
import com.example.separate.entity.Menu;
import com.example.separate.entity.User;
import com.example.separate.exception.ServiceException;
import com.example.separate.mapper.MenuMapper;
import com.example.separate.mapper.RoleMapper;
import com.example.separate.mapper.RoleMenuMapper;
import com.example.separate.mapper.UserMapper;
import com.example.separate.service.IMenuService;
import com.example.separate.service.IUserService;
import com.example.separate.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @Override
    public Integer saveUser(User user) {
        int num;
        if (user.getId() == null){
            num = userMapper.insert(user);
        }else {
            num = userMapper.updateById(user);
        }
        return num;
    }

    @Override
    public Integer deleteUserById(Integer id) {
        int num = userMapper.deleteById(id);
        return num;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page, String username, String email, String address) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if ( !"".equals(username) ){
//            queryWrapper.like("username",username);
//        }
//        if ( !"".equals(email) ){
//            queryWrapper.like("email",email);
//        }
//        if ( !"".equals(address) ){
//            queryWrapper.like("address",address);
//        }
//
//        //获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("获取当前用户信息============================="+currentUser);
//
//        queryWrapper.orderByDesc("id");
//        IPage<User> page = new Page<>(pageNum,pageSize);
//        IPage<User> userPage = page(page, queryWrapper);

         return userMapper.findPage(page,username,email,address);

    }

    @Override
    public Integer selectTotal() {
        Integer count = Math.toIntExact(userMapper.selectCount(null));
        return count;
    }

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        int num = userMapper.deleteBatchIds(ids);
        return num;
    }

    @Override
    public void exportUser(HttpServletResponse response) throws IOException {
        List<User> list = userMapper.selectList(null);
        //在内存操作，再写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","手机号");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createtime","创建时间");
        writer.addHeaderAlias("avatarUrl","头像");
        //一次性将list对象写出到Excel
        writer.write(list,true);
        //设置浏览器的响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @Override
    public Boolean importUser(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        //通过JavaBean的方式读取Excel内的对象，但要求表头必须是中文，跟JavaBean的属性对应
//        List<User> list = excelReader.readAll(User.class);
        //忽略表头的中文，直接读取表的内容，但代码内容是写死的
        List<List<Object>> list = excelReader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }
        System.out.println(users);
        saveBatch(users);
        return true;
    }


    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;
    @Override
    public UserDto login(UserDto userDto) {
        User user = getUserInfo(userDto);
        if ( user!=null){
            BeanUtil.copyProperties(user,userDto,true); //copy属性 忽略大小写
            //设置Token
            String token = TokenUtils.getToken(user.getId().toString(), user.getPassword().toString());
            userDto.setToken(token);
            String role = user.getRole(); // "ROLE_ADMIN"
            // 查询出当前用户角色的所有roleId
            Integer roleId = roleMapper.selectRoleIdByFlag(role);
            // 查询出当前用户角色的所有menuId
            List<Integer> menuIds = roleMenuMapper.selectMenuIdByRoleId(roleId);
            // 查出系统的所有菜单
            List<Menu> menuList = menuService.findAll("");
            List<Menu> roleMenus = new ArrayList<>();
            // 筛选当前用户角色的菜单
            for (Menu menu : menuList) {
                if (menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                List<Menu> children = menu.getChildren();
                // removeIf()  移除 children 里面不在 menuIds集合中的 元素
                children.removeIf(child -> !menuIds.contains(child.getId()));
//                for (Menu child : children) {
//                    if (!menuIds.contains(child.getId())){
//                        menuIds.remove(child.getId());
//                    }
//                    System.out.println("child---------------->"+child.getName());
//                }
//                System.out.println("menu------------------>"+menu.getName());
            }
            userDto.setMenuList(roleMenus);
            return userDto;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }

    @Override
    public User register(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        User user;
        try{
            user = userMapper.selectOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (user != null){
            throw new ServiceException(Constants.CODE_400,"用户名已存在"); //用户名已存在
        }else {
            user = new User();
            BeanUtil.copyProperties(userDto,user,true);
            userMapper.insert(user);
            return user;
        }
    }

    @Override
    public User me(Integer id) {
        return userMapper.selectById(id);
    }

    public User getUserInfo(UserDto userDto){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User user;
        try{
            user = userMapper.selectOne(queryWrapper);
        }catch (Exception e){
            log.error("{}",e.getMessage());
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return user;
    }

    @Override
    public List<User> findUsersByRole(String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }
}

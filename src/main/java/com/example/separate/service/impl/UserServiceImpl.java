package com.example.separate.service.impl;

import cn.dev33.satoken.secure.SaBase64Util;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
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
//        //????????????????????????
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("????????????????????????============================="+currentUser);
//
//        queryWrapper.orderByDesc("id");
//        IPage<User> page = new Page<>(pageNum,pageSize);
//        IPage<User> userPage = page(page, queryWrapper);

        IPage<User> page1 = userMapper.findPage(page, username, email, address);
        return page1;


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
        //???????????????????????????????????????
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //??????????????????
        writer.addHeaderAlias("username","?????????");
        writer.addHeaderAlias("password","??????");
        writer.addHeaderAlias("nickname","??????");
        writer.addHeaderAlias("email","??????");
        writer.addHeaderAlias("phone","?????????");
        writer.addHeaderAlias("address","??????");
        writer.addHeaderAlias("createtime","????????????");
        writer.addHeaderAlias("avatarUrl","??????");
        //????????????list???????????????Excel
        writer.write(list,true);
        //??????????????????????????????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("????????????","utf-8");
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
        //??????JavaBean???????????????Excel???????????????????????????????????????????????????JavaBean???????????????
//        List<User> list = excelReader.readAll(User.class);
        //??????????????????????????????????????????????????????????????????????????????
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
            BeanUtil.copyProperties(user,userDto,true); //copy?????? ???????????????
            //??????Token
            String token = TokenUtils.getToken(user.getId().toString(), user.getPassword().toString());
            userDto.setToken(token);
            String role = user.getRole(); // "ROLE_ADMIN"
            // ????????????????????????????????????roleId
            Integer roleId = roleMapper.selectRoleIdByFlag(role);
            // ????????????????????????????????????menuId
            List<Integer> menuIds = roleMenuMapper.selectMenuIdByRoleId(roleId);
            // ???????????????????????????
            List<Menu> menuList = menuService.findAll("");
            List<Menu> roleMenus = new ArrayList<>();
            // ?????????????????????????????????
            for (Menu menu : menuList) {
                if (menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                List<Menu> children = menu.getChildren();
                // removeIf()  ?????? children ???????????? menuIds???????????? ??????
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
            throw new ServiceException(Constants.CODE_600,"????????????????????????");
        }

    }
    // ????????????
    String key = "password";
    @Override
    public User register(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        User user;
        try{
            user = userMapper.selectOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"????????????");
        }
        if (user != null){
            throw new ServiceException(Constants.CODE_400,"??????????????????"); //??????????????????
        }else {

            // ????????????
            String text = userDto.getPassword();
            // ??????
            String ciphertext = SaSecureUtil.aesEncrypt(key,text);
            System.out.println("AES????????????" + ciphertext);
            userDto.setPassword(ciphertext);

            /*String text = userDto.getPassword();
            // ??????Base64??????
            String base64Text = SaBase64Util.encode(text);
            System.out.println("Base64????????????" + base64Text);
            userDto.setPassword(base64Text);*/

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
//        queryWrapper.eq("password",userDto.getPassword());
        User user;
        try{
            user = userMapper.selectOne(queryWrapper);
            String DBpassword = user.getPassword();
            // ??????
            String password = SaSecureUtil.aesDecrypt(key, DBpassword);
            System.out.println("AES????????????" + password);
            if (userDto.getPassword().equals(password)){
                return user;
            }
        }catch (Exception e){
            log.error("{}",e.getMessage());
            throw new ServiceException(Constants.CODE_500,"????????????");
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

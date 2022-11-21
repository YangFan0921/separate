package com.example.separate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.common.Result;
import com.example.separate.entity.Menu;
import com.example.separate.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.separate.entity.RoleMenu;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
public interface IRoleService extends IService<Role> {

    Integer saveRole(Role role);

    IPage<Role> selectRolePage(Integer pageNum, Integer pageSize, String name);

    List<Role> findAll();

    Role me(Integer id);

    Integer deleteRoleById(Integer id);

    Integer deleteBatch(List<Integer> ids);

    void updateRole(Role role);

    void exportRole(HttpServletResponse response) throws IOException;

    Boolean importRole(MultipartFile file) throws IOException;

    Result setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}

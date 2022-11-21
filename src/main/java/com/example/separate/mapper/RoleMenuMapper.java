package com.example.separate.mapper;

import com.example.separate.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectMenuIdByRoleId(@Param("roleId") Integer roleId);
}

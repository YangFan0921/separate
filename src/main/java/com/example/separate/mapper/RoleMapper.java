package com.example.separate.mapper;

import com.example.separate.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where flag=#{flag}")
    Integer selectRoleIdByFlag(@Param("flag") String flag);
}

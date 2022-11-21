package com.example.separate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-16
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    IPage<Course> findPage(Page<Course> page, @Param("name") String name);

    void deleteStudentCourse(@Param("courseId")Integer courseId, @Param("studentId")Integer studentId);

    void setStudentCourse(@Param("courseId")Integer courseId, @Param("studentId")Integer studentId);

    IPage<Course> findMyPage(@Param("studentId") Integer studentId, Page<Course> page, String name);
}

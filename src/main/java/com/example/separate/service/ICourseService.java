package com.example.separate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.separate.entity.User;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-16
 */
public interface ICourseService extends IService<Course> {

    List<Course> findAll(String name);

    Integer saveCourse(Course course);

//    IPage<Course> selectCoursePage(Integer pageNum, Integer pageSize, String name);

    Course me(Integer id);

    Integer deleteCourseById(Integer id);

    Integer deleteBatch(List<Integer> ids);

    Integer updateCourse(Course course);


    IPage<Course> selectCoursePage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);

    IPage<Course> selectMyCoursePage(Integer studentId, Page<Course> page, String name);
}

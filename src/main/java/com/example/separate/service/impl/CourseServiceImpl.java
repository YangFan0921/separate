package com.example.separate.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.entity.Course;
import com.example.separate.entity.Course;
import com.example.separate.entity.Role;
import com.example.separate.entity.User;
import com.example.separate.mapper.CourseMapper;
import com.example.separate.mapper.CourseMapper;
import com.example.separate.mapper.UserMapper;
import com.example.separate.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Course> findAll(String name) {
        List<Course> courses = courseMapper.selectList(null);
        return courses;
    }

    @Override
    public Integer saveCourse(Course course) {
        int num;
        if (course.getId() == null){
            num = courseMapper.insert(course);
        }else {
            num = courseMapper.updateById(course);
        }
        return num;
    }

    @Override
    public IPage<Course> selectCoursePage(Page<Course> page, String name) {

//        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        if ( !"".equals(name) ){
//            queryWrapper.like("name",name);
//        }
//        queryWrapper.orderByDesc("id");
//        IPage<Course> page = new Page<>(pageNum,pageSize);
//        IPage<Course> coursePage = page(page, queryWrapper);
//        List<Course> records = coursePage.getRecords();
//        for (Course record : records) {
//            User user = userMapper.selectById(record);
//            if (user != null){
//                record.setTeacher(user.getNickname());
//            }
//        }
        return courseMapper.findPage(page,name);
    }

    @Transactional
    @Override
    public void setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStudentCourse(courseId,studentId);
        courseMapper.setStudentCourse(courseId,studentId);
    }

    @Override
    public IPage<Course> selectMyCoursePage(Integer studentId, Page<Course> page, String name) {
        User user = userMapper.selectById(studentId);
        if (user.getRole().equals("ROLE_TEACHER")){
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",user.getId());
            return courseMapper.selectPage(page,queryWrapper);
        }else if (user.getRole().equals("ROLE_STUDENT")){
            return courseMapper.findMyPage(studentId,page,name);
        }
        return courseMapper.findMyPage(studentId,page,name);
    }

    @Override
    public Course me(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public Integer deleteCourseById(Integer id) {
        int num = courseMapper.deleteById(id);
        return num;
    }

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        int num = courseMapper.deleteBatchIds(ids);
        return num;
    }

    @Override
    public Integer updateCourse(Course course) {
        return courseMapper.updateById(course);
    }


}

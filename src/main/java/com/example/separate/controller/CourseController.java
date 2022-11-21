package com.example.separate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.common.Result;
import com.example.separate.entity.Course;
import com.example.separate.entity.User;
import com.example.separate.service.ICourseService;
import com.example.separate.service.ICourseService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;



    @GetMapping("")
    public List<Course> courseList(@RequestParam(defaultValue = "") String name){
        return courseService.findAll(name);
    }

    @PostMapping("/save")
    public Integer saveUser(@RequestBody Course Course){
        return courseService.saveCourse(Course);
    }

    @GetMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer courseId,@PathVariable Integer studentId){
        courseService.setStudentCourse(courseId,studentId);
        return Result.success();
    }

    @GetMapping("/page")
    public IPage<Course> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        return courseService.selectCoursePage(new Page<>(pageNum,pageSize) ,name);
    }
    @GetMapping("/page/MyCourse/{studentId}")
    public IPage<Course> findMyPage(@PathVariable Integer studentId,
                                    @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        return courseService.selectMyCoursePage(studentId,new Page<>(pageNum,pageSize) ,name);
    }

    @GetMapping("/me/{id}")
    public Course me(@PathVariable Integer id){
        return courseService.me(id);
    }

    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(courseService.list().stream().map(Course::getId));
    }

    @GetMapping("/delete/{id}")
    public Integer deleteCourseById(@PathVariable Integer id){
        return courseService.deleteCourseById(id);
    }

    @PostMapping("/delete/batch")
    public Integer deleteBatch( @RequestBody List<Integer> ids){
        return courseService.deleteBatch(ids);
    }

    @PostMapping("/update")
    public Integer updateCourse(@RequestBody Course Course){
        return courseService.updateCourse(Course);
    }

}

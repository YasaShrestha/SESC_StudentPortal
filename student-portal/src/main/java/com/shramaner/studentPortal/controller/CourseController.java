package com.shramaner.studentPortal.controller;

import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseService courseService;
    @GetMapping("/courses")
    public @ResponseBody List<Course>  coursesPage(){
        return courseService.getCourse();
    }
}

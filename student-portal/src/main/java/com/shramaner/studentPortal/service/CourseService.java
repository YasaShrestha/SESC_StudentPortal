package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.ICourseDAO;
import com.shramaner.studentPortal.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseService {
    @Autowired
    ICourseDAO iCourseDAO;
    public List<Course> getCourse(){
        List<Course> courseList= iCourseDAO.findAll();
        return courseList;

    }
    public List<Course> searchCourse(String name) {
        List<Course> courseList = iCourseDAO.findByCourseName(name);
        return courseList;
    }
}

package com.shramaner.studentPortal.controller;

import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Enrollment;

import com.shramaner.studentPortal.service.CourseService;
import com.shramaner.studentPortal.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.User;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
public class EnrollmentController {
    @Autowired
    EnrollmentService enrollmentService;
    @Autowired
    CourseService courseService;
    @GetMapping("/enrollments")
    public @ResponseBody List<Enrollment> enrollmentsPage(@RequestBody Authentication authentication){
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        return  enrollmentService.getEnrollment(studentId);
    }

    @GetMapping("/enrol/course/{courseid}")
    public @ResponseBody String  enrollmentsPage(@RequestBody Authentication authentication, @PathVariable String courseid){
        Enrollment enrollment= new Enrollment();
        enrollment.setCourseId(Long.valueOf(courseid));
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        enrollment.setStudentId(studentId);

        Course course = courseService.getCourseById(courseid);

        enrollmentService.saveEnrollment(enrollment, course.getCourseFee());
        return "Success";
    }

}

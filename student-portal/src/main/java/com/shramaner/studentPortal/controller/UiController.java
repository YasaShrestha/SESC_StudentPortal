package com.shramaner.studentPortal.controller;

import com.shramaner.studentPortal.model.Enrollment;
import com.shramaner.studentPortal.model.Student;
import com.shramaner.studentPortal.service.CourseService;
import com.shramaner.studentPortal.service.EnrollmentService;
import com.shramaner.studentPortal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UiController {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping({"/", "/login"})
    public String login(Model model){
        model.addAttribute("student", new Student());
        return "login"; //return page
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute Student student){
        String email = student.getEmail();
        String password = student.getPassword();

        Student loginStudent = studentService.getEmail(email);
        if(loginStudent != null){
            if(email.equals(loginStudent.getEmail()) && password.equals(loginStudent.getPassword())){
                return "redirect:/courses"; // return controller api
            }
            System.out.println("=> No record exist.");
        }

        return "redirect:/login?error=true";
    }

    @GetMapping({ "/profile"})
    public String profile(Model model){
        model.addAttribute("student", studentService.getStudentByStudentId(100l));
        return "profile"; //return page
    }

    @PostMapping({ "/profile"})
    public String profile(@ModelAttribute Student student){
        student.setStudentId(100l);
        studentService.updateStudent(student);
        return "redirect:/profile"; //return page
    }

    @GetMapping("/graduation")
    public ModelAndView  graduationPage(){
        ModelAndView mav = new ModelAndView("graduation");
        mav.addObject("graduation", studentService.getGraduation(100l));
        return mav;
    }

    @GetMapping("/enrollments")
    public ModelAndView  enrollmentsPage(){
        ModelAndView mav = new ModelAndView("enrollments");
        mav.addObject("enrollment", enrollmentService.getEnrollment(100l));
        return mav;
    }

    @GetMapping("/enrol/course/{courseid}")
    public String  enrollmentsPage(@PathVariable String courseid){
        Enrollment enrollment= new Enrollment();
        enrollment.setCourseId(Long.valueOf(courseid));
        enrollment.setStudentId(100l);
        enrollmentService.saveEnrollment(enrollment);
        return "redirect:/enrollments";
    }


    @GetMapping("/courses")
    public ModelAndView  coursesPage(){
        ModelAndView mav = new ModelAndView("courses");
        mav.addObject("courses", courseService.getCourse());
        return mav;
    }

    @GetMapping({ "/register"})
    public String register(Model model){
        model.addAttribute("student", new Student());
        return "register"; //return page
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute Student student){
         studentService.createStudent(student);

        return "redirect:/login";
    }



}

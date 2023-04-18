package com.shramaner.studentPortal.controller;

import com.shramaner.studentPortal.model.Student;
import com.shramaner.studentPortal.service.CourseService;
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
                return "redirect:/enrollments"; // return controller api
            }
            System.out.println("=> No record exist.");
        }

        return "redirect:/login?error=true";
    }



//    @GetMapping("/enrollments")
//    public String enrollmentsPage(){
//        return "enrollments";
//    }

    @GetMapping("/enrollments")
    public ModelAndView  enrollmentsPage(){
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

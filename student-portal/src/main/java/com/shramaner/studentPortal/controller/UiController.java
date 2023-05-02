package com.shramaner.studentPortal.controller;

import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Enrollment;
import com.shramaner.studentPortal.model.Student;
import com.shramaner.studentPortal.service.CourseService;
import com.shramaner.studentPortal.service.EnrollmentService;
import com.shramaner.studentPortal.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

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



    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute Student student, HttpServletRequest request){
        String email = student.getEmail();
        String password = student.getPassword();

        Student loginStudent = studentService.getEmail(email);
        if(loginStudent != null){
            if(email.equals(loginStudent.getEmail()) && password.equals(loginStudent.getPassword())){
                try {
                    UsernamePasswordAuthenticationToken authReq =
                            new UsernamePasswordAuthenticationToken(email, password);
                    Authentication auth = authManager.authenticate(authReq);
                    SecurityContext sc = SecurityContextHolder.getContext();
                    sc.setAuthentication(auth);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
                } catch (Exception e) {
                    SecurityContextHolder.getContext().setAuthentication(null);
                }

                return "redirect:/courses"; // return controller api
            }
            System.out.println("=> No record exist.");
        }

        return "redirect:/login?error=true";
    }

    @GetMapping({ "/profile"})
    public String profile(Authentication authentication, Model model){
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        model.addAttribute("student", studentService.getStudentByStudentId(studentId));
        return "profile"; //return page
    }

    @PostMapping({ "/profile"})
    public String profile(Authentication authentication, @ModelAttribute Student student){
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        student.setStudentId(studentId);
        studentService.updateStudent(student);
        return "redirect:/profile"; //return page
    }

    @GetMapping("/graduation")
    public ModelAndView  graduationPage(Authentication authentication){
        ModelAndView mav = new ModelAndView("graduation");
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        mav.addObject("graduation", studentService.getGraduation(studentId));
        return mav;
    }

    @GetMapping("/enrollments")
    public ModelAndView  enrollmentsPage(Authentication authentication){
        ModelAndView mav = new ModelAndView("enrollments");
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        mav.addObject("enrollment", enrollmentService.getEnrollment(studentId));
        return mav;
    }

    @GetMapping("/enrol/course/{courseid}")
    public String  enrollmentsPage(Authentication authentication, @PathVariable String courseid){
        Enrollment enrollment= new Enrollment();
        enrollment.setCourseId(Long.valueOf(courseid));
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        enrollment.setStudentId(studentId);

        Course course = courseService.getCourseById(courseid);

        enrollmentService.saveEnrollment(enrollment, course.getCourseFee());
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

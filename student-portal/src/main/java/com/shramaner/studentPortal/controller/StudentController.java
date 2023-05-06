package com.shramaner.studentPortal.controller;
import com.shramaner.studentPortal.model.Student;
import com.shramaner.studentPortal.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController//Rest API Controller for student
@RequestMapping("/api")
public class StudentController {
    @Autowired
    StudentService studentService;



    @GetMapping({ "/profile"})
    public@ResponseBody Student profile(@RequestBody Authentication authentication){
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        return studentService.getStudentByStudentId(studentId);
    }

    @PostMapping({ "/profile"})
    public @ResponseBody String profile(Authentication authentication, @RequestBody Student student){
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        student.setStudentId(studentId);
        studentService.updateStudent(student);
        return "success"; //return page
    }
    @GetMapping("/graduation")
    public @ResponseBody Boolean graduationPage(@RequestBody Authentication authentication){
        ModelAndView mav = new ModelAndView("graduation");
        User userDetails = (User) authentication.getPrincipal();
        long studentId = Long.valueOf(userDetails.getUsername());
        mav.addObject("graduation");
        return studentService.getGraduation(studentId);
    }

    @PostMapping("/register")
    public @ResponseBody String registerProcess(@RequestBody Student student){
        studentService.createStudent(student);

        return "register success";
    }


    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;
    @PostMapping(value = {"/login", "/"})
    public @ResponseBody String loginProcess(@RequestBody Student student, HttpServletRequest request){
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
                    return "error";
                }

                return " Login Success"; // return controller api
            }
            System.out.println("=> No record exist.");
        }

        return "error";
    }


}

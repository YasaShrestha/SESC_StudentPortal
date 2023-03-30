package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.IStudentDAO;
import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class StudentService {
@Autowired
    IStudentDAO iStudentDAO;
    public Student getUserName(String name) {
        Student student = iStudentDAO.findByUserName(name);
        return student;
    }
    public Student getEmail(String email) {
        Student studentList = iStudentDAO.findByEmail(email);
        return studentList;
    }
    public void createStudent (Student student){
        Random rd = new Random();
        student.setStudentId(rd.nextLong());
        iStudentDAO.save(student);
    }
    public void updateStudent (Student student){
        iStudentDAO.save(student);
    }

}

package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.IStudentDAO;
import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
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
        Student studentDb = iStudentDAO.findByStudentId(student.getStudentId());
        studentDb.setFullName(student.getFullName());
        studentDb.setPassword(student.getPassword());
        studentDb.setAge(student.getAge());
        studentDb.setUserName(student.getUserName());
        iStudentDAO.save(studentDb);
    }

    public Student getStudentByStudentId(long studentId) {
        Student student = iStudentDAO.findByStudentId(studentId);
        return student;
    }

    public Boolean getGraduation(long studentId) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8081/accounts/student/"+studentId;
        ResponseEntity<Map> response = restTemplate.getForEntity(fooResourceUrl, Map.class);
        Map map = response.getBody();
        boolean hasOutstandingBalance = (boolean) map.get("hasOutstandingBalance");
        return !hasOutstandingBalance;
    }
}

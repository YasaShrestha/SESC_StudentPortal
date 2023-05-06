package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.IStudentDAO;
import com.shramaner.studentPortal.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class StudentService {
@Autowired
    IStudentDAO iStudentDAO;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Student getUserName(String name) {
        Student student = iStudentDAO.findByUsername(name);
        return student;
    }
    public Student getEmail(String email) {
        Student studentList = iStudentDAO.findByEmail(email);
        return studentList;
    }
    public void createStudent (Student student){
        Random rd = new Random();
        long num = rd.nextInt(5000000) + 1000000;
        student.setStudentId(num);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        iStudentDAO.save(student);

        RestTemplate restTemplate = new RestTemplate();

        //create finance account for the student
        String createFinanceAccount = "http://localhost:8081/accounts";
        Map createFinanceAccountMap= new HashMap();
        createFinanceAccountMap.put("studentId", student.getStudentId());
        ResponseEntity<String> createFinanceAccountResponse = restTemplate.postForEntity(createFinanceAccount,createFinanceAccountMap, String.class);

        //create library account for the student
        String createLibraryAccount = "http://localhost/api/register";
        Map createLibraryAccountMap= new HashMap();
        createLibraryAccountMap.put("studentId", student.getStudentId());
        ResponseEntity<String> createLibraryAccountResponse = restTemplate.postForEntity(createLibraryAccount,createLibraryAccountMap, String.class);
    }
    public void updateStudent (Student student){
        Student studentDb = iStudentDAO.findByStudentId(student.getStudentId());
        studentDb.setFullName(student.getFullName());
        studentDb.setPassword(passwordEncoder.encode(student.getPassword()));
        studentDb.setAge(student.getAge());
        studentDb.setUsername(student.getUsername());
        studentDb.setPhone(student.getPhone());
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

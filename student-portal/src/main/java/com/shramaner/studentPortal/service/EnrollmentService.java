package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.ICourseDAO;
import com.shramaner.studentPortal.doa.IEnrollmentDAO;
import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnrollmentService {
    @Autowired
    IEnrollmentDAO iEnrollmentDAO;

    public List<Enrollment> getEnrollment(Long studentId) {
        List<Enrollment> enrollmentList = iEnrollmentDAO.findByStudentId(studentId);
        return enrollmentList;
    }
    public void saveEnrollment(Enrollment enrollment, float cfee) {
        iEnrollmentDAO.save(enrollment);

        RestTemplate restTemplate = new RestTemplate();

       //Creating invoice in the finance 
        String createInvoice = "http://localhost:8081/invoices";
        Map createInvoiceMap= new HashMap();
        createInvoiceMap.put("amount", cfee+"");
        createInvoiceMap.put("type", "TUITION_FEES");
        createInvoiceMap.put("dueDate", "2024-01-24");

        Map createInvoiceStudentMap= new HashMap();
        createInvoiceStudentMap.put("studentId", enrollment.getStudentId());

        createInvoiceMap.put("account", createInvoiceStudentMap);

       restTemplate.postForEntity(createInvoice,createInvoiceMap, String.class);

    }


}

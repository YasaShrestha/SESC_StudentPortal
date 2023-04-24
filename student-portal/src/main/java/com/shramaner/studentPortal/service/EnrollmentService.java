package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.ICourseDAO;
import com.shramaner.studentPortal.doa.IEnrollmentDAO;
import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentService {
    @Autowired
    IEnrollmentDAO iEnrollmentDAO;

    public List<Enrollment> getEnrollment(Long studentId) {
        List<Enrollment> enrollmentList = iEnrollmentDAO.findByStudentId(studentId);
        return enrollmentList;
    }
    public void saveEnrollment(Enrollment enrollment) {
        iEnrollmentDAO.save(enrollment);
    }

}

package com.shramaner.studentPortal.doa;

import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEnrollmentDAO extends JpaRepository<Enrollment, Long> {


    List<Enrollment> findByStudentId(Long studentId);
}

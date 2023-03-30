package com.shramaner.studentPortal.doa;

import com.shramaner.studentPortal.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnrollmentDAO extends JpaRepository<Enrollment, Long> {
}

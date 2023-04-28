package com.shramaner.studentPortal.doa;

import com.shramaner.studentPortal.model.Course;
import com.shramaner.studentPortal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudentDAO extends JpaRepository<Student, Long> {

    public Student findByUsername(String name);
    public Student findByEmail(String email);

    Student findByStudentId(long studentId);
}

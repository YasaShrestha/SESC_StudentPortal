package com.shramaner.studentPortal.doa;

import com.shramaner.studentPortal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseDAO extends JpaRepository<Course, Long> {

    public List<Course> findByCourseName(String name);



}

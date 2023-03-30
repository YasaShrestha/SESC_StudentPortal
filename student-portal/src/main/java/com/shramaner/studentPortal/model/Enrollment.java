package com.shramaner.studentPortal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long enrollmentId;
    @Column(nullable = false)
    private Long studentId;
    @Column(nullable = false)
    private  String courseId;

}

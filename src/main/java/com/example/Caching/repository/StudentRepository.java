package com.example.Caching.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Caching.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}

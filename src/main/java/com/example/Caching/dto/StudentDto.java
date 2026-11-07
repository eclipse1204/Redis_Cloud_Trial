package com.example.Caching.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Serializable because the service caches this object in Redis
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String course;
}

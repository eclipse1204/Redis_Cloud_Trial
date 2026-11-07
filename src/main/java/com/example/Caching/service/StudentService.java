package com.example.Caching.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.Caching.dto.StudentDto;
import com.example.Caching.entity.Student;
import com.example.Caching.exception.StudentNotFoundException;
import com.example.Caching.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private static final String CACHE = "students";

    private final StudentRepository repository;
    private final ModelMapper modelMapper;

    public StudentDto create(StudentDto studentDto) {
        Student saved = repository.save(toEntity(studentDto));
        return toDto(saved);
    }

    @Cacheable(cacheNames = CACHE, key = "#id")
    public StudentDto getById(Long id) {
        // Logged so you can see when the DB is hit vs. the cache
        System.out.println("Cache miss - loading student " + id + " from DB");
        Student student = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        return toDto(student);
    }

    public List<StudentDto> getAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @CachePut(cacheNames = CACHE, key = "#id")
    public StudentDto update(Long id, StudentDto studentDto) {
        Student existing = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        existing.setFirstName(studentDto.getFirstName());
        existing.setLastName(studentDto.getLastName());
        existing.setEmail(studentDto.getEmail());
        existing.setAge(studentDto.getAge());
        existing.setCourse(studentDto.getCourse());
        return toDto(repository.save(existing));
    }

    @CacheEvict(cacheNames = CACHE, key = "#id")
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private StudentDto toDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private Student toEntity(StudentDto dto) {
        return modelMapper.map(dto, Student.class);
    }
}

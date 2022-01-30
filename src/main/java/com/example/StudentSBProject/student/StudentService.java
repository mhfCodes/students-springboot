package com.example.StudentSBProject.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	public void addStudent(Student student) {
		
		Optional<Student> myStudent = studentRepository.findStudentByEmail(student.getEmail());
		
		if (myStudent.isPresent()) {
			throw new IllegalStateException("Email Taken");
		}
		
		studentRepository.save(student);
	}
	
	
}

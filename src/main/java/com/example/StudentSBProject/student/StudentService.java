package com.example.StudentSBProject.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		// Check email is same as someone else
		if (myStudent.isPresent()) {
			throw new IllegalStateException("Email Taken");
		}
		
		studentRepository.save(student);
	}
	
	@Transactional
	public void updateStudent(Long studentId, String email, String password) {
		
		// Check student exists?
		Student student = studentRepository.findById(studentId).orElseThrow(() -> {
			throw new IllegalStateException("Student Does Not Exist");
		});
		
		// Check email exists? email is not previous email? 
		if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
			
			// Check email is same as someone else
			Optional<Student> myStudent = studentRepository.findStudentByEmail(email);
			if (myStudent.isPresent()) {
				throw new IllegalStateException("Email Taken");
			}
			
			student.setEmail(email);
		} 
		
		// Check password exists? password is not previous password?
		if (password != null && password.length() > 0 && !Objects.equals(student.getPassword(), password)) {
			student.setPassword(password);
		}
		
	}
	
	public void deleteStudent(Long studentId) {
		
		// Check student with studentId exists?
		boolean student = studentRepository.existsById(studentId);
		if (!student) {
			throw new IllegalStateException("Student With Id" + studentId + " Does Not Exist");
		}
		
		studentRepository.deleteById(studentId);
	}
	
	
	
	
}

package com.lms.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.lms.data.StudentRepository;
import com.lms.model.Student;

@Stateless
public class StudentService {
	@Inject
	private StudentRepository studentRepository;

	public Student addStudentDetails(Student student) {
		return studentRepository.persistStudentDetails(student);
	}

	public Student updateStudentDetails(Student student) {
		return studentRepository.updateStudentDetails(student);
	}

	public List<Student> getAllStudent() {
		return studentRepository.findAllStudent();

	}

	public StudentRepository getStudentRepository() {
		return studentRepository;
	}

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

}

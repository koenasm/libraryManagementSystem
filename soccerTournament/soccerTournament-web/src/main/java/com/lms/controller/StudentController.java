package com.lms.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.lms.model.Student;
import com.lms.service.StudentService;


@ManagedBean
@SessionScoped
public class StudentController extends AbstractController {

	protected static final String STAFF_LIST_PAGE = "/lms/student/student_list.xhtml?faces-redirect=true";
	protected static final String EDIT_STAFF_PAGE = "/lms/student/edit_student.xhtml?faces-redirect=true";
	protected static final String CREATE_STAFF = "/lms/student/create_student.xhtml?faces-redirect=true";
	protected static final String ACCESS_DENIED = "Access denied";

	private static final String STAFF_ID = "student with the id ";

	private static final String HAS_BEEN_CREATED = "has been captured before";

	protected static final String EXCEPTION_OCCURRED = "Exception occurred: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient StudentService studentService;

	@Inject
	private Student student;

	private List<Student> studentList;

	private static Logger log = Logger.getLogger(StudentController.class.getName());

	@PostConstruct
	public void initStudentDetails() {
		student = new Student();
	}

	public String validateStudentDetails() {

		List<Student> studentList = studentService.getAllStudent();
		if (studentList != null) {
			for (Student stf : studentList) {
				if (stf.getIdentifier().equalsIgnoreCase(student.getIdentifier())) {
					facesContext.getExternalContext().getFlash().setKeepMessages(true);
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Student with id Number, " + student.getIdentifier() + ", " + HAS_BEEN_CREATED, null));
					return CREATE_STAFF;
				}

			}
		}
		return addStudentDetails();

	}

	public String createStudent() {
		initStudentDetails();
		return CREATE_STAFF;
	}

	public String addStudentDetails() {

		try {

			student = studentService.addStudentDetails(student);

			initStudentDetails();
			studentList = null;
			return STAFF_LIST_PAGE;
		} catch (PersistenceException pe) {

			return handleException(pe, STAFF_ID + student + " " + HAS_BEEN_CREATED, facesContext);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return handleException(e, "Record with the following has been found", facesContext);
			} else {
				return handleException(e, STAFF_ID + student.get_id() + HAS_BEEN_CREATED, facesContext);
			}

		}

	}

	public String updateStudentDetails() {

		try {
			student = studentService.updateStudentDetails(student);
			initStudentDetails();
			studentList = null;
			return STAFF_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update student details" + e.getMessage(), e);
			return "";
		}

	}

	public String displaySelectedStudent(Student student) {
		this.student = student;
		if (this.student != null) {
			this.student.getStudentName();

		}
		return EDIT_STAFF_PAGE;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	public List<Student> getStudentList() {
		if (studentList == null) {
			studentList = studentService.getAllStudent();

		} else {
			for (Student tmpStudent : studentList) {
				System.out.println("student Name : " + tmpStudent.getStudentName());
			}
		}
		return studentList;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

}

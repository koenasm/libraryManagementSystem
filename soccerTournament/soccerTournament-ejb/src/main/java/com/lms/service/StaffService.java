package com.lms.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.lms.data.StaffRepository;
import com.lms.model.Staff;


public class StaffService {
	@Inject
	private StaffRepository staffRepository;

	public Staff addStaffDetails(Staff staff) {
		return staffRepository.persistStaffDetails(staff);
	}

	public Staff updateStaffDetails(Staff staff) {
		return staffRepository.updateStaffDetails(staff);
	}

	public List<Staff> getAllStaff() {
		return staffRepository.findAllStaff();

	}

	public StaffRepository getStaffRepository() {
		return staffRepository;
	}

	public void setStaffRepository(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}
}

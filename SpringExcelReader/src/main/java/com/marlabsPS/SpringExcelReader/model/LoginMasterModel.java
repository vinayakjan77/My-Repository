package com.marlabsPS.SpringExcelReader.model;

import javax.persistence.Column;
//import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
//@Table(name = "login_master", catalog = "skill_evaluation")
public class LoginMasterModel {

	@Id
	@Column(name = "associate_id")
	private int associate_id;

	@Column(name = "associate_name")
	private String associate_name;

	@Column(name = "associate_role")
	private String associate_role;

	@Column(name = "associate_mobile_number")
	private String associate_mobile_number;

}

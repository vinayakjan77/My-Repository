package com.marlabsPS.SpringExcelReader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tech_master_table", catalog = "ames")
public class TechnologyMasterModel {

	@Id
	@Column(name = "tech_id")
	private String tech_Id;

	@Column(name = "tech_name")
	private String tech_Name;

}

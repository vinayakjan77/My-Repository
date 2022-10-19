package com.marlabsPS.SpringExcelReader.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "associate_details_master", catalog = "ames")
public class AssociateDetailModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String batch_code;

	@Id
	private int associate_id;

	private String associate_name;

	private double assessor_mark;

	private double mentor_mark;

	private String assessor_remarks;

	private String mentor_remarks;

}

package com.marlabsPS.SpringExcelReader.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.Setter;

@Embeddable
@Data
@Setter

public class MentorPhaseMasterCPK implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String phase_id;
	private String tech_id;

}

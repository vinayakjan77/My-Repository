package com.marlabsPS.SpringExcelReader.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class MentorPhaseMasterModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MentorPhaseMasterCPK mentorphasecpk;

}

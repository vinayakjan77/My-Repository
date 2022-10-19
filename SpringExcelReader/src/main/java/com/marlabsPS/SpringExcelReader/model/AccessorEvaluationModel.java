package com.marlabsPS.SpringExcelReader.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
//@Table(name = "assessor_evaluation_result", catalog = "skill_evaluation")
public class AccessorEvaluationModel implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int associateId;
	private String phaseId;
	private String criteriaId;
	private String assessorGivenMarks;

}

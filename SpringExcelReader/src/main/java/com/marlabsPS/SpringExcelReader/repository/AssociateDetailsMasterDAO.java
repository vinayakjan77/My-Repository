package com.marlabsPS.SpringExcelReader.repository;

import java.util.List;

import com.marlabsPS.SpringExcelReader.model.AssociateDetailModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssociateDetailsMasterDAO extends JpaRepository<AssociateDetailModel, Integer> {

	@Query(value = "SELECT * FROM ames.associate_details_master u WHERE u.associate_id = ?1", nativeQuery = true)
	AssociateDetailModel findAssociateById(Integer id);

	@Query(value = "select * from associate_details_master where batch_code=?1", nativeQuery = true)
	List<AssociateDetailModel> findAssociatesByBatchCode(String batchCode);
}

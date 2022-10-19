package com.marlabsPS.SpringExcelReader.repository;

import com.marlabsPS.SpringExcelReader.model.TechnologyMasterModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyMasterDAO extends JpaRepository<TechnologyMasterModel, String> {

}

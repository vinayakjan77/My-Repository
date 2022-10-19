package com.marlabsPS.SpringExcelReader.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.marlabsPS.SpringExcelReader.model.AssociateDetailModel;
import com.marlabsPS.SpringExcelReader.service.SpringExcelReaderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



/*
GET->http://localhost:8096/savebatchpdf?batch_id=HYD15NT001
GET->	http://localhost:8080/savepdf?associate_id=333621
*/
@Controller
public class SpringExcelReaderController {

	@Autowired
	SpringExcelReaderService employeeService;

	@GetMapping("/display")
	public String checkMethod() {

		return "Excelupload";
	}

	@RequestMapping(value = "/uploadrecords", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String Upload(@RequestParam("tech_master_excel") MultipartFile tech_master_excel) {
		employeeService.Upload(tech_master_excel);
		return "Excelupload";
	}

	@RequestMapping(value = "/savepdf", method = RequestMethod.GET)
	public ResponseEntity<String> savePDF(@RequestParam("associate_id") Integer associate_id,
			HttpServletResponse response) {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);

		AssociateDetailModel associate = employeeService.findAssociateById(associate_id);
		List<AssociateDetailModel> associates = new ArrayList<AssociateDetailModel>();
		associates.add(associate);
		employeeService.savePDF(associates, response);
		return ResponseEntity.status(HttpStatus.OK).body("PDF saved successfully");
	}

	@RequestMapping(value = "/savebatchpdf", method = RequestMethod.GET)
	public ResponseEntity<String> saveBatchPDF(@RequestParam("batch_id") String batchCode,
			HttpServletResponse response) {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);

		List<AssociateDetailModel> batches = employeeService.findAssociatesByBatchCode(batchCode);
		employeeService.savePDF(batches, response);
		return ResponseEntity.status(HttpStatus.OK).body("PDF saved successfully");
	}
}

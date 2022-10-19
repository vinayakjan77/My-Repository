package com.marlabsPS.SpringExcelReader.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.marlabsPS.SpringExcelReader.model.AssociateDetailModel;
import com.marlabsPS.SpringExcelReader.model.TechnologyMasterModel;
import com.marlabsPS.SpringExcelReader.repository.AssociateDetailsMasterDAO;
import com.marlabsPS.SpringExcelReader.repository.TechnologyMasterDAO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SpringExcelReaderService {

	@Autowired
	TechnologyMasterDAO techDAO;

	@Autowired
	AssociateDetailsMasterDAO associatedetailsmasterDAO;

	public void Upload(MultipartFile tech_master_excel) {
		InputStream inputStream = null;
		Workbook workbook = null;

		ArrayList<TechnologyMasterModel> technologymastermodellist = new ArrayList<>();
		try {
			inputStream = new BufferedInputStream(tech_master_excel.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);
		// Sheet sheet1 = workbook.getSheetAt(1);

		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			data.put(i, new ArrayList<String>());
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue() + ",");
				data.get(i).add(cell.getStringCellValue());

			}
			System.out.println();
			i++;
		}
		int rowcount = data.size();
		System.out.println("Row count: " + data.size());
		for (int j = 0; j < rowcount; j++) {
			TechnologyMasterModel technologymastermodel = new TechnologyMasterModel();
			technologymastermodel.setTech_Id(data.get(j).get(0));
			technologymastermodel.setTech_Name(data.get(j).get(1));
			technologymastermodellist.add(technologymastermodel);

		}
		techDAO.saveAll(technologymastermodellist);

	}

	public AssociateDetailModel findAssociateById(Integer id) {
		return associatedetailsmasterDAO.findAssociateById(id);
	}

	public void savePDF(List<AssociateDetailModel> associateList, HttpServletResponse response) {
		// Create the Object of Document
		Document document = new Document(PageSize.A4);
		// get the document and write the response to output stream
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.open();

		// Add header of 1 columns
		PdfPTable headertable = new PdfPTable(1);
		headertable.setWidthPercentage(100f);
		// Create Table Header
		PdfPCell headercell = new PdfPCell();
		headercell.setBackgroundColor(CMYKColor.MAGENTA);
		Phrase headerphrase = new Phrase("Resource Performance Report - In MFRP");
		headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headercell.setPhrase(headerphrase);
		headertable.addCell(headercell);
		document.add(headertable);

		for (AssociateDetailModel associate : associateList) {
			createAssociate(document, associate);
		}
		document.close();

	}

	public void createAssociate(Document document, AssociateDetailModel associate) {

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 8, 8, 8, 8, 8, 8 });
		table.setSpacingBefore(5);
		// Create Table Header
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.YELLOW);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("Resource ID"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(associate.getAssociate_id())));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Training Location"));
		table.addCell(cell);
		String locationcode = associate.getBatch_code();// get first 3 characters from BATCH_CODE
		cell.setPhrase(new Phrase(locationcode.substring(0, 3)));
		table.addCell(cell);
		cell.setBackgroundColor(CMYKColor.WHITE);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resource Name"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getAssociate_name()));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Accessor Evaluation Complete"));
		table.addCell(cell);

		if (associate.getAssessor_mark() > 0) {
			cell.setPhrase(new Phrase("YES"));

		} else {
			cell.setPhrase(new Phrase("NO"));
		}
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mentor Evaluation Complete"));
		table.addCell(cell);
		if (associate.getMentor_mark() > 0) {
			cell.setPhrase(new Phrase("YES"));

		} else {
			cell.setPhrase(new Phrase("NO"));
		}
		table.addCell(cell);

		cell.setPhrase(new Phrase("Batch Code"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getBatch_code()));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Assesor Evaluation Score"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(associate.getAssessor_mark())));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mentor Evaluation Score"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(associate.getMentor_mark())));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Technology Trained"));
		table.addCell(cell);
		String techcode = associate.getBatch_code().substring(5, 7);// get first 3 characters from BATCH_CODE
		String TechName = techDAO.findById(techcode).get().getTech_Name();
		cell.setPhrase(new Phrase(TechName));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Assessor Evaluation Feedback"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getAssessor_remarks()));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mentor Evaluation Feedback"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getMentor_remarks()));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Assessor Evaluation Max Score"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(60)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mentor Evaluation Max Score"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(40)));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Assessor Evaluation Percentage"));
		table.addCell(cell);
		int assesorpct = (int) ((associate.getAssessor_mark() / 60) * 100);
		cell.setPhrase(new Phrase(String.valueOf(assesorpct)));
		table.addCell(cell);
		int mentorpct = (int) ((associate.getMentor_mark() / 60) * 100);
		cell.setPhrase(new Phrase("Mentor Evaluation Percentage"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(mentorpct)));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setBackgroundColor(CMYKColor.CYAN);
		cell.setPhrase(new Phrase("Total Percentage"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf((assesorpct + mentorpct) / 2)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mentor Evaluation Feedback"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getMentor_remarks()));
		table.addCell(cell);

		cell.setBackgroundColor(CMYKColor.WHITE);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setBackgroundColor(CMYKColor.CYAN);
		cell.setPhrase(new Phrase("Grade"));
		table.addCell(cell);
		double totalpct = (assesorpct + mentorpct) / 2;
		if (totalpct > 80 && totalpct < 100) {
			cell.setPhrase(new Phrase("A"));
		} else if (totalpct > 60 && totalpct < 80) {
			cell.setPhrase(new Phrase("B"));
		} else if (totalpct > 40 && totalpct < 60) {
			cell.setPhrase(new Phrase("C"));
		} else if (totalpct > 20 && totalpct < 40) {
			cell.setPhrase(new Phrase("D"));
		} else {
			cell.setPhrase(new Phrase(""));
		}
		table.addCell(cell);
		cell.setPhrase(new Phrase("Assesor Evaluation Feedback"));
		table.addCell(cell);
		cell.setPhrase(new Phrase(associate.getAssessor_remarks()));
		table.addCell(cell);

		// Add table to document
		document.add(table);

	}

	public List<AssociateDetailModel> findAssociatesByBatchCode(String batchCode) {

		return associatedetailsmasterDAO.findAssociatesByBatchCode(batchCode);
	}
}

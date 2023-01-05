package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.ProductUserType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class ProductUserTypeExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Content-Disposition", "attachment;filename=ProductUserType.xls");
        Sheet sheet = workbook.createSheet("ProductUserType");
        List<ProductUserType> puList = (List<ProductUserType>) model.get("puList");
        setHeading(sheet);
        setBody(sheet, puList);
    }

    private void setHeading(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("USER TYPE");
        row.createCell(2).setCellValue("USER CODE");
        row.createCell(3).setCellValue("USER FOR");
        row.createCell(4).setCellValue("USER EMAIL");
        row.createCell(5).setCellValue("USER CONTACT");
        row.createCell(6).setCellValue("USER ID TYPE");
        row.createCell(7).setCellValue("IF OTHER");
        row.createCell(8).setCellValue("USER ID NUMBER");
    }

    private void setBody(Sheet sheet, List<ProductUserType> puList) {
        int rowIdx = 1;
        for (ProductUserType pu : puList) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(pu.getId());
            row.createCell(1).setCellValue(pu.getUserType());
            row.createCell(2).setCellValue(pu.getUserCode());
            row.createCell(3).setCellValue(pu.getUserFor());
            row.createCell(4).setCellValue(pu.getUserEmail());
            row.createCell(5).setCellValue(pu.getUserContact());
            row.createCell(6).setCellValue(pu.getUserIdType());
            row.createCell(7).setCellValue(pu.getIfOther());
            row.createCell(8).setCellValue(pu.getUserIdNumber());
        }
    }
}
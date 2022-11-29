package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class MusExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Disposition", "attachment;filename=Mus.xls");
        Sheet sheet = workbook.createSheet("MUS");
        List<Mus> list = (List<Mus>) model.get("obs");
        setHeading(sheet);
        setBody(sheet, list);
    }

    private void setHeading(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("MUS TYPE");
        row.createCell(2).setCellValue("MUS MODEL");
        row.createCell(3).setCellValue("MUS DESCRIPTION");
    }

    private void setBody(Sheet sheet, List<Mus> list) {
        int rowIdx = 1;
        for (Mus mus : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(mus.getId());
            row.createCell(1).setCellValue(mus.getMusType());
            row.createCell(2).setCellValue(mus.getMusModel());
            row.createCell(3).setCellValue(mus.getMusDesc());
        }
    }
}


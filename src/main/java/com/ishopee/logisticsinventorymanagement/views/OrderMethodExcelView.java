package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderMethodExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Content-Disposition", "attachment;filename=OrderMethod.xls");
        Sheet sheet = workbook.createSheet("OrderMethod");
        List<OrderMethod> omList = (List<OrderMethod>) model.get("omList");
        setHeading(sheet);
        setBody(sheet, omList);
    }

    private void setHeading(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("MODE");
        row.createCell(2).setCellValue("CODE");
        row.createCell(3).setCellValue("TYPE");
        row.createCell(4).setCellValue("ACCEPT");
        row.createCell(5).setCellValue("NOTE");
    }

    private void setBody(Sheet sheet, List<OrderMethod> omList) {
        int rowIdx = 1;
        for (OrderMethod om : omList) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(om.getId());
            row.createCell(1).setCellValue(om.getOrderMode());
            row.createCell(2).setCellValue(om.getOrderCode());
            row.createCell(3).setCellValue(om.getOrderType());
            row.createCell(4).setCellValue(om.getOrderAcpt().toString());
            row.createCell(5).setCellValue(om.getOrderNote());
        }
    }
}


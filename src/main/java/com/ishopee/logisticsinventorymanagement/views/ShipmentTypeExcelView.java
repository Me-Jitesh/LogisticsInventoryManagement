package com.ishopee.logisticsinventorymanagement.views;


import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ShipmentTypeExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {

        Sheet sheet = workbook.createSheet("ShipmentType");
        List<ShipmentType> list = (List<ShipmentType>) model.get("obs");
        setHeading(sheet);
        setBody(sheet, list);
    }

    private void setHeading(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("MODE");
        row.createCell(2).setCellValue("CODE");
        row.createCell(3).setCellValue("ENABLED");
        row.createCell(4).setCellValue("GRAD");
        row.createCell(5).setCellValue("DESCRIPTION");
    }

    private void setBody(Sheet sheet, List<ShipmentType> list) {
        int rowIdx = 1;
        for (ShipmentType shipmentType : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(shipmentType.getId());
            row.createCell(1).setCellValue(shipmentType.getShipMode());
            row.createCell(2).setCellValue(shipmentType.getShipCode());
            row.createCell(3).setCellValue(shipmentType.getEnableShip());
            row.createCell(4).setCellValue(shipmentType.getShipGrad());
            row.createCell(5).setCellValue(shipmentType.getShipDesc());
        }
    }
}

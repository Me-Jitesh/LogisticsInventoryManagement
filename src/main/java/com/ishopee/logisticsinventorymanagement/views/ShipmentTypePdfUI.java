package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;


@Component
public class ShipmentTypePdfUI {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypePdfUI.class);

    private PdfPTable table;
    private PdfPCell cell;
    private final Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.ITALIC, Color.WHITE);

    public ByteArrayInputStream buildPdfDocument(List<ShipmentType> list) {

        LOG.info("ENTERED INTO buildPdfDocument ");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // step 1: creation of a document-object
        Document document = new Document();

        try {
            // step 2:  we create a writer that listens to the document and directs a PDF-stream to a file
            PdfWriter.getInstance(document, outputStream);

            // step 3: we open the document
            document.open();

            LOG.debug("TABLE UI CREATION");

            // step 4 : Create UI
            table = new PdfPTable(7);
            table.setWidthPercentage(100);
            setTitle("SHIPMENT TYPE DATA");
            setHeader();
            setData(list);

            // step 5 : Flush Table to the document
            document.add(table);
            LOG.debug("WRITTENED DATA TO DOCUMENT");
        } catch (DocumentException de) {
            LOG.error(de.getMessage());
        }
        // step 6:  close the resource
        document.close();
        LOG.info("RETURNING TO CONTROLLER");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void setTitle(String title) {
        //      Set  Title
        cell = new PdfPCell(new Paragraph(title, font));
        cell.setColspan(7);
        cell.setBorderColor(new Color(0, 0, 0));
        cell.setBackgroundColor(new Color(0, 225, 245));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(20);
        table.addCell(cell);
    }

    private void setHeader() {
//        Set Heading
        int padding = 9;
        Color bgColor = new Color(207, 216, 220);
        int textAlignment = Element.ALIGN_CENTER;

        cell = new PdfPCell(new Paragraph("ID"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("MODE"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("CODE"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("ENABLED"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("GRADE"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("DESCRIPTION"));
        cell.setColspan(2);
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);
    }

    private void setData(List<ShipmentType> list) {
        // Set Data
        int padding = 5;
        int textAlignment = Element.ALIGN_CENTER;

        for (ShipmentType shipmentType : list) {

            cell = new PdfPCell(new Paragraph(shipmentType.getId().toString()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(shipmentType.getShipMode()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(shipmentType.getShipCode()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(shipmentType.getEnableShip()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(shipmentType.getShipGrad()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(shipmentType.getShipDesc()));
            cell.setColspan(2);
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);
        }
    }
}

package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class MusPdfUI {

    private static final Logger LOG = LoggerFactory.getLogger(MusPdfUI.class);
    private Table table;

    public ByteArrayInputStream buildPdfDocument(List<Mus> musList) {

        LOG.info("ENTERED INTO buildPdfDocument ");

        // File where the data will be  writtened
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Telling pdf writer where to write the data
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        // Handling Pdf Pages
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        // Creating new page
        pdfDocument.addNewPage();
        // Registring pdf writer with document so that when event take place it will  write the data in File
        Document document = new Document(pdfDocument);
        // Table Creation
        try {
            LOG.debug("TABLE UI CREATION STARTED");
            table = new Table(4, true);
            setTitle("MEASUREMENT UNIT SYSTEM");
            String[] headings = {"ID", "TYPE", "MODE", "DESC"};
            setHeading(headings);
            setData(musList);

            document.add(table);
            LOG.debug("TABLE UI CREATION COMPLETED");

            LOG.debug("FLUSHING RESOURSES");
            table.complete();
            table.flush();
            document.close();
            document.flush();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("TABLE UI CREATION FAILED DUE TO {}", e.getMessage());
        }

        LOG.info("RETURNING TO THE CONTROLLER");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void setTitle(String title) {

        Cell cell = new Cell(0, 4)
                .add(new Paragraph(title))
                .setFontColor(new DeviceRgb(100, 100, 100))
                .setFontSize(20)
                .setBold()
                .setItalic()
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(new DeviceRgb(220, 250, 100))
                .setPadding(20)
                .setBorder(Border.NO_BORDER);
//                .setBorder(new RidgeBorder(new DeviceRgb(250, 200, 200), 1));
        table.addCell(cell);
    }

    private void setHeading(String[] headings) {

        TextAlignment textAlignment = TextAlignment.CENTER;
        float padding = 10f;

        for (String heading : headings) {
            Cell cell = new Cell()
                    .add(new Paragraph(heading))
                    .setTextAlignment(textAlignment)
                    .setBold()
                    .setPadding(padding)
                    .setBackgroundColor(new DeviceRgb(200, 200, 200));
            table.addCell(cell);
        }
    }

    private void setData(List<Mus> musList) {

        TextAlignment textAlignment = TextAlignment.CENTER;
        float padding = 5f;

        for (Mus mus : musList) {

            Cell cell = new Cell()
                    .add(new Paragraph(mus.getId().toString()))
                    .setTextAlignment(textAlignment)
                    .setPadding(padding);
            table.addCell(cell);

            cell = new Cell()
                    .add(new Paragraph(mus.getMusType()))
                    .setTextAlignment(textAlignment)
                    .setPadding(padding);
            table.addCell(cell);

            cell = new Cell()
                    .add(new Paragraph(mus.getMusModel()))
                    .setTextAlignment(textAlignment)
                    .setPadding(padding);
            table.addCell(cell);

            cell = new Cell()
                    .add(new Paragraph(mus.getMusDesc()))
                    .setTextAlignment(textAlignment)
                    .setPadding(padding);
            table.addCell(cell);
        }
    }
}
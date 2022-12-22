package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Component
public class ShipmentTypeTextView {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeTextView.class);
    ByteArrayOutputStream outputStream;

    public ByteArrayInputStream buildTextDocument(List<ShipmentType> list) {
        LOG.info("ENTERED INTO buildTextDocument");

        outputStream = new ByteArrayOutputStream();

        try {
            LOG.debug("UI CREATION");
            setTitle("**************************** SHIPMENT TYPE DATA ****************************");
            String[] headers = {"ID", "MODE", "CODE", "ENABLED", "GRADE", "DESCRIPTION"};
            setHeader(headers);
            setData(list);
            outputStream.flush();
            outputStream.close();
            LOG.debug("WRITTENED DATA INTO DOCUMENT");
        } catch (DocumentException | IOException de) {
            LOG.error(de.getMessage());
            de.printStackTrace();
        }
        LOG.info("RETURNING TO CONTROLLER");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void setTitle(String title) throws IOException {
        //      Set  Title
        outputStream.write(title.getBytes());
        outputStream.write("\n".getBytes());
    }

    private void setHeader(String[] headers) throws IOException {
        //      Set Heading
        for (String header : headers) {
            outputStream.write((header + "\t").getBytes());
        }
        outputStream.write("\n".getBytes());
    }

    private void setData(List<ShipmentType> list) throws IOException {
        //      Set Data
        for (ShipmentType shipmentType : list) {
            outputStream.write((shipmentType.getId() + "\t").getBytes());
            outputStream.write((shipmentType.getShipMode() + "\t").getBytes());
            outputStream.write((shipmentType.getShipCode() + "\t").getBytes());
            outputStream.write((shipmentType.getEnableShip() + "\t").getBytes());
            outputStream.write((shipmentType.getShipGrad() + "\t").getBytes());
            outputStream.write((shipmentType.getShipDesc() + "\t").getBytes());
            outputStream.write("\n".getBytes());
        }

    }
}

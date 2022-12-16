package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Component
public class ShipmentTypeCSVView {
    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeCSVView.class);


    public ByteArrayInputStream buildCSVDocument(List<ShipmentType> list) {

        LOG.info("ENTERED INTO buildCSVDocument ");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "MODE", "CODE", "ENABLED", "GRADE", "DESCRIPTION"));
            LOG.debug("DATA SETTING");
            for (ShipmentType st : list) {
                List<String> data = Arrays.asList(st.getId().toString(), st.getShipMode(), st.getShipCode(), st.getEnableShip(), st.getShipGrad(), st.getShipDesc());
                csvPrinter.printRecord(data);
            }
            LOG.debug("WRITTENED DATA INTO DOCUMENT");
            csvPrinter.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("RETURNING TO CONTROLLER");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}

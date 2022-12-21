package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class ShipmentTypeXmlView {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeXmlView.class);

    public ByteArrayInputStream buildXmlDocument(List<ShipmentType> shipmentTypeList, Class<ShipmentType> className) {
        LOG.info("ENTERED INTO buildXmlDocument METHOD");
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            LOG.debug("MARSHALLING STARTED");
            JAXBContext jaxbContext = JAXBContext.newInstance(className);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // Formatting XML
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);
            // Marshalling - Covert Object into Xml
            for (ShipmentType st : shipmentTypeList)
                marshaller.marshal(st, outputStream);
            LOG.debug("MARSHALLING FINISHED");
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (JAXBException e) {
            LOG.error("MARSHALLING FAILED DUE TO {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
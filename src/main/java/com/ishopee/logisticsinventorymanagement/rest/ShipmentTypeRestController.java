package com.ishopee.logisticsinventorymanagement.rest;

import com.ishopee.logisticsinventorymanagement.exceptions.ShipmentTypeNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/st")
@Api(description = "GET SHIPMENT TYPE API DETAILS")
public class ShipmentTypeRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeRestController.class);

    @Autowired
    private IShipmentTypeService service;

    @GetMapping("/all")
    @ApiOperation("FETCH ALL SHIPMENT TYPE RECORDS")
    public ResponseEntity<?> getAllShipmentType() {
        LOG.info("ENTERED INTO getAllShipmentType METHOD");
        ResponseEntity<?> res;
        try {
            List<ShipmentType> list = service.getAllShipmentType();
            res = new ResponseEntity<Object>(list, HttpStatus.OK);
            LOG.debug("ALL SHIPMENT TYPE LOADED");
        } catch (ShipmentTypeNotFoundException stnfe) {
            LOG.error("UNABLE TO PROCESS LOADING {} ", stnfe.getMessage());
            throw stnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getAllShipmentType METHOD");
        return res;
    }

    @GetMapping("/find/{id}")
    @ApiOperation("FETCH ONE SHIPMENT TYPE RECORD")
    public ResponseEntity<?> getOneShipmentType(@PathVariable Integer id) {
        LOG.info("ENTERED INTO getOneShipmentType METHOD");
        ResponseEntity<?> res;
        try {
            ShipmentType shipmentType = service.getShipmentType(id);
            res = new ResponseEntity<Object>(shipmentType, HttpStatus.OK);
            LOG.debug("SHIPMENT TYPE LOADED {} ", id);
        } catch (ShipmentTypeNotFoundException stnfe) {
            LOG.error("UNABLE TO PROCESS LOADING {} ", stnfe.getMessage());
            throw stnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getOneShipmentType METHOD");
        return res;
    }

    @PostMapping("/create")
    @ApiOperation("CREATE SHIPMENT TYPE RECORD")
    public ResponseEntity<?> createShipmentType(@RequestBody ShipmentType st) {
        LOG.info("ENTERED INTO craeteShipmentType METHOD");
        ResponseEntity<?> res;
        try {
            Integer id = service.saveShipmentType(st);
            String msg = "ShipmentType " + id + " Created";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("SHIPMENT TYPE SAVED {} ", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING craeteShipmentType METHOD");
        return res;
    }

    @PutMapping("/modify")
    @ApiOperation("UPDATE SHIPMENT TYPE RECORD")
    public ResponseEntity<?> updateShipmentType(@RequestBody ShipmentType st) {
        LOG.info("ENTERED INTO updateShipmentType METHOD");
        ResponseEntity<?> res;
        try {
            service.updateShipmentType(st);
            String msg = "ShipmentType Updated";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("SHIPMENT TYPE UPDATED");
        } catch (ShipmentTypeNotFoundException stnfe) {
            LOG.error("UNABLE TO PROCESS {} ", stnfe.getMessage());
            throw stnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING updateShipmentType METHOD");
        return res;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("DELETE SHIPMENT TYPE RECORD")
    public ResponseEntity<?> deleteShipmentType(@PathVariable Integer id) {
        LOG.info("ENTERED INTO deleteShipmentType METHOD");
        ResponseEntity<?> res;
        try {
            service.deleteShipmentType(id);
            String msg = "ShipmentType " + id + " Deleted";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("SHIPMENT TYPE DELETED {} ", id);
        } catch (ShipmentTypeNotFoundException stnfe) {
            LOG.error("UNABLE TO DELETE  {} ", stnfe.getMessage());
            throw stnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO DELETE {} ", e.getMessage());
        }
        LOG.info("LEAVING deleteShipmentType METHOD");
        return res;
    }
}

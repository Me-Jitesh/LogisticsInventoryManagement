package com.ishopee.logisticsinventorymanagement.rest;

import com.ishopee.logisticsinventorymanagement.exceptions.OrderMethodNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import com.ishopee.logisticsinventorymanagement.services.IOrderMethodService;
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
@RequestMapping("/api/om")
@Api(description = "GET ORDER METHOD API DETAILS")
public class OrderMethodRestController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderMethodRestController.class);

    @Autowired
    private IOrderMethodService service;

    @GetMapping("/all")
    @ApiOperation("FETCH ALL ORDER METHOD RECORDS")
    public ResponseEntity<?> getAllOrderMethod() {
        LOG.info("ENTERED INTO getAllOrderMethod METHOD");
        ResponseEntity<?> res;
        try {
            List<OrderMethod> list = service.getAllOrderMethod();
            res = new ResponseEntity<Object>(list, HttpStatus.OK);
            LOG.debug("ALL ORDER METHOD LOADED");
        } catch (OrderMethodNotFoundException omnfe) {
            LOG.error("UNABLE TO PROCESS LOADING {} ", omnfe.getMessage());
            throw omnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getAllOrderMethod METHOD");
        return res;
    }

    @GetMapping("/find/{id}")
    @ApiOperation("FETCH ONE ORDER METHOD RECORD")
    public ResponseEntity<?> getOneOrderMethod(@PathVariable Integer id) {
        LOG.info("ENTERED INTO getOneOrderMethod METHOD");
        ResponseEntity<?> res;
        try {
            OrderMethod om = service.getOrderMethodById(id);
            res = new ResponseEntity<Object>(om, HttpStatus.OK);
            LOG.debug("ORDER METHOD LOADED {} ", id);
        } catch (OrderMethodNotFoundException omnfe) {
            LOG.error("UNABLE TO PROCESS LOADING {} ", omnfe.getMessage());
            throw omnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getOneOrderMethod METHOD");
        return res;
    }

    @PostMapping("/create")
    @ApiOperation("CREATE ORDER METHOD RECORD")
    public ResponseEntity<?> createOrderMethod(@RequestBody OrderMethod om) {
        LOG.info("ENTERED INTO craeteOrderMethod METHOD");
        ResponseEntity<?> res;
        try {
            Integer id = service.saveOrderMethod(om);
            String msg = "Order Method " + id + " Created";
            res = new ResponseEntity<>(msg, HttpStatus.CREATED);
            LOG.debug("ORDER METHOD SAVED {} ", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING craeteOrderMethod METHOD");
        return res;
    }

    @PutMapping("/modify")
    @ApiOperation("UPDATE ORDER METHOD RECORD")
    public ResponseEntity<?> updateOrderMethod(@RequestBody OrderMethod om) {
        LOG.info("ENTERED INTO updateOrderMethod METHOD");
        ResponseEntity<?> res;
        try {
            service.updateOrderMethod(om);
            String msg = "Order Method Updated";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("ORDER METHOD UPDATED");
        } catch (OrderMethodNotFoundException omnfe) {
            LOG.error("UNABLE TO PROCESS {} ", omnfe.getMessage());
            throw omnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING updateOrderMethod METHOD");
        return res;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("DELETE ORDER METHOD RECORD")
    public ResponseEntity<?> deleteOrderMethod(@PathVariable Integer id) {
        LOG.info("ENTERED INTO deleteOrderMethod METHOD");
        ResponseEntity<?> res;
        try {
            service.deleteOrderMethod(id);
            String msg = "Order Method " + id + " Deleted";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("ORDER METHOD DELETED {} ", id);
        } catch (OrderMethodNotFoundException omnfe) {
            LOG.error("UNABLE TO DELETE  {} ", omnfe.getMessage());
            throw omnfe;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO DELETE {} ", e.getMessage());
        }
        LOG.info("LEAVING deleteOrderMethod METHOD");
        return res;
    }
}

package com.ishopee.logisticsinventorymanagement.rest;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mus")
public class MusRestController {

    private static final Logger LOG = LoggerFactory.getLogger(MusRestController.class);

    @Autowired
    private IMusService service;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMus() {
        LOG.info("ENTERED INTO getAllMus METHOD");
        ResponseEntity<?> res;
        try {
            List<Mus> list = service.getAllMus();
            res = new ResponseEntity<Object>(list, HttpStatus.OK);
            LOG.debug("ALL MUS LOADED");
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getAllMus METHOD");
        return res;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getOneMus(@PathVariable Integer id) {
        LOG.info("ENTERED INTO getOneMus METHOD");
        ResponseEntity<?> res;
        try {
            Mus mus = service.getMus(id);
            res = new ResponseEntity<Object>(mus, HttpStatus.OK);
            LOG.debug("MUS LOADED {} ", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS LOADING {} ", e.getMessage());
        }
        LOG.info("LEAVING getOneMus METHOD");
        return res;
    }

    @PostMapping("/create")
    public ResponseEntity<?> craeteMus(@RequestBody Mus mus) {
        LOG.info("ENTERED INTO craeteMus METHOD");
        ResponseEntity<?> res;
        try {
            Integer id = service.saveMus(mus);
            String msg = "Mus " + id + " Created";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("MUS SAVED {} ", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING craeteMus METHOD");
        return res;
    }

    @PutMapping("/modify")
    public ResponseEntity<?> updateMus(@RequestBody Mus mus) {
        LOG.info("ENTERED INTO updateMus METHOD");
        ResponseEntity<?> res;
        try {
            service.updateMus(mus);
            String msg = "Mus Updated";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("MUS UPDATED");
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING updateMus METHOD");
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMus(@PathVariable Integer id) {
        LOG.info("ENTERED INTO deleteMus METHOD");
        ResponseEntity<?> res;
        try {
            service.deleteMus(id);
            String msg = "Mus " + id + " Deleted";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
            LOG.debug("MUS DELETED {} ", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO PROCESS {} ", e.getMessage());
        }
        LOG.info("LEAVING deleteMus METHOD");
        return res;
    }
}

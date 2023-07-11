package com.ishopee.logisticsinventorymanagement.rest;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mus")
public class MusRestController {

    @Autowired
    private IMusService service;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMus() {
        ResponseEntity<?> res;
        try {
            List<Mus> list = service.getAllMus();
            res = new ResponseEntity<Object>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getOneMus(@PathVariable Integer id) {
        ResponseEntity<?> res;
        try {
            Mus mus = service.getMus(id);
            res = new ResponseEntity<Object>(mus, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @PostMapping("/create")
    public ResponseEntity<?> craeteMus(@RequestBody Mus mus) {
        ResponseEntity<?> res;
        try {
            Integer id = service.saveMus(mus);
            String msg = "Mus " + id + " Created";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @PutMapping("/modify")
    public ResponseEntity<?> updateMus(@RequestBody Mus mus) {
        ResponseEntity<?> res;
        try {
            service.updateMus(mus);
            String msg = "Mus Updated";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMus(@PathVariable Integer id) {
        ResponseEntity<?> res;
        try {
            service.deleteMus(id);
            String msg = "Mus " + id + " Deleted";
            res = new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}

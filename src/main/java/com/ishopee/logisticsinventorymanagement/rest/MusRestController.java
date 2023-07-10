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
    public ResponseEntity<List<Mus>> getAllMus() {
        List<Mus> list = service.getAllMus();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Mus> getOneMus(@PathVariable Integer id) {
        Mus mus = service.getMus(id);
        return ResponseEntity.ok(mus);
    }

    @PostMapping("/create")
    public ResponseEntity<String> craeteMus(@RequestBody Mus mus) {
        Integer id = service.saveMus(mus);
        String msg = "Mus " + id + " Created";
        ResponseEntity<String> res = new ResponseEntity<>(msg, HttpStatus.OK);
        return res;
    }

    @PutMapping("/modify")
    public ResponseEntity<String> updateMus(@RequestBody Mus mus) {
        service.updateMus(mus);
        String msg = "Mus Updated";
        ResponseEntity<String> res = new ResponseEntity<>(msg, HttpStatus.OK);
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMus(@PathVariable Integer id) {
        service.deleteMus(id);
        String msg = "Mus " + id + " Deleted";
        ResponseEntity<String> res = new ResponseEntity<>(msg, HttpStatus.OK);
        return res;
    }
}

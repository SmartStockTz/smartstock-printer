package com.fahamutech.ssmjpos.rest;

import com.fahamutech.ssmjpos.service.MyPrinterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class PrintServiceRest {
    private MyPrinterService myPrinterService;

    PrintServiceRest(MyPrinterService myPrinterService) {
        this.myPrinterService = myPrinterService;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getPrinters() {
        List<String> printers = myPrinterService.getPrinters();
        if (printers != null) {
            return new ResponseEntity<List<String>>(printers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/print")
    public ResponseEntity<String> print(@RequestBody Map<String, Object> printData) {
        String data = (String) printData.get("data");
        String id = (String) printData.get("id");
        data = "#" + id + "\n\n" + data + "\n\n\n";

        boolean done = myPrinterService.printString("TM-T20II", data);
        if (done) {
            return new ResponseEntity<String>("done print", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fails to print", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

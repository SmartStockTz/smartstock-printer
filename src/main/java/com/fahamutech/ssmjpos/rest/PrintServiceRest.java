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
    private final MyPrinterService myPrinterService;

    PrintServiceRest(MyPrinterService myPrinterService) {
        this.myPrinterService = myPrinterService;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getPrinters() {
        List<String> printers = myPrinterService.getPrinters();
        if (printers != null) {
            return new ResponseEntity<>(printers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/print")
    public ResponseEntity<String> print(@RequestBody Map<String, Object> printData) {
        String data = (String) printData.get("data");
        String id = (String) printData.get("id");
        String printer = (String) printData.get("printer");
        data = "#" + id + "\n\n" + data + "\n\n\n";
        // "TM-T20II"
        boolean done = myPrinterService.printString(printer != null ? printer : "TM-T20II", data);
        if (done) {
            return new ResponseEntity<>("done print", HttpStatus.OK);
        }
        return new ResponseEntity<>("Fails to print", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

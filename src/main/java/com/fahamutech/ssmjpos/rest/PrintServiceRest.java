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
        String header =
                "          #" + id + "\n" +
                        "           Lb Pharmacy Ltd     \n" +
                        "       Gongo La Mboto Stand\n" +
                        "           P.O.Box 41593\n" +
                        "      number: +255717959146\n" +
                        data;

        String footer =
                "\n" +
                        "***********************************\n" +
                        "Kusoma oda piga \n" +
                        "1. 0684972687\n" +
                        "2. 0714702887\n" +
                        "3. 0768316283\n\n" +
                        "***************************************\n\n\n\n\n\n";
        String concat = header.concat(footer);
        boolean done = myPrinterService.printString("TM-T20II", concat);
        if (done) {
            return new ResponseEntity<String>("done print", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fails to print", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        // System.out.println(printData);
//        return "done print";
    }

}

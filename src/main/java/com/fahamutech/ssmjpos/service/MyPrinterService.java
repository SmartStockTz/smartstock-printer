package com.fahamutech.ssmjpos.service;

import org.springframework.stereotype.Component;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyPrinterService implements Printable {

    public List<String> getPrinters() {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, pras);
        List<String> printerList = new ArrayList<String>();
        for (PrintService printerService : printServices) {
            printerList.add(printerService.getName());
        }
        return printerList;
    }

    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /*
         * User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        /* Now we perform our rendering */

        g.setFont(new Font("Roman", 0, 8));
        g.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
    }

    public boolean printString(String printerName, String text) {

        // find the printService of name printerName
        // System.out.println("start");
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob job = outI(printerName, flavor);

        System.out.println(text);
        try {
            byte[] text2bytes;
            // important for umlaut chars
            text2bytes = text.getBytes("CP437");
            Doc doc = new SimpleDoc(text2bytes, flavor, null);
            assert job != null;
            job.print(doc, null);
            // cut that paper!
            byte[] cutP = new byte[]{0x1d, 'V', 1};
            printBytes(printerName, cutP);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }

    }

    public void printBytes(String printerName, byte[] bytes) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob job = outI(printerName, flavor);
        try {
            Doc doc = new SimpleDoc(bytes, flavor, null);
            assert job != null;
            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DocPrintJob outI(String printerName, DocFlavor flavor) {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        // PrintService printService1 = PrintServiceLookup.lookupDefaultPrintService();
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(
                flavor, printRequestAttributeSet);
        PrintService service = findPrintService(printerName, printService);
        // System.out.println(service.getName());
        if (service != null) {
            return service.createPrintJob();
        } else {
            return null;
        }
    }

    private PrintService findPrintService(String printerName,
                                          PrintService[] services) {
        for (PrintService service : services) {
            System.out.println(service.getName());
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }
}

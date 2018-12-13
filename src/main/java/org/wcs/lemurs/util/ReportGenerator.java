/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

/**
 *
 * @author vvizard
 */
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.io.FileUtils;
import org.wcs.lemurs.model.TaxonomiBase;

public class ReportGenerator {

//    public static void main(String[] args) throws Exception {
//        TaxonomiBase taxonomi = new TaxonomiBase();
//        taxonomi.setScientificname("test");
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        FileOutputStream fos = null;
//        try {
//            byte[] bytes = new ReportGenerator().generateJasperReportPDF("Fiche", outputStream, taxonomi);
//            if (bytes.length > 1) {
//                File someFile = new File("E:/test.pdf");
//                fos = new FileOutputStream(someFile);
//                fos.write(bytes);
//                fos.flush();
//                fos.close();
//                System.out.println("<<<<<<<<<<<<Report Created>>>>>>>>");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public  void generateJasperReportPDF(File file, TaxonomiBase taxonomi, String realPath, String profil) throws IOException {
        JRPdfExporter exporter = new JRPdfExporter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {                        
            // - Chargement et compilation du rapport
          //  String reportLocation = "E:\\kandra\\lemurs\\src\\main\\resources\\Fiche.jrxml";
           // JasperDesign jasperDesign = JRXmlLoader.load(reportLocation);
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("Fiche.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(in);
            jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            
            // - Paramètres à envoyer au rapport
            Map parameters = new HashMap();
            String sep = "/";
            if(!realPath.contains(sep)) {
                sep = "\\";
            }
            parameters.put("entete", realPath+"resources"+sep+"assets"+sep+"img"+sep+"enteteFin.png");
            if(profil.isEmpty()) {
                parameters.put("profil", realPath+"resources"+sep+"assets"+sep+"img"+sep+"Logo_LemursPortal_Final_old.png");
            }
            else {
                String p = realPath + profil.replace("/", sep);
                parameters.put("profil", p);
            }
            Field[] field = taxonomi.getClass().getDeclaredFields();
            for(Field f : field) {
                parameters.put(f.getName(), taxonomi.getClass().getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), null).invoke(taxonomi, null));
            } 
            parameters.put("link", "");
            // - Execution du rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            
            // - Export
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
            exporter.setConfiguration(configuration);
            exporter.exportReport();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in generate Report..." + e);
        } finally {
        }
        FileUtils.writeByteArrayToFile(file, outputStream.toByteArray());        
    }
}

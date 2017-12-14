/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wcs.lemurs.dao.DarwinCoreDao;
//import jdk.nashorn.internal.ir.Statement;

/**
 *
 * @author ando
 */
public class UploadFile {
    @Autowired(required = true)
    @Qualifier("ddao")
    DarwinCoreDao  ddao;
    public void import_file(String path) throws SQLException, Exception {
        ArrayList<Object> value = new ArrayList<>();
        
        try {
            InputStream input = new FileInputStream(path);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            
            Iterator rows = sheet.rowIterator();
           
            while (rows.hasNext()) {
                value.clear();
                
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                if(row.getRowNum() == 0) continue;
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        value.add((int) cell.getNumericCellValue());
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        value.add("'"+ cell.getStringCellValue() +"'");
                    } else if (HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
                        value.add(cell.getBooleanCellValue());
                    } else if (HSSFCell.CELL_TYPE_NUMERIC == cell.getNumericCellValue()) {
                        value.add(cell.getNumericCellValue());
                    }
                }
                    String requette = "INSERT INTO Darwincore darwincore VALUES (";
                    String values = value.get(0).toString();
                    String req = "";
                    for (int i = 1; i < value.size(); i++) {
                        values += "," + value.get(i);
                        req = requette + values + ");";
                    }
                    ddao.for_upload(req); //A verifier
            }
        } catch (IOException e) {
        }
    }
}

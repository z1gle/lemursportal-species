/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ando
 */
public class UploadFile {

    public static List<String> import_file(InputStream is) throws SQLException, Exception {
        
        List<String> list_req = new ArrayList<>();
        
        ArrayList<Object> value = new ArrayList<>();

        try {
            XSSFWorkbook  wb = new XSSFWorkbook (is);
            XSSFSheet sheet = wb.getSheetAt(0);

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                value.clear();

                XSSFRow row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                if (row.getRowNum() == 0) {
                    continue;
                }
                while (cells.hasNext()) {
                    XSSFCell cell = (XSSFCell) cells.next();
                    if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        value.add((int) cell.getNumericCellValue());
                    } else if (XSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        value.add("'" + cell.getStringCellValue() + "'");
                    } else if (XSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
                        value.add(cell.getBooleanCellValue());
                    } else if (XSSFCell.CELL_TYPE_NUMERIC == cell.getNumericCellValue()) {
                        value.add(cell.getNumericCellValue());
                    }
                }
                String requete = "INSERT into darwin_core () VALUES (";
                String values = value.get(0).toString();
                String req = "";
                for (int i = 1; i < value.size(); i++) {
                    values += "," + value.get(i);
                    req = requete + values + ")";
                }
                list_req.add(req); //A verifier
            }
        } catch (IOException e) {
            throw e;
        }
        return list_req;
    }
}

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

    public static List<String> import_darwin_core_excel(InputStream is) throws SQLException, Exception {

        List<String> list_req = new ArrayList<>();

        ArrayList<Object> value = new ArrayList<>();

        try {
            XSSFWorkbook wb = new XSSFWorkbook(is);
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
                String requete = "INSERT into darwin_core (\"iddwc\",\"acceptednameusage\",\"associatedmedia\",\"associatedoccurrences\",\"associatedreferences\",\"associatedsequences\",\n"
                        + "\"associatedtaxa\",\"basisofrecord\",\"behavior\",\"catalognumber\",\"collectioncode\",\"continent\",\"coordinateprecision\",\n"
                        + "\"coordinateuncertaintyinmeters\",\"country\",\"countrycode\",\"county\",\"datageneralizations\",\"datasetname\",\"dateidentified\",\n"
                        + "\"decimallatitude\",\"decimallongitude\",\"disposition\",\"darwinclass\",\"dwcday\",\"family\",\"dwcmonth\",\"darwinorder\",\"dwcyear\",\n"
                        + "\"dynamicproperties\",\"enddayofyear\",\"eventdate\",\"eventremarks\",\"eventtime\",\"fieldnotes\",\"fieldnumber\",\"footprintspatialfit\",\n"
                        + "\"footprintsrs\",\"footprintwkt\",\"genus\",\"geodeticdatum\",\"georeferencedby\",\"georeferenceprotocol\",\"georeferenceremarks\",\n"
                        + "\"georeferencesources\",\"georeferenceverificationstatus\",\"habitat\",\"higherclassification\",\"highergeography\",\"identificationqualifier\",\n"
                        + "\"identificationreferences\",\"identificationremarks\",\"identifiedby\",\"individualcount\",\"informationwithheld\",\"infraspecificepithet\",\n"
                        + "\"institutioncode\",\"island\",\"islandgroup\",\"kingdom\",\"lifestage\",\"locality\",\"locationaccordingto\",\"locationremarks\",\n"
                        + "\"maximumdepthinmeters\",\"maximumdistanceabovesurfaceinmeters\",\"maximumelevationinmeters\",\"minimumdepthinmeters\",\n"
                        + "\"minimumdistanceabovesurfaceinmeters\",\"minimumelevationinmeters\",\"municipality\",\"nomenclaturalcode\",\"occurrenceremarks\",\n"
                        + "\"othercatalognumbers\",\"ownerinstitutioncode\",\"phylum\",\"pointradiusspatialfit\",\"preparations\",\"previousidentifications\",\"recordedby\",\n"
                        + "\"recordnumber\",\"reproductivecondition\",\"samplingeffort\",\"samplingprotocol\",\"scientificname\",\"scientificnameauthorship\",\"sex\",\n"
                        + "\"specificepithet\",\"startdayofyear\",\"stateprovince\",\"subgenus\",\"taxonrank\",\"taxonremarks\",\"typestatus\",\"verbatimcoordinates\",\n"
                        + "\"verbatimcoordinatesystem\",\"verbatimdepth\",\"verbatimelevation\",\"verbatimeventdate\",\"verbatimlatitude\",\"verbatimlocality\",\n"
                        + "\"verbatimlongitude\",\"verbatimsrs\",\"verbatimtaxonrank\",\"vernacularname\",\"waterbody\") VALUES (";
                String values = value.get(0).toString();
                String req = "";
                for (int i = 1; i < value.size(); i++) {
                    values += "," + value.get(i);
                    req = requete + values + ")";
                }
                list_req.add(req);
            }
        } catch (IOException e) {
            throw e;
        }
        return list_req;
    }
}

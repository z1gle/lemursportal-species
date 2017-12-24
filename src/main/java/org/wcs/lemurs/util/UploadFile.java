/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.TaxonomiBase;

/**
 *
 * @author ando
 */
public class UploadFile {

    public static List<DarwinCore> import_darwin_core_excel(InputStream is) throws SQLException, Exception {

        List<DarwinCore> list_dw = new ArrayList<>();

        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        Iterator rows = sheet.rowIterator();

        String methodes = "setInstitutioncode,setCollectioncode,setDatasetname,setOwnerinstitutioncode,setBasisofrecord,setInformationwithheld,setDatageneralizations,setDynamicproperties,setScientificname,setAcceptednameusage,setHigherclassification,setKingdom"
                + ",setPhylum,setDarwinclass,setDarwinorder,setFamily,setGenus,setSubgenus,setSpecificepithet,setInfraspecificepithet,setTaxonrank,setVerbatimtaxonrank,setScientificnameauthorship,setVernacularname,setNomenclaturalcode,setTaxonremarks,setCatalognumber"
                + ",setOccurrenceremarks,setRecordnumber,setRecordedby,setIndividualcount,setSex,setLifestage,setReproductivecondition,setBehavior,setPreparations,setDisposition,setOthercatalognumbers,setPreviousidentifications,setAssociatedmedia,setAssociatedreferences"
                + ",setAssociatedoccurrences,setAssociatedsequences,setAssociatedtaxa,setSamplingprotocol,setSamplingeffort,setEventdate,setEventtime,setStartdayofyear,setEnddayofyear,setDwcyear,setDwcmonth,setDwcday,setVerbatimeventdate,setHabitat,setFieldnumber,setFieldnotes"
                + ",setEventremarks,setHighergeography,setContinent,setWaterbody,setIslandgroup,setIsland,setCountry,setCountrycode,setStateprovince,setCounty,setMunicipality,setLocality,setVerbatimlocality,setVerbatimelevation,setMinimumelevationinmeters,setMaximumelevationinmeters"
                + ",setVerbatimdepth,setMinimumdepthinmeters,setMaximumdepthinmeters,setMinimumdistanceabovesurfaceinmeters,setMaximumdistanceabovesurfaceinmeters,setLocationaccordingto,setLocationremarks,setVerbatimcoordinates,setVerbatimlatitude,setVerbatimlongitude"
                + ",setVerbatimcoordinatesystem,setVerbatimsrs,setDecimallatitude,setDecimallongitude,setGeodeticdatum,setCoordinateuncertaintyinmeters,setCoordinateprecision,setPointradiusspatialfit,setFootprintwkt,setFootprintsrs,setFootprintspatialfit,setGeoreferencedby"
                + ",setGeoreferenceprotocol,setGeoreferencesources,setGeoreferenceverificationstatus,setGeoreferenceremarks,setIdentifiedby,setDateidentified,setIdentificationreferences,setIdentificationremarks,setIdentificationqualifier,setTypestatus";
        String[] allmethodes = methodes.split(",");

        int count_cell = 0;
        row = (XSSFRow) rows.next();
        count_cell = row.getFirstCellNum() + row.getLastCellNum();
        
        int index_method = 0;
        int index_cell = 0;
        DarwinCore dw = null;
        while (rows.hasNext()) {

            dw = new DarwinCore();
            row = (XSSFRow) rows.next();
            while (index_cell < count_cell) {

                cell = row.getCell(index_cell);
                if (cell != null) {
                    
                    dw.getClass().getMethod(allmethodes[index_method], String.class).invoke(dw, cell.toString());
                } else {
                    
                    dw.getClass().getMethod(allmethodes[index_method], String.class).invoke(dw, "-");
                }
                index_method++;
                index_cell++;
            }
            list_dw.add(dw);
            index_method = 0;
            index_cell = 0;
        }

        return list_dw;
    }

    public List<TaxonomiBase> import_taxonomi_base_excel(InputStream is) throws Exception {

        List<TaxonomiBase> list_taxonomi = new ArrayList<>();

        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        Iterator rows = sheet.rowIterator();
        int i = 0;

        String methodes = "setHigherclassification,setKingdom,setPhylum,setDwcclass,setDwcorder,setDwcfamily,setGenus,setGenussource,setSpecificepithet,setSpecificepithetsource,setInfraspecificepithet,setInfraspecificepithetsource,setScientificname,setScientificnameauthorship,setAcceptednameusage,setBasisofrecord,setFrenchvernacularname,setMalagasyvernacularname,setEnglishvernacularname,setHabitatFr,setHabitatEn,setHabitatsource,setEcologyFr,setEcologyEn,setEcologysource,setBehaviorFr,setBehaviorEn,setBehaviorsource,setThreatFr,setThreatEn,setThreatsource,setMorphologyFr,setMorphologyEn,setProtectedareaoccurrences";
        String[] allmethodes = methodes.split(",");

        int count_cell = 0;
        row = (XSSFRow) rows.next();
        count_cell = row.getFirstCellNum() + row.getLastCellNum();
        
        int index_method = 0;
        int index_cell = 0;
        TaxonomiBase taxo = null;
        while (rows.hasNext()) {

            taxo = new TaxonomiBase();
            row = (XSSFRow) rows.next();
            while (index_cell < count_cell) {

                cell = row.getCell(index_cell);
                if (cell != null) {
                    
                    taxo.getClass().getMethod(allmethodes[index_method], String.class).invoke(taxo, cell.toString());
                } else {
                    
                    taxo.getClass().getMethod(allmethodes[index_method], String.class).invoke(taxo, "-");
                }
                index_method++;
                index_cell++;
            }
            list_taxonomi.add(taxo);
            index_method = 0;
            index_cell = 0;
        }
        
        return list_taxonomi;
    }
}

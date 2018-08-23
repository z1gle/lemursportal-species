/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.TaxonomiBase;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;
import org.wcs.lemurs.service.DarwinCoreService;

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

//        String meth = "";
        Field[] colonnes = DarwinCore.class.getDeclaredFields();
//        int iterator = 0;
//        for (Field f : colonnes) {
//            meth += DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), String.class) + ",";
//            iterator++;
//            if (iterator == colonnes.length - 1) {
//                break;
//            }
//        }
//
//        String[] allmethodes = meth.split(",");

        int count_cell = 0;
        row = (XSSFRow) rows.next();

        List<HashMap<String, Object>> fonctions = new ArrayList<>();
        List<String> colonnesHeader = new ArrayList<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            colonnesHeader.add(row.getCell(i).toString());
            String colonne = row.getCell(i).toString().toLowerCase();
            for (Field f : colonnes) {
                String base = f.getName().toLowerCase();
                base = base.replaceAll("dwc", "");
                base = base.replaceAll("darwinclass", "class");
                base = base.replaceAll("darwinorder", "order");
                base = base.replaceAll("idrebioma", "id");
                if (base.compareTo(colonne) == 0) {
                    HashMap<String, Object> fonction = new HashMap<>();
                    fonction.put("id", i);
                    try {
                        fonction.put("fonction", DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), String.class));
                    } catch (NoSuchMethodException nsme) {
                        fonction.put("fonction", DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), Integer.class));
                    }
                    fonctions.add(fonction);
                    break;
                }
            }
        }

        count_cell = row.getFirstCellNum() + row.getLastCellNum();

        int index_method = 0;
        int index_cell = 0;
        DarwinCore dw = null;
        while (rows.hasNext()) {

            dw = new DarwinCore();
            row = (XSSFRow) rows.next();

            for (HashMap<String, Object> fonction : fonctions) {
                Method m = (Method) fonction.get("fonction");
                try {
                    String valeur = "-";
                    if (row.getCell((Integer) fonction.get("id")) != null) {
                        valeur = row.getCell((Integer) fonction.get("id")).toString();
                        valeur = enleverEspaceDebutFin(valeur);
                    }
                    if (valeur.compareTo(" ") == 0 || valeur.isEmpty()) {
                        valeur = "-";
                    }
                    m.invoke(dw, valeur);
                } catch (IllegalArgumentException iae) {
                    try {
                        String sToInt = row.getCell((Integer) fonction.get("id")).toString();
                        sToInt = sToInt.replaceAll(" ", "");
                        Integer tempInt = Integer.decode(sToInt);
                        m.invoke(dw, tempInt);
                    } catch (Exception iaex) {
                        throw iaex;
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    aioobe.printStackTrace();
                }
            }
            list_dw.add(dw);

//            while (index_cell < count_cell) {
//
//                cell = row.getCell(index_cell);
//                if (cell != null) {
//
//                    dw.getClass().getMethod(allmethodes[index_method], String.class).invoke(dw, cell.toString());
//                } else {
//
//                    dw.getClass().getMethod(allmethodes[index_method], String.class).invoke(dw, "-");
//                }
//                index_method++;
//                index_cell++;
//            }
//            list_dw.add(dw);
//            index_method = 0;
//            index_cell = 0;
        }
        return list_dw;
    }

    public static List<TaxonomiBase> import_taxonomi_base_excel(InputStream is) throws Exception {

        List<TaxonomiBase> list_taxonomi = new ArrayList<>();

        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;
        Iterator rows = sheet.rowIterator();
        int i = 0;
        String methodes = "";

        Field[] colonnes = TaxonomiBase.class.getDeclaredFields();
        for (Field f : colonnes) {
            methodes += "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1) + ",";
        }
        methodes = methodes.substring(0, methodes.length() - 1);
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

    public void writeCsv(List<TaxonomiBase> taxonomies, char separator, OutputStream output) throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        String header = "";
        Field[] colonnes = TaxonomiBase.class.getDeclaredFields();
        for (Field f : colonnes) {
            String temp = f.getName();
            if (temp.contains("dwc")) {
                temp = temp.split("dwc")[0] + "Darwin core " + temp.split("dwc")[1];
            }
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            header += temp + separator;
        }
        header = header.substring(0, header.length() - 1);
        writer.append(header);
        writer.newLine();
        for (TaxonomiBase row : taxonomies) {
            String field = "";
            for (Field f : colonnes) {
                try {
                    String temp = (String) row.getClass().getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), null).invoke(row, null);
                    if (temp.contains(";")) {
                        temp = temp.replace(";", ",");
                    }
                    field += temp + ";";
                } catch (Exception e) {
                    field += "-;";
                }
            }
            field = field.substring(0, field.length() - 1);
            writer.append(field);
            writer.newLine();
        }
        writer.flush();
    }

    public void writeDwcCsv(List<VueValidationDarwinCore> taxonomies, int[] listeColonnes, char separator, OutputStream output, int idU) throws Exception {
        output.write(239);
        output.write(187);
        output.write(191);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        String header = "";
        if (idU > 0) {
            header += "id" + separator;
        }
        Field[] colonnes = DarwinCore.class.getDeclaredFields();
        for (int f : listeColonnes) {
            String temp = colonnes[f].getName();
            if (temp.contains("dwc")) {
                temp = temp.split("dwc")[0] + "" + temp.split("dwc")[1];
            } else if (temp.contains("darwin")) {
                temp = temp.split("darwin")[0] + "" + temp.split("darwin")[1];
            }
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            header += temp + separator;
        }
        header = header.substring(0, header.length() - 1);
        writer.append(header);
        writer.newLine();
        for (VueValidationDarwinCore row : taxonomies) {
            String field = "";
            if (idU > 0) {
                field += Integer.toString(row.getId()) + ";";
            }
            for (int f : listeColonnes) {
                try {
                    String temp = (String) row.getClass().getMethod("get" + colonnes[f].getName().substring(0, 1).toUpperCase() + colonnes[f].getName().substring(1), null).invoke(row, null);
                    if (temp.contains(String.valueOf(separator))) {
                        temp = temp.replace(String.valueOf(separator), ",");
                    }
                    field += temp + String.valueOf(separator);
                } catch (Exception e) {
                    field += "-" + String.valueOf(separator);
                }
            }
            field = field.substring(0, field.length() - 1);
            writer.append(field);
            writer.newLine();
        }
        writer.flush();
    }

    public static String checkSyntaxEnleverEspaceFin(String texte) {
        String valiny = "";
        if (texte.endsWith(" ")) {
            valiny = texte.substring(0, texte.length() - 1);
            if (valiny.endsWith(" ")) {
                return checkSyntaxEnleverEspaceFin(valiny);
            } else {
                return valiny;
            }
        } else {
            return valiny;
        }
    }

    public static String checkSyntaxEnleverEspaceDebut(String texte) {
        String valiny = "";
        if (texte.startsWith(" ")) {
            valiny = texte.substring(1);
            if (valiny.startsWith(" ")) {
                return checkSyntaxEnleverEspaceDebut(valiny);
            } else {
                return valiny;
            }
        } else {
            return texte;
        }
    }

    public static String enleverEspaceDebutFin(String texte) {
        texte = checkSyntaxEnleverEspaceFin(texte);
        texte = checkSyntaxEnleverEspaceDebut(texte);
        return texte;
    }

    public static List<DarwinCore> import_darwin_core_csv(InputStream is, String separator) throws SQLException, Exception {
        
        List<DarwinCore> list_dw = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "Cp1252"));
        Field[] colonnes = DarwinCore.class.getDeclaredFields();
        String line = "";
        line = br.readLine();
        DarwinCoreService dcs = new DarwinCoreService();
        List<HashMap<String, Object>> fonctions = dcs.getFonctionNumeroColonneDwc(colonnes, line, separator);
        while ((line = br.readLine()) != null) {
            line = line.replaceAll(separator, " "+separator+" ");
            String[] cols = line.split(separator);
            /*if (cols.length < colonnes.length - 4) {
                throw new Exception("Le nombre de colonnes est manquantes cols = " + Integer.toString(cols.length) + " and dwc = " + Integer.toString(colonnes.length - 1));
            } else*/
            if (cols.length <= colonnes.length - 4 && cols.length > 2) {
                DarwinCore dwcTemp = new DarwinCore();
                for (HashMap<String, Object> fonction : fonctions) {
                    Method m = (Method) fonction.get("fonction");
                    Integer intTemp = (Integer) fonction.get("id");
                    String idEnCours = cols[intTemp];
                    try {
                        String valeur = idEnCours;
                        valeur = enleverEspaceDebutFin(valeur);
                        if (valeur.compareTo(" ") == 0 || valeur.isEmpty()) {
                            valeur = "-";
                        }
                        m.invoke(dwcTemp, valeur);
                    } catch (IllegalArgumentException iae) {
                        try {
                            String sToInt = cols[(Integer) fonction.get("id")];
                            sToInt = sToInt.replaceAll(" ", "");
                            Integer tempInt = Integer.decode(sToInt);
                            m.invoke(dwcTemp, tempInt);
                        } catch (Exception iaex) {
                            throw iaex;
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        aioobe.printStackTrace();
                    }
                }
                list_dw.add(dwcTemp);
            } else {
                DarwinCore dwcTemp = new DarwinCore();
                try {
                    dwcTemp.getClass().getMethod("setId", Integer.class).invoke(dwcTemp, Integer.parseInt(cols[0].replaceAll(" ", "")));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("vérifier le nombre de colonne du darwin core");
                }

                int iterator = 1;
                for (HashMap<String, Object> fonction : fonctions) {
                    Method m = (Method) fonction.get("fonction");
                    try {
                        m.invoke(dwcTemp, cols[(Integer) fonction.get("id")]);
                    } catch (IllegalArgumentException iae) {
                        try {
                            String sToInt = cols[(Integer) fonction.get("id")];
                            sToInt = sToInt.replaceAll(" ", "");
                            Integer tempInt = Integer.decode(sToInt);
                            m.invoke(dwcTemp, tempInt);
                        } catch (java.lang.NumberFormatException iaex) {
                            System.out.println("valeur de " + m.getName() + " est de 0");
                        } catch (Exception iaex) {
                            throw iaex;
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        aioobe.printStackTrace();
                    }
                }
                list_dw.add(dwcTemp);
            }
        }
        return list_dw;
    }

}

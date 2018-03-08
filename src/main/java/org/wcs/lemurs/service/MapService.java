/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.io.File;
import org.wcs.lemurs.util.ogr2ogr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class MapService extends DarwinCoreService {
    ogr2ogr ogr2ogr = new ogr2ogr();
    public void toKML() {        
//        System.setProperty("java.library.path", "C:/Program Files/GDAL/gdalconstjni.dll;C:/Program Files/GDAL/gdaljni.dll;C:/Program Files/GDAL/ogrjni.dll;C:/Program Files/GDAL/osrjni.dll");
//        System.setProperty("java.library.path", "C:/Program Files (x86)/GDAL/gdaljni.dll");
//        System.setProperty("java.library.path", "C:/Program Files (x86)/GDAL/ogrjni.dll");
//        System.setProperty("java.library.path", "C:/Program Files (x86)/GDAL/osrjni.dll");
//        Runtime.getRuntime().loadLibrary("C:/Program Files/GDAL/gdalconstjni.dll");
//        Runtime.getRuntime().loadLibrary("C:/Program Files/GDAL/gdaljni.dll");
//        Runtime.getRuntime().loadLibrary("C:/Program Files/GDAL/ogrjni.dll");
//        Runtime.getRuntime().loadLibrary("C:/Program Files/GDAL/osrjni.dll");
//        System.out.println(System.getProperty("java.library.path"));
        String[] cmd = {"-t_srs", "CRS:84", "-f", "KML","E:\\vvizard\\Projet en cours\\testshpLemurs\\protected_areas.kml","E:\\vvizard\\Projet en cours\\testshpLemurs\\protected_areas.shp"};        
        ogr2ogr.main(cmd);
    }
    
    public void mapTest() throws Exception {
        File fichier = null;
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();     
            String nom = "E:\\vvizard\\Projet en cours\\testshpLemurs\\protected_areas";
            String pathToExe = "ogr2ogr";//C:\\Program Files (x86)\\GDAL\\ogr2ogr.exe";
            String command = "cmd /C e: && cd E:\\vvizard\\Projet en cours\\testshpLemurs\\ && "+pathToExe+" -f \"KML\" protected_areas.kml protected_areas.shp";// +pathToExe + " -f \"KML\" "+nom+".kml "+nom+".shp";
            p = runtime.exec(command, null);
            int processComplete = p.waitFor();
            if (processComplete == 0) {
                System.out.println("tokony mande");
            } else {
                throw new Exception(Integer.toString(processComplete));
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
}

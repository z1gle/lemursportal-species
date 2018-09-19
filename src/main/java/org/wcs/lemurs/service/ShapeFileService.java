/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.web.multipart.MultipartFile;
import org.wcs.lemurs.model.Kml;
import org.wcs.lemurs.model.ShpInfo;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class ShapeFileService extends BaseService {

    public static String PATH_Shp2pgsql = "C:\\Program Files\\PostgreSQL\\10\\bin\\shp2pgsql.exe";

    private static final NumberFormat NUMBER_FORMAT;

    static {
        NUMBER_FORMAT = new DecimalFormat();
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    public void saveShp(MultipartFile file, ShpInfo shpInfo) throws IOException, Exception {
        InputStream is = null;
        BufferedOutputStream stream = null;
        try {
            File fileTemp = File.createTempFile(shpInfo.getShapeTable(), ".zip");
            is = file.getInputStream();
            stream = new BufferedOutputStream(new FileOutputStream(fileTemp));
            int i;
            while ((i = is.read()) != -1) {
                stream.write(i);
            }
            stream.flush();
            String cheminDossier = shpInfo.getPath().substring(0, shpInfo.getPath().lastIndexOf(File.separator)) + shpInfo.getShapeTable();
            ZipService.unZipIt(fileTemp, cheminDossier, true);
            String cheminShp = cheminDossier + File.separator + file.getOriginalFilename().replaceAll(".zip", ".shp").replaceAll("-", "");
            saveShapeFile(cheminShp, shpInfo, PATH_Shp2pgsql);
            deplacerPhoto(fileTemp, cheminDossier, shpInfo.getShapeTable() + ".zip");
        } finally {
            if (is != null) {
                is.close();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    public void saveShapeFile(String pathShape, ShpInfo shpInfo, String pathShp2pgsql) throws Exception {
        System.out.println("START Batch " + new Date());
        File child = new File(pathShape);
        String extension = "";
        String filename = "";
        int i = child.getName().lastIndexOf('.');
        if (i > 0) {
            extension = child.getName().substring(i + 1);
            filename = child.getName().substring(0, i);
        }
        if (!extension.equalsIgnoreCase("shp")) {
            throw new Exception("The file is not a shapeFile");
        }
        System.out.println("*** Start treating " + filename);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.out.println(pathShp2pgsql + "   " + child.getAbsolutePath() + "  " + filename);
        CommandLine cmdLine = CommandLine.parse(pathShp2pgsql + "   " + child.getAbsolutePath() + "  " + filename);
        PumpStreamHandler psh = new PumpStreamHandler(bos, System.out);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(psh);
        try {
            executor.execute(cmdLine);
            String sql = bos.toString();
            Session session = null;
            Transaction tr = null;
            try {
                session = super.getHibernateDao().getSessionFactory().openSession();
                tr = session.beginTransaction();

                save(session, shpInfo);

                session.createSQLQuery("DROP TABLE  IF EXISTS " + filename)
                        .executeUpdate();
                session.createSQLQuery(
                        "DELETE FROM shp_info WHERE shapetable='"
                        + filename + "'").executeUpdate();
                sql = sql.replaceAll(": ", ":");
                System.err.println("begin sql : \n" + sql + "\nfin sql \n");
                try {
                    session.createSQLQuery(sql).executeUpdate();
                } catch (org.hibernate.exception.GenericJDBCException psqle) {
                    if (psqle.getCause().getMessage().contains("Un résultat a été retourné alors qu'aucun n'était attendu")) {
                        // There is no pbm
                    } else {
                        throw psqle;
                    }
                }

                session.createSQLQuery(
                        "CREATE INDEX idx_" + filename + " ON " + filename
                        + " USING GIST(geom)").executeUpdate();
                tr.commit();
            } catch (Exception re) {
                if (tr != null) {
                    tr.rollback();
                }
                throw re;
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            System.out.println("*** End treating " + filename);
        } catch (ExecuteException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            bos.close();
        }
    }

    public HashMap<String, String> getKmlFile(String tableName, List<Integer> gids, double gisSimplificationTolerance, String chemin) throws Exception {
        ShpInfo shp = new ShpInfo();
        shp.setShapeTable(tableName);

        String valiny = "";

        Session session = null;
        try {
            session = super.getHibernateDao().getSessionFactory().openSession();
            try {
                shp = (ShpInfo) super.findAll(shp).get(0);
            } catch (NullPointerException npe) {
                throw new Exception("Impossible de trouver le fichier shp dans la base de donnée");
            }
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ")
                    .append(shp.getNomChampGid()).append(" as ").append(KmlService.KML_GID_NAME).append(", ")
                    .append(shp.getShapeLabel()).append(" as ").append(KmlService.KML_LABEL_NAME)
                    .append(", ST_AsKML(CAST(ST_Simplify(").append(shp.getNomChampGeometrique()).append(", :tolerance) as varchar)) as gisAsKmlResult ");
            sql.append(" FROM ").append(tableName)
                    .append(" WHERE ").append(shp.getNomChampGid()).append(" IN (");

            String tolerance = NUMBER_FORMAT.format(gisSimplificationTolerance);
            tolerance = tolerance.replace(',', '_');
            StringBuilder fileNameSuffix = new StringBuilder();
            fileNameSuffix.append(tolerance).append("_")
                    .append(gids.size());
            int idx = 0;
            for (Integer gid : gids) {//on prefère utilise des OR plutot que un IN
                if (idx > 0) {
                    sql.append(",");
                }
                sql.append(gid);
                fileNameSuffix.append(gid);
                idx++;
            }
            sql.append(") ");
            String fileName = tableName + fileNameSuffix.toString();
            fileName = gids.stream().map((gid) -> "_" + gid).reduce(fileName, String::concat);
            File file = new File(chemin + fileName + ".kml");
            if (!file.exists()) {
                file = File.createTempFile(fileName, ".kml");
                System.out.println("**************************************");
                System.out.println("Création du fichier " + file.getAbsolutePath());
                System.out.println("**************************************");

                SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
                sqlQuery.setDouble("tolerance", gisSimplificationTolerance);
                sqlQuery.addScalar(KmlService.KML_GID_NAME);
                sqlQuery.addScalar(KmlService.KML_LABEL_NAME);
                sqlQuery.addScalar("gisAsKmlResult");
                sqlQuery.setResultTransformer(Transformers.aliasToBean(Kml.class));
                List<Kml> kmlDbRows = sqlQuery.list();
                String kml = KmlService.getKMLString(kmlDbRows);
                deplacerPhoto(file, chemin, fileName + ".kml");
                writeKmlFile(kml, chemin, fileName);
                System.out.println("**************************************");
                System.out.println("Fichier " + file.getAbsolutePath() + " Créé");
                System.out.println("**************************************");
            } else {
                System.out.println("**************************************");
                System.out.println("Le fichier " + file.getAbsolutePath() + " existe déjà");
                System.out.println("**************************************");
            }
            valiny = chemin + fileName + ".kml";
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        HashMap<String, String> farany = new HashMap<>();
        farany.put("absolutePath", valiny);
        farany.put("link", valiny.replace("\\", "/").substring(valiny.indexOf("resources")));
        return farany;
    }

    private void writeKmlFile(String kml, String filePath, String fileName) throws IOException {
        File file = new File(filePath + "/" + fileName + ".kml");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(kml);
        writer.close();
    }

    public List<Kml> findAllKML(Integer shpId) throws Exception {
        double gisTableSimplificationTolerance = 0.01d;//default value

        ShpInfo shp = new ShpInfo();
        shp.setId(shpId);

        Session session = null;

        try {
            session = super.getHibernateDao().getSessionFactory().openSession();
            super.findById(session, shp);
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ")
                    .append(shp.getNomChampGid()).append(" as ").append(KmlService.KML_GID_NAME).append(", ")
                    .append(shp.getShapeLabel()).append(" as ").append(KmlService.KML_LABEL_NAME)
                    .append(", ST_AsKML(CAST(ST_Simplify(").append(shp.getNomChampGeometrique()).append(", :tolerance) as varchar)) as gisAsKmlResult ");
            sql.append(" FROM ").append(shp.getShapeTable());
            SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
            sqlQuery.addScalar(KmlService.KML_GID_NAME);
            sqlQuery.addScalar(KmlService.KML_LABEL_NAME);
            sqlQuery.setDouble("tolerance", gisTableSimplificationTolerance);
            sqlQuery.addScalar("gisAsKmlResult");
            sqlQuery.setResultTransformer(Transformers.aliasToBean(Kml.class));
            return sqlQuery.list();
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
//import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.PhotoTaxonomi;
import org.wcs.lemurs.model.TaxonomiBase;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class ExportService extends BaseService {

    public static Font policeTitre = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    public static Font policeSousTitre = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    public static Font policeTexte = new Font(Font.FontFamily.TIMES_ROMAN, Font.DEFAULTSIZE);

    public Document getDocument(File file) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        return document;
    }

    public void setImageEntete(Document document, String nomImage) throws Exception {
        if (nomImage != null && !nomImage.isEmpty()) {
            Image image = Image.getInstance(nomImage);
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            document.add(image);
            document.add(new Paragraph());
        }
    }

    public void setImageProfil(Document document, String nomImage) throws Exception {
        if (nomImage != null && !nomImage.isEmpty()) {
            Image image = Image.getInstance(nomImage);
            image.scaleToFit(PageSize.A4.getWidth() / 2, PageSize.A4.getHeight());
            image.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);
            document.add(image);
            document.add(new Paragraph());
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        }
    }

    public Paragraph getTitre(String titreDocument) {
        Paragraph titre = new Paragraph(titreDocument, policeTitre);
        titre.setAlignment(Image.ALIGN_CENTER);
        titre.setSpacingAfter(10);
        return titre;
    }

    public Paragraph addSousTitre(Document document, String titreDocument) throws DocumentException {
        Paragraph titre = new Paragraph(titreDocument, policeSousTitre);
        titre.setAlignment(Image.ALIGN_JUSTIFIED);
        titre.setSpacingAfter(10);
        document.add(titre);
        return titre;
    }

    private void addText(Document document, String texte) throws DocumentException {
        Paragraph p = new Paragraph(texte, policeTexte);
        p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        p.setSpacingAfter(20);
        document.add(p);
    }

    private void addPartText(Document document, java.util.List<String> textes) throws DocumentException {
        List orderedList = new List(List.UNORDERED);
        for(String s : textes) {
            orderedList.add(new ListItem(s));
        }      
        document.add(orderedList);
        document.add(Chunk.NEWLINE);
    }

    public void genererPdfSujet(File file, TaxonomiBase taxonomi, String realPath) throws Exception {
        PhotoTaxonomi pht = new PhotoTaxonomi();
        pht.setProfil(Boolean.TRUE);
        pht.setIdTaxonomi(taxonomi.getId());
        try {
            java.util.List<PhotoTaxonomi> liste = (java.util.List<PhotoTaxonomi>)(java.util.List<?>)this.findMultiCritere(pht);
            pht = ((java.util.List<PhotoTaxonomi>)(java.util.List<?>)this.findMultiCritere(pht)).get(0);
        } catch(NullPointerException npe) {            
            pht.setChemin("resources\\assets\\img\\photos\\default.jpg");
        }        
        Document document = getDocument(file);
        document.open();
        setImageEntete(document, realPath + "resources\\assets\\img\\photos\\entete.jpg");
        document.add(getTitre(taxonomi.getScientificname()));        
        setImageProfil(document, realPath + pht.getChemin());        
        
        java.util.List<String> textes = new ArrayList<>();
        
        addSousTitre(document, "TAXONOMI");        
        textes.add("Kingdom : "+taxonomi.getKingdom());
        textes.add("Phylum : "+taxonomi.getPhylum());
        textes.add("Class : "+taxonomi.getDwcclass());
        textes.add("Order : "+taxonomi.getDwcorder());
        textes.add("Family : "+taxonomi.getDwcfamily());
        textes.add("Genus : "+taxonomi.getGenus());
        textes.add("Scientific Name : "+taxonomi.getScientificname());
        textes.add("French Vernacular Name : "+taxonomi.getFrenchvernacularname());
        textes.add("Malagasy Vernacular Name : "+taxonomi.getMalagasyvernacularname());
        textes.add("English Vernacular Name : "+taxonomi.getEnglishvernacularname());
        textes.add("Germany Vernacular Name : "+taxonomi.getGermanyVernacularName());
        textes.add("Kingdom : "+taxonomi.getKingdom()); 
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "MORPHOLOGIE");
        textes.add("Length and weight : "+taxonomi.getLengthAndWeight());
        textes.add("Length and weight source : "+taxonomi.getLengthAndWeightSource());
        textes.add("Color (EN) : "+taxonomi.getColorEn());
        textes.add("Color (FR) : "+taxonomi.getColorFr());
        textes.add("Color source : "+taxonomi.getColorSource());
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "HABITAT ET DENSITE DE LA POPULATION");
        textes.add("Habitat (EN) : " + taxonomi.getHabitatEn());
        textes.add("Habitat (FR) : " + taxonomi.getHabitatFr());
        textes.add("Habitat Source : " + taxonomi.getHabitatsource());
        textes.add("Population density : " + taxonomi.getPopulationDensity());
        textes.add("Population Density Source : " + taxonomi.getPopulationDensitySource());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "ECOLOGIE ET COMPORTEMENT");        
        textes.add("Ecology (EN) : " + taxonomi.getEcologyEn());
        textes.add("Ecology (FR) : " + taxonomi.getEcologyFr());
        textes.add("Ecology Source : " + taxonomi.getEcologysource());
        textes.add("Social behavior (EN) : " + taxonomi.getBehaviorEn());
        textes.add("Social behavior (FR) : " + taxonomi.getBehaviorFr());
        textes.add("Social behavior Source : " + taxonomi.getBehaviorsource());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "MENACES");                
        textes.add("Threat (EN) : " + taxonomi.getThreatEn());
        textes.add("Threat (FR) : " + taxonomi.getThreatFr());
        textes.add("Threat Source : " + taxonomi.getThreatsource());        
        addPartText(document, textes);
        textes.clear();
                
        //
        addSousTitre(document, "Statut de conservation");            
        textes.add("Conservation Statut IUCN : " + taxonomi.getConservationStatus());
        textes.add("IUCN Source : " + taxonomi.getIucnSource());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "Aires protégées");                  
        textes.add("Protected area occurrences : " + taxonomi.getProtectedareaoccurrences());
        textes.add("protected area occurrences Sources : " + taxonomi.getProtectedAreaOccurrencesSources());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "Experts");                        
        textes.add("Species expert : " + taxonomi.getSpeciesExpert());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "Actualités");                        
        textes.add("News and updates : " + taxonomi.getNewAndUpdates());        
        addPartText(document, textes);
        textes.clear();
        
        addSousTitre(document, "Publications");                        
        textes.add("Top five publications : " + taxonomi.getTopFivePublication());        
        addPartText(document, textes);
        textes.clear();
        
        document.close();
    }
}

package org.wcs.lemurs.model;


import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="id"))   
})
@Table(name = "shp_info")
public class ShpInfo extends BaseModel implements Serializable {

    @Column(name="shapetable")
    private String shapeTable;
    
    @Column(name="shapeprecision")
    private Double shapePrecision = 0.01;
    
    @Column(name="shapelabel")
    private String shapeLabel;
    
    @Column(name="nom_champ_gid")
    private String nomChampGid = "gid";
    
    @Column(name="nom_champ_geometrique")
    private String nomChampGeometrique = "geom";
    
    @Column(name="path")
    private String path;        

    public String getShapeTable() {
        return shapeTable;
    }

    public void setShapeTable(String shapeTable) {
        shapeTable = shapeTable.replace("-", "");
        this.shapeTable = shapeTable;
    }

    public Double getShapePrecision() {
        return shapePrecision;
    }

    public void setShapePrecision(Double shapePrecision) {
        this.shapePrecision = shapePrecision;
    }

    public String getShapeLabel() {
        return shapeLabel;
    }

    public void setShapeLabel(String shapeLabel) {
        this.shapeLabel = shapeLabel;
    }

    public String getNomChampGid() {
        return nomChampGid;
    }

    public void setNomChampGid(String nomChampGid) {
        this.nomChampGid = nomChampGid;
    }

    public String getNomChampGeometrique() {
        return nomChampGeometrique;
    }

    public void setNomChampGeometrique(String nomChampGeometrique) {
        this.nomChampGeometrique = nomChampGeometrique;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }            
    
}

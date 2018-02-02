/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wcs.lemurs.service.*;

/**
 *
 * @author rudyr
 */
@Component
public class PhotoService extends BaseService {    
    @Value( "${photo.dir}" )
    private String photoDir;

    public String getPhotoDir() {
        return photoDir;
    }

    public void setPhotoDir(String photoDir) {
        this.photoDir = photoDir;
    }
    
    public String getPhotoDirUrl() {
        return photoDir.replace("\\", "/");
    }
}

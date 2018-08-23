/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wcs.lemurs.service.ascii.ASCFileDrawer;
import org.wcs.lemurs.service.ascii.ASCFileReader;
import org.wcs.lemurs.service.ascii.ASCReaderProvider;
import org.wcs.lemurs.service.ascii.GMapsUtil;
import org.wcs.lemurs.service.ascii.GeoProjection;

/**
 *
 * @author Zacharie
 */
@RestController
public class AsciiController {

    private static final int MAX_COLOR = 0xFF0000;

    private static final int MIN_COLOR = 0xFF;

    private static final int TILE_WIDTH = 2048;//Not optimized, should change when the zoom change

    private static final int TILE_HEIGHT = 2048;//Not optimized, should change when the zoom change
    
    private static final double MADAGASCAR_OVERLAY_SOUTH = -31.952162238024968;
    private static final double MADAGASCAR_OVERLAY_WEST = 33.75;
    private static final double MADAGASCAR_OVERLAY_NORTH = -11.178401873711778;
    private static final double MADAGASCAR_OVERLAY_EAST = 56.25;    

    @RequestMapping(value = "/overlay", method = RequestMethod.GET, produces = "image/png")
    public byte[] observationModification(@RequestParam String f, HttpSession session) {

        ASCFileDrawer.ImageParams iParams = new ASCFileDrawer.ImageParams();
        iParams.width = TILE_WIDTH;
        iParams.height = TILE_HEIGHT;
        iParams.minColor = MIN_COLOR;
        iParams.maxColor = MAX_COLOR;                

        try {
            ASCFileReader asc = ASCReaderProvider.getReader(session.getServletContext().getRealPath("/")+"resources"+File.separator+"assets"+File.separator+"modele"+File.separator+"ascii"+File.separator+f);
            
            BufferedImage img = ASCFileDrawer.renderASCFile(asc, iParams,
                    GeoProjection.GMAPS_MERCATOR_PROJECTION, MADAGASCAR_OVERLAY_SOUTH, MADAGASCAR_OVERLAY_WEST, MADAGASCAR_OVERLAY_NORTH, MADAGASCAR_OVERLAY_EAST);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "png", bao);

            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

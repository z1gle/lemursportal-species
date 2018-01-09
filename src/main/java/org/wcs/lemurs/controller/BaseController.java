/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rudyr
 */
@Controller
public class BaseController {
    
    @RequestMapping(value="/")  
    public ModelAndView darwinportal(){
        return new ModelAndView("darwinportal");  
    }
    
    @RequestMapping(value="/taxonomi")  
    public ModelAndView taxoportal(){
        return new ModelAndView("taxonomiportal");  
    }
    
    @RequestMapping(value="/addDarwinCore")  
    public ModelAndView addDarwinCore(){
        return new ModelAndView("cruddwc");
    }
    
    @RequestMapping(value="/login")  
    public ModelAndView login(){
        return new ModelAndView("loginTemp");  
    }
}

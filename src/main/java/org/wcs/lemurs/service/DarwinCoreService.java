/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.DarwinCoreDao;
import org.wcs.lemurs.model.DarwinCore;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class DarwinCoreService {
    
    @Autowired(required = true)
    @Qualifier("darwinCoreDao")
    private DarwinCoreDao darwinCoreDao;
    
    @Transactional
    public void save() {}
    
    @Transactional
    public void update() {}
    
    @Transactional
    public void delete() {}
    
    public DarwinCore findById(int iddwc){
        return null;
    }
    
    public List<DarwinCore> findAll() {
        return null;
    }
}

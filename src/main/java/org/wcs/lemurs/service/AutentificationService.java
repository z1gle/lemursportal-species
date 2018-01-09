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
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.Utilisateur;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class AutentificationService {

    @Autowired(required = true)
    @Qualifier("hibernateDao")
    private HibernateDao hibernateDao;    

    public Utilisateur checkLogin(String login, String pw) throws Exception {
        Utilisateur u = new Utilisateur();
        u.setLogin(login);
        u.setMotdepasse(pw);
        List<Utilisateur> liste = (List<Utilisateur>)(List<?>)hibernateDao.findMultiCritere(u);
        if(liste.isEmpty())return null;
        return liste.get(0);
    }   
}

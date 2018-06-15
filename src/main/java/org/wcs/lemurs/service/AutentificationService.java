/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.Utilisateur;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class AutentificationService extends BaseService {

//    @Autowired(required = true)
//    @Qualifier("hibernateDao")
//    private HibernateDao hibernateDao;    
    public Utilisateur checkLogin(String login, String pw) throws Exception {
        Utilisateur u = new Utilisateur();
        u.setEmail(login);
        u.setMotdepasse(pw);
        List<Utilisateur> liste = (List<Utilisateur>) (List<?>) findMultiCritere(u);
        if (liste.isEmpty()) {
            return null;
        }
        return liste.get(0);
    }

    public static String encrypte(String id) {
        Random rand = new Random();
        String step1 = RandomStringUtils.randomAlphanumeric(rand.nextInt(20));
        String step2 = RandomStringUtils.randomAlphanumeric(rand.nextInt(20));
//        String step3 = step1 + "!_!" + id + "!_!" + "321addceeecs!jjdislu" + "!_!" + step2;
//        return enc(step3);
        return step1 + "!_!" + enc(id) + "!_!" + "321addc" + RandomStringUtils.randomAlphanumeric(rand.nextInt(20)) + "cs!jjdislu" + "!_!" + step2;
    }

    public Integer decrypte(String code) {
//        String decode = dec(code);
        String decode = code;
        String[] liste = decode.split("!_!");        
        String id = liste[1];        
        return new Integer(dec(id));
    }

    private static String enc(String string) {
        String seed = "lemursPortalMakeOneSessionForAll";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(seed);
        return encryptor.encrypt(string);
    }

    private String dec(String string) {
        String seed = "lemursPortalMakeOneSessionForAll";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(seed);
        return encryptor.decrypt(string);
    }
}

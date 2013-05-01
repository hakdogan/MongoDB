/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hakdogan
 */

public class PersistenceManager {
    
    private static PersistenceManager instance = null;
    private static Object lock                 = new Object();
    private EntityManagerFactory factory;
    
    
    public PersistenceManager() {
        System.out.println("PersistenceManager init()");
        factory = Persistence.createEntityManagerFactory("kodcuMongo");
    }
    
    public static PersistenceManager instance(){
        
        if(instance == null) { 
            synchronized (lock) {
                if(null == instance){
                    instance = new PersistenceManager();
                }
            }
        }
        return instance;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }
    
    
    public void printThis() {
        System.out.println(this);
    }
    
}

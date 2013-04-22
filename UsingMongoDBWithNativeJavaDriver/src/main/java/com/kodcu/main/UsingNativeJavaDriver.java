/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.main;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hakdogan
 */
public class UsingNativeJavaDriver {
    
    public static void main(String args[]){
        
        try {
            
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db                   = mongoClient.getDB("kodcu");
            DBCollection collection = db.getCollection("articles");
            
            dropCollection(collection);
            
            insertRecord(new String[] {"Yazilar"}, 
                         "Lean Kanban Atolye Calismasi", 
                         "Kanban sistemi son 10 yildir yazilim yonetimi alaninda etkiliyici bir yaklasimdir.", 
                         new Date(), 
                         "Altug Bilgin Altintas", 
                         new String[] {null}, 
                         collection);
            
            insertRecord(new String[] {"Yazilar"}, 
                         "Backbone.js ile ipleri elinize alin! – Ucretsiz Webiner", 
                         "Backbone.js, karmasaya bogulmus JavaScript kodunuzu bir yapi icerisine sokan ve gelistirimi zevkli bir ortam sunan bir JavaScript kutuphanesidir.", 
                         new Date(), 
                         "Kodcu.Com", 
                         new String[] {null}, 
                         collection);
            
            insertRecord(new String[] {"Java", "Tutorial", "Yazilar", "Yazilim"}, 
                         "Java ile Apache Solr'a Veri Indeksleme", 
                         "Merhabalar, bir onceki yazimda Apache Solr kurulumundan, konfigurasyonundan, komut satirindan Solr'a veri indeksleme ve bu veriler uzerinden sorgu yapabilme islemlerinden bahsetmistim.", 
                         new Date(), 
                         "Cuneyt Yesilkaya", 
                         new String[] {"java", "java ile solr'a veri indekslemek", "solr", "solrj"}, 
                         collection);
            
            updateRecord("title", "Lean Kanban Atolye Calismasi", "tags", new String[]{"lean", "kanban", "egitim"}, collection);
            
            DBCursor cursor = findRecord("author", "Altug Bilgin Altintas", collection);
            while (cursor.hasNext()) {
                System.out.println("Bulunan kayitlar: " + cursor.next());
            }
        
            executeRegexQuery("content", "Backbone.*JavaScript", collection);
            
            findAllRecords(collection);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsingNativeJavaDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void insertRecord(String[] category, String title, String content,
                                    Date date, String author, String[] tags, DBCollection collection){
        
        BasicDBObject document = new BasicDBObject("category", category)
                                .append("title", title)
                                .append("content", content)
                                .append("date", date)
                                .append("author", author)
                                .append("tags", tags);
        
        collection.insert(document);
        
        System.out.println("Eklenen kayit: " + document);
        
        
    }
    
    public static void updateRecord(String findField, String findFieldNewValue, String updatedField, String[] updatedFieldNewValue, DBCollection collection){
        
        BasicDBObject query = new BasicDBObject();
	query.put(findField, findFieldNewValue);
        
	BasicDBObject findDocument = new BasicDBObject();
	findDocument.put(updatedField, updatedFieldNewValue);
 
	BasicDBObject updatedDocument = new BasicDBObject();
	updatedDocument.put("$set", findDocument);
 
	collection.update(query, updatedDocument);       
        System.out.println("Kayit guncellendi: " + updatedDocument);
    
    }
    
    public static void findAllRecords(DBCollection collection){
        
        System.out.println("***************** Butun kayitlari listele *****************");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("***************** Butun kayitlari listele *****************");
    }
    
    public static DBCursor findRecord(String field, String value, DBCollection collection){
        
        BasicDBObject query = new BasicDBObject();
	query.put(field, value);
        DBCursor dBCursor = collection.find(query);
        
        return dBCursor;
    }
    
    public static void executeRegexQuery(String field, String value, DBCollection collection){
        
        BasicDBObject query = new BasicDBObject();
        query.put(field, new BasicDBObject("$regex", "Backbone.*JavaScript").append("$options", "i"));
        DBCursor cursor = collection.find(query);
        
        System.out.println("***************** Regex Query *****************");
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("***************** Regex Query *****************");
        
    }
    
    public static void dropCollection(DBCollection collection){
        
        collection.drop();
        System.out.println("Kolleksiyon sifirlandi");
    }
}

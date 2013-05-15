/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.main;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hakdogan
 */
public class UsingNativeJavaDriver {
    
    public static void main(String args[]) {
        
        try {
            
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db                   = mongoClient.getDB("kodcu");
            DBCollection collection = db.getCollection("webinar");
            
            dropCollection(collection);
            
            System.out.println("***************** insertRecord *****************");
            insertRecord("PHP", "Hüseyin Akdoğan", "15/07/2011 20:00:00", 2, collection);
            insertRecord("Spring Core", "Altuğ Bilgin Altıntaş", "20/11/2012 21:00:00", 2, collection);
            insertRecord("Spring Core", "Altuğ Bilgin Altıntaş", "22/11/2012 21:00:00", 2, collection);
            insertRecord("JSF", "Hüseyin Akdoğan", "15/03/2012 14:00:00", 2, collection);
            insertRecord("JSF 2.0", "Hüseyin Akdoğan", "10/08/2012 14:00:00", 2, collection);
            insertRecord("RestFull", "Rahman Usta", "10/02/2012 17:00:00", 1, collection);
            insertRecord("Lean & Kanban & Agile", "Altuğ Bilgin Altıntaş", "10/01/2013 15:00:00", 1, collection);
            insertRecord("Backbone.js", "Rahman Usta", "17/04/2013 16:30:00", 1, collection);
            insertRecord("NoSQL", "Hüseyin Akdoğan", "15/05/2013 16:00:00", 1, collection);
            System.out.println("***************** insertRecord *****************");
            
            whereQuery("subject", "NoSQL", collection);
            whereQuery("speaker", "Rahman Usta", collection);
            
            groupQuery("Hüseyin Akdoğan", collection);
            updateRecord("subject", "JSF", "subject", "Basic JSF", collection);
        
            executeRegexQuery("subject", "lean.*agile", collection);
            
            //removeRecord("subject", "Basic JSF", collection);
            //findAllRecords(collection);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsingNativeJavaDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void insertRecord(String konu, String konusmaci, String tarih, int sure, DBCollection collection) {
        
        try {
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            BasicDBObject document      = new BasicDBObject("subject", konu)
                                        .append("speaker", konusmaci)
                                        .append("date", dateFormat.parse(tarih))
                                        .append("time", sure);
            
            collection.insert(document);    
            System.out.println("Added entry: " + document);
        
        } catch (ParseException ex) {
            Logger.getLogger(UsingNativeJavaDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateRecord(String findField, String findFieldValue, String updatedField, String updatedFieldNewValue, DBCollection collection){
        
        System.out.println("\n***************** updateRecord *****************");
        
        BasicDBObject newDocument = new BasicDBObject();
	    newDocument.put(updatedField, updatedFieldNewValue);
 
        BasicDBObject updatedDocument = new BasicDBObject();
        updatedDocument.put("$set", newDocument);

        System.out.println("Updated records: " + collection.update(findDocument(findField, findFieldValue), updatedDocument, false, true).getN());
        System.out.println(updatedDocument);
        System.out.println("***************** updateRecord *****************");
    }
    
    public static void removeRecord(String findField, String findFieldValue, DBCollection collection){
        collection.remove(findDocument(findField, findFieldValue));
    }
    
    public static BasicDBObject findDocument(String findField, String findFieldValue){
        BasicDBObject document = new BasicDBObject();
	    document.put(findField, findFieldValue);
        
        return document;
    }
    
    public static void executeRegexQuery(String field, String value, DBCollection collection){
        
        System.out.println("\n***************** executeRegexQuery *****************");
        BasicDBObject query = new BasicDBObject();
        query.put(field, new BasicDBObject("$regex", value).append("$options", "i"));
        DBCursor cursor = collection.find(query);
        
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("***************** executeRegexQuery *****************");
        
    }
    
    public static void whereQuery(String field, String value, DBCollection collection){
        
        DBObject math   = new BasicDBObject("$match", new BasicDBObject(field, value));
        DBObject fields = new BasicDBObject("subject", 1);
        
        fields.put("_id", 0);
        fields.put("speaker", 1);
        fields.put("date", 1);
        fields.put("time", 1);
        
        DBObject project = new BasicDBObject("$project", fields);
        
        System.out.println("\n****************** whereQuery ******************");
        AggregationOutput output = collection.aggregate(math, project);
        for(DBObject obj :output.results()){
            System.out.println(obj);
        }
        System.out.println("****************** whereQuery ******************");
    }
    
    public static void groupQuery(String konusmaci, DBCollection collection){
        
        DBObject math   = new BasicDBObject("$match", new BasicDBObject("speaker", konusmaci));
        DBObject fields = new BasicDBObject("subject", 1);
        
        fields.put("_id", 0);
        fields.put("time", 1);
        
        DBObject project = new BasicDBObject("$project", fields );
        
        DBObject groupFields = new BasicDBObject("_id", "$subject");
        groupFields.put("Average time", new BasicDBObject( "$avg", "$time"));
        DBObject group = new BasicDBObject("$group", groupFields);
        
        AggregationOutput output = collection.aggregate(math, project, group);
        
        System.out.println("\n****************** groupQuery ******************");
        for(DBObject obj : output.results()){
            System.out.println(obj);
        }
	System.out.println("****************** groupQuery ******************");
    }
    
    public static void findAllRecords(DBCollection collection){
        
        System.out.println("***************** findAllRecords *****************");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("***************** findAllRecords *****************");
    }
    
    public static void dropCollection(DBCollection collection){
        
        collection.drop();
        System.out.println("Collection is reset\n");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author hakdogan
 */

@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Article implements Serializable {

    public Article() { 
    }
    
    @Id
    @GeneratedValue
    @Field(name="_id")
    private String id;
    
    @ElementCollection
    private List<Categories> categoryLists = new ArrayList<Categories>();
    
    @Basic
    private String title;
    
    @Basic
    private String content;
    
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @Basic
    private String author;
    
    @ElementCollection
    private List<Tags> tagLists = new ArrayList<Tags>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Categories> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(List<Categories> categoryLists) {
        this.categoryLists = categoryLists;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }
//530 951 18 34
    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Tags> getTagLists() {
        return tagLists;
    }

    public void setTagLists(List<Tags> tagLists) {
        this.tagLists = tagLists;
    }
}

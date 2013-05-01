/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author hakdogan
 */

@Embeddable
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Categories implements Serializable {
    
    @Basic
    private String category;

    public Categories() {
    }

    public Categories(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
        
}

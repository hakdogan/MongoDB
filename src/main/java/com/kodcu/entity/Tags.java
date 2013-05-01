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
public class Tags implements Serializable {

    @Basic
    private String tag;
    
    public Tags() {
    }

    public Tags(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
   
}

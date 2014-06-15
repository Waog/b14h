/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b14h.model;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Entity;

/**
 *
 * @author aabarb
 */
@Entity(name="Parent")
public class Parent {
    @Id private int parentId;
    private int dispo;
    private String preapprovalKey;
    
    public int getDispo() {
       return this.dispo;
   }
    
    public String getPreapprovalKey() {
        return this.preapprovalKey;
    }   
    
    public Parent() {
        this.dispo = 0;
        this.preapprovalKey="";
    }
            
    public void setPreapprovalKey(String key) {
        this.preapprovalKey = key;
    }
    
}

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
@Entity(name="child")
public class Child {
    
   private int blubs;
   @Id private int childId;
   
   public int getBlubs() {
       return this.blubs;
   }
   
   public Child() {
       this.blubs = 0;
   }
   
   public void updateBlubs(int amount) {
       this.blubs += amount;
       if (this.blubs < 0) this.blubs = 0;
   }
}

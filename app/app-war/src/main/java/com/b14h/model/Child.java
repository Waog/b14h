/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b14h.model;

import java.util.ArrayList;

/**
 *
 * @author aabarb
 */
public class Child {
    
   private int blubs;
   private int parentId;
   private int childId;
   private ArrayList<Task> tasks;
   
   public int getBlubs() {
       return this.blubs;
   }
   
   public int getParentId() {
       return this.parentId;
   }
   
   public Child getByChildId(int childId) {
       // TODO: get entry from DB
       return new Child(1);
   }
   
   public Child(int parentId) {
       this.blubs = 0;
       this.parentId = parentId; 
       this.tasks = new ArrayList<Task>();
       // TODO: db query to create a new entry in the Child table returns childKey
   }
   
   public void save() {
       //TODO: query to save the entry in the DB
   }
   
   public void updateBlubs(int amount) {
       this.blubs += amount;
       if (this.blubs < 0) this.blubs = 0;
   }
}

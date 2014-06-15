/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b14h.model;

import com.b14h.libs.Constants.TaskStatus;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Entity;

/**
 *
 * @author aabarb
 */
@Entity(name="task")
public class Task {
    
    private final String description;
    private final int credit;
    @Id private long taskId;
    private final String title;
    private TaskStatus status;
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getCredit() {
        return this.credit;
    }
    
    public TaskStatus getStatus() {
        return this.status;
    }        
    
    public Task(String title, String description, int credit) {
        this.title = title;
        this.description = description;
        this.credit = credit;
    }
    
    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }    
}

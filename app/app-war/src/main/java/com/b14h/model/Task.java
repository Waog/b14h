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
    private final int parentId;
    private final int childId;
    @Id private long taskId;
    private final String title;
    private TaskStatus status;
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getParentId() {
        return this.parentId;
    }
    
    public int getCredit() {
        return this.credit;
    }
     
    public int getChildId() {
        return this.childId;
    }
    
    public TaskStatus getStatus() {
        return this.status;
    }
    
    public Task getByTaskId(int taskId) {
        //TODO; get entry from DB
        return new Task("Task", "Do the dishes", 1, 1, 1);
    }
        
    
    public Task(String title, String description, int credit, int parentId, int childId) {
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.parentId = parentId;
        this.childId = childId;        
    }
    
    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }    
}

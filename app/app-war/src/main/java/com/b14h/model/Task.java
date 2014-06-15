/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity(name = "task")
public class Task {

    private final String description;
    private final int credit;
    private final String title;
    @Id
    private long taskId;
    private TaskStatus status;

    public Task(String title, String description, int credit) {
        this.title = title;
        this.description = description;
        this.credit = credit;
    }

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

    public void setStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }

    public enum TaskStatus {
        OPEN,
        CLOSED,
        CONFIRMED
    }
}

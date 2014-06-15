package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity(name = "Parent")
public class Parent {
    @Id
    private Long parentId;
    private int dispo;
    private String preapprovalKey;

    public Parent() {
        this.dispo = 0;
        this.preapprovalKey = "";
    }

    public int getDispo() {
        return this.dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    public String getPreapprovalKey() {
        return this.preapprovalKey;
    }

    public void setPreapprovalKey(String key) {
        this.preapprovalKey = key;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}

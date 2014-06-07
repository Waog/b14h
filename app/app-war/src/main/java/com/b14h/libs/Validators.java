package com.b14h.libs;

import com.google.appengine.api.datastore.Entity;

public class Validators {

    public static boolean validateTask(Entity task){
        return (task.getProperty("title")!= null &&
                !task.getProperty("title").toString().isEmpty() &&
                task.getProperty("credit") != null &&
                !task.getProperty("credit").toString().isEmpty()
        );
    }
}

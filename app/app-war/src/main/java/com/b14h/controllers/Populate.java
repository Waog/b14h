package com.b14h.controllers;

import com.b14h.libs.Constants;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Populate extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = "my Task title ";
        String desc = "my Task desc ";
        List<Entity> taskCollection = new ArrayList<Entity>();

        for (int i = 0; i < 10; i++) {
            Entity task = new Entity(Constants.TASK_ENTITY);
            task.setProperty("Title", title + Integer.toString(i));
            task.setProperty("Description", desc + Integer.toString(i));
            task.setProperty("Description", desc + Integer.toString(i));
            task.setProperty("Credit", 10 + i);
            task.setProperty("state", Constants.TASK_OPEN);
            taskCollection.add(task);
        }
        datastore.put(taskCollection);
        resp.getWriter().write("OK");
    }
}

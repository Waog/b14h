package com.b14h.controllers;

import com.b14h.libs.Constants;
import com.b14h.libs.Constants.TaskStatus;
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

public class PopulateServlet extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String title = "my Task title ";
        String desc = "Compiles JavaScript templates into functions that can be evaluated for rendering. Useful for "
                + "rendering complicated bits of HTML from JSON data sources. Template functions can both interpolate "
                + "variables, using <%= … %>, as well as execute arbitrary JavaScript code";

        List<Entity> taskCollection = new ArrayList<Entity>();

        for (int i = 0; i < 10; i++) {
            Entity task = new Entity(Constants.TASK_ENTITY);
            task.setProperty("title", title + Integer.toString(i));
            task.setProperty("description", desc + Integer.toString(i));
            task.setProperty("credit", 10 + i);
            task.setProperty("state", TaskStatus.OPEN);
            taskCollection.add(task);
        }
        datastore.put(taskCollection);
        resp.getWriter().write("OK");
    }
}

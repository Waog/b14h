package com.b14h.controllers;


import com.b14h.libs.Constants;
import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Task extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Query q = new Query(Constants.TASK_ENTITY);
        List<Entity> tasks = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        Gson json = new Gson();
        resp.setContentType("application/json");
        resp.getWriter().write(json.toJson(tasks));
    }
}

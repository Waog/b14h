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

    /**
     * Get Task list
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Query q = new Query(Constants.TASK_ENTITY);
        List<Entity> tasks = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        Gson json = new Gson();
        resp.setContentType("application/json");
        resp.getWriter().write(json.toJson(tasks));
    }

    /**
     * Update task
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Key key = KeyFactory.stringToKey(req.getParameter("key"));
        String state = req.getParameter("state");
        Entity task = null;

        try {
            task = datastore.get(key);
            task.setProperty("state", state);
            datastore.put(task);
        } catch (EntityNotFoundException e) {}

    }

    /**
     * Append task
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Entity task = new Entity(Constants.TASK_ENTITY);
        task.setProperty("title",  req.getParameter("title"));
        task.setProperty("description",  req.getParameter("description"));
        task.setProperty("credit", req.getParameter("credit"));
        task.setProperty("state", Constants.TASK_OPEN);
        datastore.put(task);
    }

    /**
     * Delete task
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Key key = KeyFactory.stringToKey(req.getParameter("key"));
        datastore.delete(key);
    }
}

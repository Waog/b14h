package com.b14h.controllers;

import com.b14h.libs.Constants;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

public class Task extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Get Task list
     * 
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Query q = new Query(Constants.TASK_ENTITY);
        List<Entity> tasks = datastore.prepare(q).asList(withLimit(50));
        Gson json = new Gson();
        resp.setContentType("application/json");
        resp.getWriter().write(json.toJson(tasks));
    }

    /**
     * Update task
     * 
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Key key = KeyFactory.createKey(Constants.TASK_ENTITY,
                Long.parseLong(req.getParameter("id")));

        String state = req.getParameter("state");

        try {
            Entity task = datastore.get(key);
            task.setProperty("state", state);
            datastore.put(task);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     * Append task
     * 
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        Entity task = new Entity(Constants.TASK_ENTITY);
        task.setProperty("title", req.getParameter("title"));
        task.setProperty("description", req.getParameter("description"));
        task.setProperty("credit", req.getParameter("credit"));
        task.setProperty("state", Constants.TASK_OPEN);
        datastore.put(task);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    /**
     * Delete task
     * 
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Key key = KeyFactory.createKey(Constants.TASK_ENTITY,
                Long.parseLong(req.getParameter("id")));
        datastore.delete(key);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}

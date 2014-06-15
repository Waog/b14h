package com.b14h.controllers;

import com.b14h.libs.Constants;
import com.b14h.libs.Constants.TaskStatus;
import com.b14h.libs.Validators;
import com.b14h.model.Task;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskServlet extends HttpServlet {

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
         Query q = new Query(Constants.TASK_ENTITY).addSort("state",
                 Query.SortDirection.DESCENDING);
        List<Entity> tasks = datastore.prepare(q).asList(withLimit(50));
        Gson json = new Gson();
        resp.setContentType("application/json; charset=UTF-8");
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
                Long.parseLong(req.getParameter("taskId")));

        String state = req.getParameter("status");

        try {
            Entity task = datastore.get(key);
            task.setProperty("status", state);
            datastore.put(task);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e.getMessage());
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
        task.setProperty("childId", req.getParameter("childId"));
        task.setProperty("parentId", req.getParameter("parentId"));
        task.setProperty("status", TaskStatus.OPEN.ordinal());

        if (!Validators.validateTask(task)) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            resp.getWriter().print("Task not valid");
        } else {
            datastore.put(task);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
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
                Long.parseLong(req.getParameter("taskId")));
        datastore.delete(key);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


}

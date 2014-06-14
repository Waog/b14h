package com.b14h.controllers;

import AdaptivePaymentsService.java;
import com.b14h.libs.Constants;
import com.b14h.libs.Validators;
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

public class Preapproval extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Get Preapproval redirect URL.
     * 
     * @param req HttpServletRequest should contain the parent Paypal ID in
     *            future versions.
     * @param resp HttpServletResponse contains redirect URL to paypal with the
     *            preapproval key.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // super.doGet(req, resp);
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write("Hello Preapproval!");

        Map<String, String> customConfigurationMap = new HashMap<String, String>();
        customConfigurationMap.put("mode", "sandbox"); // Load the map with all
                                                       // mandatory parameters
        AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(Map < String,
                String > customConfigurationMap);
        
        PreapprovalRequest preapprovalRequest = new PreapprovalRequest();
        
        PayResponse payResponse = adaptivePaymentsService.preapproval(preapprovalRequest);
    }
}

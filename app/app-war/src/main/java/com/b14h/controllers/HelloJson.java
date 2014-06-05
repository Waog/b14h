package com.b14h.controllers;

/**
 * Test controller
 */

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloJson extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson json = new Gson();
        String[] strings = {"Hello", "Json"};
        resp.getWriter().print(json.toJson(strings));
    }
}

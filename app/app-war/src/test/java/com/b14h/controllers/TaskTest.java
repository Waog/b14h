package com.b14h.controllers;

import com.b14h.libs.Constants;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TaskTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

    private Task servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter response_writer;
    private Map<String, String> parameters;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ds.put(new Entity(Constants.TASK_ENTITY));

        parameters = new HashMap<String, String>();
        servlet = new Task();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        response_writer = new StringWriter();

        when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocation) {
                return parameters.get(invocation.getArguments()[0]);
            }
        });
        when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void testDoGet() throws Exception {
        servlet.doGet(request, response);
        Assert.assertEquals(response_writer.toString(), "[{\"key\":{\"kind\":\"Task\",\"id\":1},\"propertyMap\":{}}]");
    }

    @Test
    public void testDoPost() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("id", "1");
        parameters.put("state", "1");
        servlet.doPost(request, response);
        Key key = KeyFactory.createKey(Constants.TASK_ENTITY, 1);
        Entity task = ds.get(key);

        Assert.assertEquals(task.getProperty("state"), "1");
    }

    @Test
    public void testDoPut() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("title", "t");
        parameters.put("description", "d");
        parameters.put("description", "10");
        servlet.doPut(request, response);
        Query q = new Query(Constants.TASK_ENTITY);
        List<Entity> tasks = ds.prepare(q).asList(withLimit(10));

        Assert.assertEquals(tasks.size(), 2);
    }

    @Test
    public void testDoDelete() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("id", "1");
        servlet.doDelete(request, response);
        Query q = new Query(Constants.TASK_ENTITY);
        List<Entity> tasks = ds.prepare(q).asList(withLimit(10));

        Assert.assertEquals(tasks.size(), 0);
    }
}
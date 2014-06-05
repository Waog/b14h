b14h.app
=============================

Install maven 3
http://maven.apache.org/download.cgi

Build
-----

    cd app
    mvn clean install


Run dev server
--------------

    cd app/app-ear
    mvn appengine:devserver


Prepopulate data
----------------

    http://127.0.0.1:8080/api/populate
    
 
Fetch all tasks
---------------

    http://127.0.0.1:8080/api/task


HTML Consumers (clients)
------------------------
 
    http://127.0.0.1:8080/html/parent
    http://127.0.0.1:8080/html/child
    http://127.0.0.1:8080/html/store
    

Admin console
-------------
    
    http://localhost:8080/_ah/admin
    
<%-- 
    Document   : albumcreateview
    Created on : Jun 18, 2024, 10:39:17 AM
    Author     : diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.Album"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Album</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <div class="container">
        <h1>Create Album</h1>
        <%
            session.setAttribute("CodServAlbum", "1");
            String select = (String) request.getAttribute("select");
            if(select != null){
        %>

        <form action="AlbumServlet" method="post">
            <input type="hidden" name="select" value="1">
            <label>Title:</label>
            <input type="text" name="title" required><br>

            <label>Release Year:</label>
            <input type="text" name="release_year" required><br>

            <button type="submit">Create Album</button>
        </form>
        
        <%
            }else{
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        %>
    </div>
</body>
</html>

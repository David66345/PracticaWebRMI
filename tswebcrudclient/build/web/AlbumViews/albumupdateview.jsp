<%-- 
    Document   : albumupdateview
    Created on : Jun 18, 2024, 10:39:43 AM
    Author     : diego
--%>

<%@page import="Models.Album"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Album</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <div class="container">
        <h1>Update Album</h1>
        
        <%
            session.setAttribute("CodServAlbum", "3");
            String select = (String) request.getAttribute("select");
            Album album = (Album) request.getAttribute("AlbumToUpdate");
            if(album != null && select!=null){
        %>
        
        <form action="AlbumServlet" method="post">
            <input type="hidden" name="select" value="3">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            
            <label>ID: <%= album.getId() %></label><br>
            <label>Title:</label>
            <input type="text" name="title" value="<%= album.getTitle() %>"><br>
            
            <label>Release Year:</label>
            <input type="text" name="release_year" value="<%= album.getRelease_year() %>"><br>
            
            <button type="submit">Update Album</button>
        </form>
            
        <%
            } else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        %>
    </div>
</body>
</html>


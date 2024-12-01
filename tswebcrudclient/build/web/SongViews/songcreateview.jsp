<%-- 
    Document   : songcreateview
    Created on : Jun 18, 2024, 10:41:51 AM
    Author     : diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.Album"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles/style.css">
    </head>
    <body>
        <div class="container">
            <h1>Create Song</h1>
            <%
                session.setAttribute("CodServSong", "1");
                String select = (String) request.getAttribute("select");
                ArrayList<Album> albums = (ArrayList<Album>) request.getAttribute("Albums");
            if (albums != null && !albums.isEmpty() && select!=null) {
            %>

            <form action="SongServlet" method="post">
                <input type="hidden" name="select" value="1">

                <label>Title:</label>
                <input type="text" name="title" required><br>

                <label>Duration:</label>
                <input type="text" name="duration" required><br>

                <label>Album:</label>
                <select name="album_id" required>
                    <option value="">Select Album</option>
                    <%
                        for (Album album : albums) {
                    %>
                    <option value="<%= album.getId() %>"><%= album.getTitle() %></option>
                    <%
                        }
                    %>
                </select><br>

                <button type="submit">Create Song</button>
            </form>

            <%
            } else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }

        %>
        </div>
        
    </body>
</html>

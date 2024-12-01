<%-- 
    Document   : songupdateview
    Created on : Jun 18, 2024, 10:42:33 AM
    Author     : diego
--%>

<%@page import="Models.Song"%>
<%@page import="Models.Album"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Song</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <div class="container">
        <h1>Update Song</h1>

        <%
            session.setAttribute("CodServSong", "3");
            String select = (String) request.getAttribute("select");
            Song song = (Song) request.getAttribute("SongToUpdate");
            ArrayList<Album> albums = (ArrayList<Album>) request.getAttribute("Albums");
            
            if(song != null && albums != null && !albums.isEmpty() && select!=null){
        %>

        <form action="SongServlet" method="post">
            <input type="hidden" name="select" value="3">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">

            <label>ID: <%= song.getId() %></label><br>
            <label>Title:</label>
            <input type="text" name="title" value="<%= song.getTitle() %>"><br>

            <label>Duration:</label>
            <input type="text" name="duration" value="<%= song.getDuration() %>"><br>

            <label>Album:</label>
                <select name="album_id" required>
                    <option value="">Select Song</option>
                    <%
                        for (Album album : albums) {
                    %>
                    <option value="<%= album.getId() %>"><%= album.getTitle() %></option>
                    <%
                        }
                    %>
                </select><br>

            <button type="submit">Update Song</button>
        </form>

        <%
            } else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }

        %>

    </div>

</body>
</html>

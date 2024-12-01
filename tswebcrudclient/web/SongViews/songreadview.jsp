<%-- 
    Document   : songreadview
    Created on : Jun 18, 2024, 10:41:29 AM
    Author     : diego
--%>

<%@page import="Models.Song"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <div class="container">
        <h1>Song Read View</h1>

        <form action="SongServlet" method="post">
            <input type="hidden" name="select" value="2">
            <input type="hidden" name="action" value="create">
            <input type="hidden" name="id" value="0">
            <button type="submit">Create Song</button>
        </form>
        <%
            session.setAttribute("CodServSong", "4");
            String select = (String) request.getAttribute("select");
            ArrayList<Song> songs = (ArrayList<Song>) request.getAttribute("Songs");
            if(select!= null){
                if (songs != null) {
        %>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Album ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%                    
                    for (Song song : songs) {
                %>
                <tr>
                    <td><%= song.getId()%></td>
                    <td><%= song.getTitle()%></td>
                    <td><%= song.getDuration()%></td>
                    <td><%= song.getTitleById(song.getAlbum_id())%></td>
                    <td>
                        <form action="SongServlet" method="post">
                            <input type="hidden" name="select" value="3">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%= song.getId()%>">
                            <button type="submit">Update</button>
                        </form>
                        <form action="SongServlet" method="post">
                            <input type="hidden" name="select" value="4">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= song.getId()%>">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            } else {
        %>
        
        <h1>Songs Empty</h1>
        
        <%
            }
        } else {
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        %>
    </div>
</body>
</html>

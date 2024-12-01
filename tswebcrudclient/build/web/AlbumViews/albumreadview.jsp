<%@page import="Models.Album"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Album Read View</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
    <div class="container">
        <h1>Album Read View</h1>

        <form action="AlbumServlet" method="post">
            <input type="hidden" name="select" value="2">
            <input type="hidden" name="action" value="create">
            <input type="hidden" name="id" value="0">
            <button type="submit">Create Album</button>
        </form>
        
        <%
            session.setAttribute("CodServAlbum", "4");
            String select = (String) request.getAttribute("select");
            ArrayList<Album> albums = (ArrayList<Album>) request.getAttribute("Albums");
            if(select!=null){
                if (albums != null) {
        %>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Release Year</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%                    
                    for (Album album : albums) {
                %>
                <tr>
                    <td><%= album.getId()%></td>
                    <td><%= album.getTitle()%></td>
                    <td><%= album.getRelease_year()%></td>
                    <td>
                        <form action="AlbumServlet" method="post">
                            <input type="hidden" name="select" value="3">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%= album.getId()%>">
                            <button type="submit">Update</button>
                        </form>
                        <form action="AlbumServlet" method="post">
                            <input type="hidden" name="select" value="4">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= album.getId()%>">
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
        <h1>Album Empty</h1>
        
        <%
            } 
        }else {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        %>
    </div>
</body>
</html>

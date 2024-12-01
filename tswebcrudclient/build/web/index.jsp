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
            <h1>Welcome to the Music App</h1>
    
            <form action="SongServlet" method="post">
                <%
                    session.setAttribute("CodServSong","2");
                %>
                <input type="hidden" name="select" value="1">
                <button type="submit">Go to Song Read View</button>
            </form>

            <form action="AlbumServlet" method="post">
                <%
                    session.setAttribute("CodServAlbum","2");
                %>
                <input type="hidden" name="select" value="2">
                <button type="submit">Go to Album Read View</button>
            </form>
        </div>
</body>
</html>

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package crudservlets;

import Models.Album;
import Models.Song;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tswebcrudinterface.Tswebcrudinterface;
import tswebcrudinterface.Tswebcrudinterface.AlbumControllerInterface;
import tswebcrudinterface.Tswebcrudinterface.SongControllerInterface;

@WebServlet(name = "SongServlet", urlPatterns = {"/SongServlet"})
public class SongServlet extends HttpServlet {
    private SongControllerInterface controllerSong;

    @Override
    public void init() throws ServletException {
        super.init();
        Registry r;
        try {
            r = LocateRegistry.getRegistry("192.168.100.192", 1234);
            controllerSong = (SongControllerInterface) r.lookup("SongControllerInterface");
        } catch (RemoteException ex) {
            Logger.getLogger(SongServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SongServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        response.setContentType("text/html;charset=UTF-8");
        
        String codigo = (String) request.getSession().getAttribute("CodServSong");
        String select = request.getParameter("select");
        String action = request.getParameter("action");
        String idSong = request.getParameter("id");
        
        if (codigo == null || select == null) {
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            System.out.println("El codigo es: "+codigo);
            System.out.print(controllerSong.suma(1, 2));
            
            out.print("<h1> hola </h1>");
            out.print("<h1>" + codigo + "</h1>");
            switch (codigo) {
                case "1":
                    createSong(request, response);
                    break;
                case "2":                    
                    readSongs(request, response);
                    break;
                case "3":
                    updateSong(request, response);
                    break;
                case "4":
                    if (action == null || idSong == null) {
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                        return;
                    }
                    ArrayList<Album> albums = controllerSong.getAllAlbum();
                    Song song;
                    RequestDispatcher dispatcher;
                    if(action.equals("create")){
                        request.setAttribute("select", "1");
                        request.setAttribute("Albums", albums);
                        dispatcher = request.getRequestDispatcher("/SongViews/songcreateview.jsp");
                        dispatcher.forward(request, response);
                    }
                    
                    if(action.equals("update")){
                        request.setAttribute("select", "3");
                        request.setAttribute("Albums", albums);
                        song = controllerSong.getSongById(Integer.parseInt(idSong));
                        request.setAttribute("SongToUpdate", song);
                        dispatcher = request.getRequestDispatcher("/SongViews/songupdateview.jsp");
                        dispatcher.forward(request, response);
                    }
                    
                    if(action.equals("delete")){
                        request.setAttribute("select", "4");
                        song = controllerSong.getSongById(Integer.parseInt(idSong));
                        controllerSong.delete(song);
                        
                        Cookie lastDeletedSongCookie = new Cookie("lastDeleteSongId", idSong);
                        lastDeletedSongCookie.setMaxAge(60 * 60 * 24); // Duración de la cookie en segundos (1 día)
                        response.addCookie(lastDeletedSongCookie);
                        
                        readSongs(request, response);
                    }
                    break;
                default:
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                    return;
            }
        }
    }
    
    private void readSongs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException{
        ArrayList<Song> songs = controllerSong.getAll();
        request.setAttribute("select", "2");
        if (!songs.isEmpty()) {
            System.out.print(songs.get(0).getTitle());
            request.setAttribute("Songs", songs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SongViews/songreadview.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.print("No songs available.");
            request.setAttribute("Songs", songs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SongViews/songreadview.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void updateSong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String duration = request.getParameter("duration");
        String album_id = request.getParameter("album_id");

        Song songUpdate = new Song(Integer.parseInt(id), title, Integer.parseInt(duration), Integer.parseInt(album_id));
        controllerSong.update(songUpdate);
        request.setAttribute("select", "3");
        readSongs(request, response);
    }
    
    private void createSong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        String title = request.getParameter("title");
        String duration = request.getParameter("duration");
        String album_id = request.getParameter("album_id");

        Song songUpdate = new Song(title, Integer.parseInt(duration), Integer.parseInt(album_id));
        controllerSong.add(songUpdate);
        readSongs(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException {
        try {
            processRequest(request, response);
        } catch (NotBoundException ex) {
            Logger.getLogger(SongServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException {
        try {
            processRequest(request, response);
        } catch (NotBoundException ex) {
            Logger.getLogger(SongServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

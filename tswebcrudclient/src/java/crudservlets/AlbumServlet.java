/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package crudservlets;

import Models.Album;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tswebcrudinterface.Tswebcrudinterface;
import tswebcrudinterface.Tswebcrudinterface.AlbumControllerInterface;

/**
 *
 * @author diego
 */
@WebServlet(name = "AlbumServlet", urlPatterns = {"/AlbumServlet"})
public class AlbumServlet extends HttpServlet {

    private AlbumControllerInterface controllerAlbum;

    @Override
    public void init() throws ServletException {
        super.init();
        Registry r;
        try {
            r = LocateRegistry.getRegistry("192.168.100.192", 1234);
            controllerAlbum = (AlbumControllerInterface) r.lookup("AlbumControllerInterface");
        } catch (RemoteException ex) {
            Logger.getLogger(AlbumServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(AlbumServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        String codigo = (String) request.getSession().getAttribute("CodServAlbum");
        String select = (String) request.getParameter("select");
        String action = request.getParameter("action");
        String idAlbum = request.getParameter("id");
        
        if (codigo == null || select == null) {
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            System.out.println("El codigo es: " + codigo);
            
            out.print("<h1> hola </h1>");
            out.print("<h1>" + codigo + "</h1>");
            switch (codigo) {
                case "1":
                    createAlbum(request, response);
                    break;
                case "2":                    
                    readAlbums(request, response);
                    break;
                case "3":
                    updateAlbum(request, response);
                    break;
                case "4":
                    if (action == null || idAlbum == null) {
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                        return;
                    }
                    Album album;
                    RequestDispatcher dispatcher;
                    if(action.equals("create")){
                        request.setAttribute("select", "1");
                        dispatcher = request.getRequestDispatcher("/AlbumViews/albumcreateview.jsp");
                        dispatcher.forward(request, response);
                    }
                    
                    if(action.equals("update")){
                        request.setAttribute("select", "3");
                        album = controllerAlbum.getAlbumById(Integer.parseInt(idAlbum));
                        request.setAttribute("AlbumToUpdate", album);
                        dispatcher = request.getRequestDispatcher("/AlbumViews/albumupdateview.jsp");
                        dispatcher.forward(request, response);
                    }
                    
                    if(action.equals("delete")){
                        request.setAttribute("select", "4");
                        album = controllerAlbum.getAlbumById(Integer.parseInt(idAlbum));
                        controllerAlbum.delete(album);
                        
                        Cookie lastDeletedAlbumCookie = new Cookie("lastDeletedAlbumId", idAlbum);
                        lastDeletedAlbumCookie.setMaxAge(60 * 60 * 24); // Duración de la cookie en segundos (1 día)
                        response.addCookie(lastDeletedAlbumCookie);
                        
                        readAlbums(request, response);
                    }
                    break;
                default:
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                    break;
            }
        }
    }
    
    private void readAlbums(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        ArrayList<Album> albums = controllerAlbum.getAll();
        request.setAttribute("select", "2");
                    
        if (!albums.isEmpty()) {
            request.setAttribute("Albums", albums);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AlbumViews/albumreadview.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.print("No albums available.");
            request.setAttribute("Albums", albums);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AlbumViews/albumreadview.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void updateAlbum(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String release_year = request.getParameter("release_year");

        Album albumUpdate = new Album(Integer.parseInt(id), title, Integer.parseInt(release_year));
        controllerAlbum.update(albumUpdate);
        request.setAttribute("select", "3");
        readAlbums(request, response);
    }
    
    private void createAlbum(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException, NotBoundException {
        String title = request.getParameter("title");
        String release_year = request.getParameter("release_year");

        Album albumUpdate = new Album(title, Integer.parseInt(release_year));
        controllerAlbum.add(albumUpdate);
        readAlbums(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RemoteException {
        try {
            processRequest(request, response);
        } catch (NotBoundException ex) {
            Logger.getLogger(AlbumServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AlbumServlet.class.getName()).log(Level.SEVERE, null, ex);
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

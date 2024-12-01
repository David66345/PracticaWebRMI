/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tswebcrudinterface;

import Models.Album;
import Models.Song;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class Tswebcrudinterface {

    public interface AlbumControllerInterface extends Remote {

        public void add(Album album) throws RemoteException;

        public ArrayList<Album> getAll() throws RemoteException;

        public void update(Album album) throws RemoteException;

        public void delete(Album album) throws RemoteException;

        public int getIdByTitle(String title) throws RemoteException;
        
        public Album getAlbumById(int id) throws RemoteException;
    }

    public interface SongControllerInterface extends Remote {

        public void add(Song song) throws RemoteException;

        public ArrayList<Song> getAll() throws RemoteException;

        public void update(Song song) throws RemoteException;

        public void delete(Song song) throws RemoteException;
        
        public Song getSongById(int id) throws RemoteException;
        
        public ArrayList<Album> getAllAlbum() throws RemoteException;
        
        public int suma (int a, int b)throws RemoteException;
    }

}

package DB;

import Models.Album;
import Models.DBConnection;
import Models.Song;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tswebcrudinterface.Tswebcrudinterface;
import tswebcrudinterface.Tswebcrudinterface.AlbumControllerInterface;

public class AlbumController extends UnicastRemoteObject implements AlbumControllerInterface {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBConnection connect = new DBConnection();

    public AlbumController() throws RemoteException {
    }

    @Override
    public void add(Album album) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("INSERT INTO Album (title, release_year) VALUES (?,?)");
            ps.setString(1, album.getTitle());
            ps.setInt(2, album.getRelease_year());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Album> getAll() throws RemoteException {
        ArrayList<Album> albums;
        albums = new ArrayList<Album>();
        try {
            ps = connect.getCon().prepareStatement("Select * from album");
            rs = ps.executeQuery();
            while (rs.next()) {
                Album album = new Album();
                album.setId(rs.getInt("id"));
                album.setTitle(rs.getString("title"));
                album.setRelease_year(rs.getInt("release_year"));
                albums.add(album);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return albums;
    }

    @Override
    public void update(Album album) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("UPDATE Album SET title = ?, release_year = ? WHERE id = ?");
            ps.setString(1, album.getTitle());
            ps.setInt(2, album.getRelease_year());
            ps.setInt(3, album.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Album album) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("DELETE FROM Album WHERE id = ?");
            ps.setInt(1, album.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getIdByTitle(String title) throws RemoteException {
        Album album;
        album = new Album();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connect.getCon().prepareStatement("Select id from album where title = ?");
            ps.setString(1, title);
            rs = ps.executeQuery();
            while (rs.next()) {
                album.setId(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return album.getId();
    }

    @Override
    public Album getAlbumById(int id) throws RemoteException {
        Album album;
        album = new Album();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connect.getCon().prepareStatement("Select * from album where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                album.setId(rs.getInt("id"));
                album.setTitle(rs.getString("title"));
                album.setRelease_year(rs.getInt("release_year"));
            }
        } catch (SQLException ex) {
        }
        return album;
    }
}

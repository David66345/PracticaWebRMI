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
import tswebcrudinterface.Tswebcrudinterface.SongControllerInterface;

public class SongController extends UnicastRemoteObject implements SongControllerInterface{
    private PreparedStatement ps;
    private ResultSet rs;
    private DBConnection connect = new DBConnection();
    
    public SongController() throws RemoteException{
    }

    @Override
    public void add(Song song) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("INSERT INTO Song (album_id, title, duration) VALUES (?,?,?)");
            ps.setInt(1, song.getAlbum_id());
            ps.setString(2, song.getTitle());
            ps.setInt(3, song.getDuration());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Song> getAll() throws RemoteException {
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            ps = connect.getCon().prepareStatement("Select * from song");
            rs = ps.executeQuery();
            while (rs.next()) {
                Song song = new Song();
                song.setId(rs.getInt("id"));
                song.setAlbum_id(rs.getInt("album_id"));
                song.setTitle(rs.getString("title"));
                song.setDuration(rs.getInt("duration"));
                songs.add(song);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs;
    }

    @Override
    public void update(Song song) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("UPDATE Song SET title = ?, duration = ?, album_id = ? WHERE id = ?");
            ps.setString(1, song.getTitle());
            ps.setInt(2, song.getDuration());
            ps.setInt(3,song.getAlbum_id());
            ps.setInt(4,song.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Song song) throws RemoteException {
        try {
            ps = connect.getCon().prepareStatement("DELETE FROM Song WHERE id = ?");
            ps.setInt(1,song.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int suma(int a, int b) throws RemoteException {
        return a+b;
    }

    @Override
    public Song getSongById(int id) throws RemoteException {
        Song song;
        song = new Song();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connect.getCon().prepareStatement("Select * from song where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                song.setId(rs.getInt("id"));
                song.setTitle(rs.getString("title"));
                song.setDuration(rs.getInt("duration"));
                song.setAlbum_id(rs.getInt("album_id"));
            }
        } catch (SQLException ex) {
        }
        return song;
    }

    @Override
    public ArrayList<Album> getAllAlbum() throws RemoteException {
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

}

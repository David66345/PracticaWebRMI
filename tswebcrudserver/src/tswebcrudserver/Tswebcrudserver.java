/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tswebcrudserver;

import DB.AlbumController;
import DB.SongController;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author diego
 */
public class Tswebcrudserver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        SongController s = new SongController();
        System.out.print(s.suma(1, 2));
        
        Registry r;
        r = LocateRegistry.createRegistry(1234);
        r.rebind("AlbumControllerInterface", new AlbumController());
        r.rebind("SongControllerInterface", new SongController());
    }
}

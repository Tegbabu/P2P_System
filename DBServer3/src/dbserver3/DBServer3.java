/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbserver3;

/**
 *
 * @author user
 */
public class DBServer3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(12036);
        server.startServer();
    }
    
}

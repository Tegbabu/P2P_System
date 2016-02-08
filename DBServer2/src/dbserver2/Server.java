/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbserver2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Server
{
 
    private static Socket socket;
    private static ServerSocket server;
    private static int port;
    private static String myIp;
    
    public Server(int port){
        try {
            this.port = port;
            server = new ServerSocket(port);
            myIp = server.getInetAddress() + "";
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void startServer(){
        while (true) {            
            try {
                System.out.println("Server is listnening at port " + port);
                socket = server.accept();
                new Thread(new CleintHandler(socket)).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

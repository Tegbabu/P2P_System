/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleintside;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class threadMethods implements Runnable{

    private String ip;
    private int port;
    private String query;
    private ArrayList<String> list;
    
    public threadMethods(String i, int port, String q,ArrayList<String> list){
        ip = i;
        this.port = port;
        query = q;
        this.list = list;
    }
    
    public ArrayList<String> sendQueryAcceptData(String ip, int port ,String query){
        ArrayList<String> list = new ArrayList<>();
        String message = null;
        Socket socket = null;
        try {
            InetAddress address = InetAddress.getByName(ip);
            socket = new Socket(address, port);
            
            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            String sendMessage = query + "\n";
            bw.write("select\n");
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : "+sendMessage);
            
            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            while((message =  br.readLine()) != null){
                System.out.println("Message received from the server : " + message);
                list.add(message);
            }
            System.out.println("exit while loop");
            
        } catch (UnknownHostException ex) {
            System.out.println("Databse couldnot be contacted");;
        } catch (IOException ex) {
            System.out.println("server couldnot be contacted");
        }
        finally{
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    @Override
    
    public void run() {
        list = sendQueryAcceptData(ip, port, query);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbserver1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class CleintHandler implements Runnable{
    
    private String query;
    private Socket socket;
    private DBConnection dbCon;
    
    public CleintHandler(Socket soc){
        socket = soc;
        dbCon = new DBConnection();
        dbCon.connectToDB();
    }
    
    public void msgHandler(){
        System.out.println("handling message");
        InputStream is = null;
        try {
            is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            if(query.equals("insert")){
                String exe = br.readLine();
                System.out.println("query asked " + exe);
                String s = dbCon.insertQuery(exe);
                
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(s +"\n");
                bw.flush();
                System.out.println("send " + s);
                
            }
            else if(query.equals("select")){
                
                String exe = br.readLine();
                System.out.println("query asked " + exe);
                String returnQuery = dbCon.getResult(exe);
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnQuery);
                bw.flush();
                System.out.println("Message sent to the client is "+returnQuery);
            }
            else if(query.equals("update") || query.equals("delete")){
                String exe = br.readLine();
                System.out.println("query asked " + exe);
                dbCon.insertQuery(exe);
            }
            System.out.println("Query received from client is " + query);
            
            //String returnQuery = dbCon.getResult(query);
            //OutputStream os = socket.getOutputStream();
            //OutputStreamWriter osw = new OutputStreamWriter(os);
            //BufferedWriter bw = new BufferedWriter(osw);
            //bw.write(returnQuery +"\n");
            //System.out.println("Message sent to the client is "+returnQuery);
            //bw.flush();
            
        } catch (IOException ex) {
            
        } finally{
            try{
                socket.close();
            }
            catch(Exception e){}
        }
    }
    
    @Override
    public void run() {
        msgHandler();
    }
}

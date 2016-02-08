/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleintside;

import java.awt.List;
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
public class Client {

    private String dbServer1 = "localhost";
    private String dbServer2 = "localhost";
    private String dbServer3 = "localhost";
    private String dbServer4 = "localhost";
    private int port = 12034;
    private int port2 = 12035;
    private int port3 = 12036;
    private int port4 = 12035;
    public ArrayList<String> strs = new ArrayList<>();

    public ArrayList<String> sendQueryToServer(String query, int i) {
        ArrayList<String> lists = new ArrayList<>();
        ArrayList<String> lists2 = new ArrayList<>();
        ArrayList<String> wholeList = new ArrayList<>();
        switch (i) {
            case 1:
            //insertStatment(id, file, i, query)
            case 2:
                lists = sendQueryAcceptData(dbServer1, port, query);
                if(lists.isEmpty()){
                    lists = sendQueryAcceptData(dbServer3, port3, query);
                }
                lists2 = sendQueryAcceptData(dbServer2, port2, query);
                if(lists2.isEmpty()){
                    lists2 = sendQueryAcceptData(dbServer4, port4, query);
                }
                System.out.println("file on the array list");
                for (String s : lists) {
                    wholeList.add(s);
                }
                for (String s : lists2) {
                    wholeList.add(s);
                }
            case 3:
//                updateDelete(dbServer1, port, query);
//                updateDelete(dbServer2, port, query);
        }
        return wholeList;
    }

    public void updateDelete(String ip, int port, String query) {
        String message = null;
        try {
            InetAddress address = InetAddress.getByName(ip);
            Socket socket = new Socket(address, port);

            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String sendMessage = query;
            bw.write("update\n");
            bw.write(sendMessage);
            bw.flush();
            bw.close();
            System.out.println("Message sent to the server : " + sendMessage);

        } catch (UnknownHostException ex) {
            System.out.println("Databse couldnot be contacted");;
        } catch (IOException ex) {
            System.out.println("server couldnot be contacted");
        }
    }

    public ArrayList<String> sendQueryAcceptData(String ip, int port, String query) {
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
            System.out.println("Message sent to the server : " + sendMessage);

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while ((message = br.readLine()) != null) {
                System.out.println("Message received from the server : " + message);
                list.add(message);
            }
            System.out.println("exit while loop");

        } catch (UnknownHostException ex) {
            System.out.println("Databse couldnot be contacted");;
        } catch (IOException ex) {
            System.out.println("server couldnot be contacted");
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public String sending(String ip, int port, String query) {
        String message = null;
        try {
            InetAddress address = InetAddress.getByName(ip);
            Socket socket = new Socket(address, port);

            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String sendMessage = query;
            bw.write("insert\n");
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : " + sendMessage);

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            message = br.readLine();
            br.close();
        } catch (UnknownHostException ex) {
            System.out.println("Databse couldnot be contacted");;
        } catch (IOException ex) {
            message = "server couldnot be contacted";
            System.out.println("server couldnot be contacted");
        }
        return message;
    }

    public String insertStatment(String fileName, int fileSize, String fileLocaion) {
        String sendMessage = "";
        String m = "";
        if (fileSize >= 102400) {
            sendMessage = "insert into file_table(fileName,fileSize,fileLocation,IPAddress,port) values('" + fileName + "'," + fileSize + ",'" + fileLocaion + "','" + dbServer1 + "'," + port + ")\n";
            m = sending(dbServer1, port, sendMessage);
            sendMessage = "insert into file_table(fileName,fileSize,fileLocation,IPAddress,port) values('" + fileName + "'," + fileSize + ",'" + fileLocaion + "','" + dbServer3 + "'," + port2 + ")\n";
            m = sending(dbServer3, port3, sendMessage);
        } else {
            sendMessage = "insert into file_table(fileName,fileSize,fileLocation,IPAddress,port) values('" + fileName + "'," + fileSize + ",'" + fileLocaion + "','" + dbServer2 + "'," + port3 + ")\n";
            m = sending(dbServer2, port2, sendMessage);
            sendMessage = "insert into file_table(fileName,fileSize,fileLocation,IPAddress,port) values('" + fileName + "'," + fileSize + ",'" + fileLocaion + "','" + dbServer4 + "'," + port4 + ")\n";
            m = sending(dbServer4, port4, sendMessage);
        }
        return m;
    }

}

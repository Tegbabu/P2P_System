/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbserver2;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class DBConnection {
    
    static final String Jdbc_Driver = "com.mysql.jdbc.Driver";
    static final String Db_Url = "jdbc:mysql://localhost:3306/db2?zeroDateTimeBehavior=convertToNull";
    static final String userName = "root";
    static final String pwd = "eyoel";
    Connection con;
    Statement st;
    
    public void connectToDB(){
        try {
            Class.forName(Jdbc_Driver);
            System.out.println("Connecting to the database");
            con = DriverManager.getConnection(Db_Url, userName, pwd);
            System.out.println("Databse connected");
        } catch (Exception e) {
        }
    }
    
    public void updateQuery(String query){
        try {
            st = con.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
        }
    }
    
    public String insertQuery(String query){
        String ret = "";
        try {
            st = con.createStatement();
            st.execute(query);
            ret = "File Succesfully uploaded";
            System.out.println("Query inserted");
        } catch (SQLException ex) {
            ex.printStackTrace();
            ret = "Unable to upload file";
        }
        finally{
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    
    public String getResult(String query){
        String queryResult = "";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query); 
            while(rs.next()){
                int id = rs.getInt("fileId");
                String name = rs.getString("fileName");
                String loc = rs.getString("fileLocation");
                int fileSize = rs.getInt("fileSize");
                String ip = rs.getString("IPAddress");
                String f = name + "#" + loc +"#" + fileSize + "#" + ip;
                queryResult += f + "\n";
            }
            rs.close();
            st.close();
        } catch (Exception e) {
        }
        return queryResult;
    }
    
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        System.out.println("to connect to DB");
        db.connectToDB();
        db.insertQuery("insert into file_table values(90,'happytoseeyou2.wav','32','C:\\Users\\user\\Documents\\happytoseeyou2.wav','localHost manually setted',12034)");
    }
}

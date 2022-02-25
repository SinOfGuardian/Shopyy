package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMYSQL {

    String classD = "com.mysql.jdbc.Driver";

    String url = "jdbc:mysql://localhost:3306/aredoweknowsql";
    String un = "root";
    String passwd = "1234";
    @SuppressLint("NewApi")
    public Connection CONNECT() {

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        Connection conn= null;
        String ConnURL= null;

        try {
            Class.forName(classD);

            conn = DriverManager.getConnection(url, un, passwd);
            conn = DriverManager.getConnection(ConnURL);
            if (conn != null){
                System.out.println("Connection Successful");
            }else{
                System.out.println("Unable Connect");
            }

        } catch (SQLException se) {
            Log.e("Error", se.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.e("Error", e.getMessage());
        }
        catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }
        return conn;
    }

}

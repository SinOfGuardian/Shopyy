package com.example.aredoweknow;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMYSQL {

    String classD = "com.mysql.jdbc.Driver";

    String url = "jdbc:mysql://192.168.100.52:3306/aredoweknowsql";
    String un = "root";
    String passwd = "pass";

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

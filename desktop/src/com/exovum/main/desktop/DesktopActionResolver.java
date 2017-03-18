package com.exovum.main.desktop;

import com.exovum.main.ActionResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by exovu on 3/18/2017.
 */

public class DesktopActionResolver implements ActionResolver {

    @Override
    public Connection getConnection() {
        String url = "jdbc:sqlite:data.sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.exovum.dbsample;

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


    // TODO: Desktop Implementation - setupAWSCredentials()
    @Override
    public void setupAWSCredentials() {

    }

    // TODO: Desktop Implementation - setupDynamoDB()
    @Override
    public void setupDynamoDB() {

    }

    // TODO: Desktop Implementation - saveObjectToMapper(Object object)
    @Override
    public void saveObjectToMapper(Object object) {

    }

    // TODO: Desktop Implementation - getObjectFromMapper()
    @Override
    public Object getObjectFromMapper() {
        return null;
    }

    // TODO: Desktop Implementation - saveBookToMapper(Book book)
    @Override
    public void saveBookToMapper(Book book) {

    }

    // TODO: Desktop Implementation - getBookFromMapper(String isbn)
    @Override
    public Book getBookFromMapper(String isbn) {
        return null;
    }



}

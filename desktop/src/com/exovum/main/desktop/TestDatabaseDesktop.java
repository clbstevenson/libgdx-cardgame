package com.exovum.main.desktop;

import com.exovum.main.TestDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by exovu on 3/17/2017.
 * Implementation of the TestDatabase for Desktop Applications
 */

public class TestDatabaseDesktop extends TestDatabase {

    protected Connection db_connection;
    protected Statement statement;
    protected boolean noDatabase = false;

    public TestDatabaseDesktop() {
        loadDatabase();
        if(isNewDatabase()) {
            onCreate();
            upgradeVersion();
        } else if (isVersionDifferent()) {
            onUpgrade();
            upgradeVersion();
        }
    }

    @Override
    public void execute(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int executeUpdate(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Result query(String sql) {
        try {
            return new ResultDesktop(statement.executeQuery(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* TODO: implement query for Desktop
    public Result query(String sql);
     */

    private void loadDatabase() {
        File file = new File(database_name + ".db");
        if(!file.exists())
            noDatabase= true;

        try {
            //Class.forName("org.sqlite.JDBC");
            Class.forName("org.sqldroid.SQLDroidDriver");
            //;Class.forName("org.sqlite.JDBC");
            //db_connection = DriverManager.getConnection("jdbc:sqlite:" + database_name + ".db");
            //db_connection = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
            System.out.println("Before you get a connection");
            db_connection = DriverManager.getConnection("jdbc:sqlite:/data/data/com.exovum.main/databases/addressbook.db");
            statement = db_connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Caught ClassNotFoundException");
            e.printStackTrace();
        } catch (SQLException sqlE) {
            System.out.println("Caught SQLException");
            sqlE.printStackTrace();
        }
    }

    private void upgradeVersion() {
        execute("PRAGMA user_version=" + version);
    }

    // Returns true if the database is not created
    private boolean isNewDatabase() {
        return noDatabase;
    }

    private boolean isVersionDifferent() {
        Result q = query("PRAGMA user_version");
        if(!q.isEmpty())
            return (q.getInt(1)!=version);
        else
            return true;
    }

    // Desktop implementation for Database Result
    public class ResultDesktop implements Result {

        ResultSet resultSet;
        boolean called_is_empty = false;

        public ResultDesktop(ResultSet set) {
            resultSet = set;
        }

        @Override
        public boolean isEmpty() {
            try {
                if(resultSet.getRow()==0) {
                    called_is_empty = true;
                    return !resultSet.next();
                }
                return resultSet.getRow()==0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean moveToNext() {
            try {
                if (called_is_empty){
                    called_is_empty = false;
                    return true;
                } else
                    return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public int getColumnIndex(String name) {
            try {
                return resultSet.findColumn(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public float getFloat(int columnIndex) {
            try {
                return resultSet.getFloat(columnIndex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public int getInt(int columnIndex) {
            try {
                return resultSet.getInt(columnIndex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public String getString(int columnIndex) {
            try {
                return resultSet.getString(columnIndex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

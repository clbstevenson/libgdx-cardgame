package com.exovum.main;

/**
 * Created by exovu on 3/17/2017.
 *
 * General class for Database transactions. Needs to be implemented for each module (android, desktop)
 */

public abstract class TestDatabase {

    protected static String database_name = "test_products";
    protected static TestDatabase instance = null;
    protected static int version = 1;

    // Runs a sql query without a returned result, such as "create"
    public abstract void execute(String sql);

    // Similar to execute, but returns number of updated rows
    public abstract int executeUpdate(String sql);

    //Runs a query and returns an Object with all the results.
    public abstract Result query(String sql);

    public void onCreate() {
        // Example query

        execute("CREATE TABLE 'products' (" +
                "'_id' INTEGER PRIMARY KEY NOT NULL , " +
                "'name' VARCHAR NOT NULL );");
        execute("INSERT INTO 'products' (name) " +
                "values ('oranges')");
        // Example query to retrieve data from table
        Result q = query("SELECT * FROM 'products'");
        if (!q.isEmpty()) {
            q.moveToNext();
            System.out.println("Product: " + q.getString(q.getColumnIndex("name")));
        }

    }


    public void onUpgrade() {
        execute("DROP TABLE IF EXISTS 'products';");
        onCreate();
        System.out.println("Database Upgraded - Database version changed.");
    }

    // Interface to be implemented by Android and Desktop modules
    public interface Result {
        public boolean isEmpty();
        public boolean moveToNext();
        public int getColumnIndex(String name);
        public float getFloat(int columnIndex);
        public int getInt(int columnIndex);
        public String getString(int columnIndex);
        //TODO: more methods
        // ...
    }
}

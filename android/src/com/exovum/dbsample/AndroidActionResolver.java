package com.exovum.dbsample;

import android.content.Context;
import android.os.Handler;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.exovum.main.ActionResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by exovu on 3/18/2017.
 */

public class AndroidActionResolver implements ActionResolver {

    Handler uiThread;
    Context context;
    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonDynamoDBClient client;
    DynamoDBMapper mapper;

    public AndroidActionResolver(Context appContext) {
        uiThread = new Handler();
        this.context = appContext;
    }

    public AndroidActionResolver() {

    }

    @Override
    public Connection getConnection() {
        String url = "jdbc:sqldroid:/data/data/my.app.name/databases/data.sqlite";
        try {
            Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
            return DriverManager.getConnection(url);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setupAWSCredentials() {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,    /* get the context for the application */
                "us-east-1:d2c63a02-4ca8-4dd8-91bd-d63aa280adc7",    /* Identity Pool ID */
                Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );
    }

    @Override
    public void setupDynamoDB() {
        client = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(client);
    }

    @Override
    public void saveObjectToMapper(Object data) {
        mapper.save(data);
    }

    @Override
    public AndroidActionResolver getObjectFromMapper() {
        return null;
    }

    @Override
    public void saveBookToMapper(Book book) {
        mapper.save(book);
    }

    @Override
    public Book getBookFromMapper(String isbn) {
        return mapper.load(Book.class, isbn);
    }


}

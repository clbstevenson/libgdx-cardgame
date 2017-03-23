package com.exovum.main;

import com.exovum.dbsample.Book;

import java.sql.Connection;

/**
 * Created by exovu on 3/18/2017.
 */

public interface ActionResolver {
    public Connection getConnection();
    /**
     * Connects to AWS - Amazon Cognito for user Identity Management
     * @return void
     */
    public void setupAWSCredentials();

    /**
     * Initializes the DynamoDB client and DynamoDBMapper
     */
    public void setupDynamoDB();

    /**
     * Stores object {@code data} to the DynamoDBMapper
     * @param data Object to be stored in the Mapper
     */
    public void saveObjectToMapper(Object data);

    /**
     * Returns object of type {@code T} from DynamoDBMapper
     * @return Object requested from the Mapper
     */
    public Object getObjectFromMapper();

    // "Book" sample implementations
    public void saveBookToMapper(Book book);

    public Book getBookFromMapper(String isbn);
}
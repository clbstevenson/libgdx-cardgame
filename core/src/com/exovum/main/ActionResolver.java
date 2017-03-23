package com.exovum.main;

import java.sql.Connection;

/**
 * Created by exovu on 3/18/2017.
 */

public interface ActionResolver {
    public Connection getConnection();
    public void setupAWSCredentials();
}
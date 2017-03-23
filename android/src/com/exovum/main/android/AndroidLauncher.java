package com.exovum.main.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.exovum.dbsample.DatabaseTest;
import com.exovum.dbsample.AndroidActionResolver;
import com.exovum.dbsample.DynamoDBSample;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new CardGame(), config);
		//initialize(new DatabaseTest(), config);



        AndroidActionResolver actionResolver = new AndroidActionResolver(getApplicationContext());
        initialize(new DynamoDBSample(actionResolver), config);
        //actionResolver.setupAWSCredentials();
//		CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//				getApplicationContext(),    /* get the context for the application */
//				"COGNITO_IDENTITY_POOL",    /* Identity Pool ID */
//				Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
//		);
	}
}

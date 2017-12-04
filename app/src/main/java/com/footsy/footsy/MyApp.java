package com.footsy.footsy;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Joe on 12/3/17.
 */

public class MyApp extends Application {
	public void onCreate() {
		super.onCreate();
		// Create an InitializerBuilder
		Stetho.InitializerBuilder initializerBuilder =
				Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
		initializerBuilder.enableWebKitInspector(
				Stetho.defaultInspectorModulesProvider(this)
		);

// Use the InitializerBuilder to generate an Initializer
		Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
		Stetho.initialize(initializer);
	}
}
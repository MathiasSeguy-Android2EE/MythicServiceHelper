/**<ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1</li>
 * <li>26 janv. 2013</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.service.helper.lib;

import android.app.Application;
import android.util.Log;

import com.android2ee.formation.service.helper.lib.serviceHelper.ServiceLoaderIntf;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be your casual Application.<br/>
 *        You should extends it instead of Application.
 *        If you don't want to extends it, just copy paste the code within your own application
 *        And don't forget to declare it in your manifest.xml file.
 */
public abstract class MApplication extends Application {
	/**
	 * Just keep a reference on it to avoid unusefull destruction/recreation of services
	 */
	private ServiceLoaderIntf sLoader;

	/*
	 * (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.e("MApplication", "MApplication:onCreate called");
		super.onCreate();
		// be available in every class of your application.
		MAppInstance.ins.setApplication(this);
		// keep the ref
		sLoader = getServiceLoaderInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		// There is a rumor on this method that it is not called by the system
		// this method is called only with emulator ?o?
		// ok so test
		Log.e("MApplication", "MApplication:onTerminate called");

		super.onTerminate();
	}

	/**
	 * This method has to be called when the application died.<br/>
	 * it kill all the services<br/>
	 * So find the activity that quit the application and add that method
	 */
	public void onBackPressed() {
		// Call the application object and release the services
		sLoader.unbindAndDie();
	}

	/**
	 * @return the service loader instance.
	 *         Should be a stuff like that:
	 *         return ServiceLoader.instance
	 */
	protected abstract ServiceLoaderIntf getServiceLoaderInstance();
}

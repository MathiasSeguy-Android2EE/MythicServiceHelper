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
package com.android2ee.formation.service.helper1.generic;

import android.app.Application;

import com.android2ee.formation.service.helper1.service.loader.ServiceLoader;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be your casual Application.
 *        You should extends it instead of Application.
 */
public class MApplication extends Application {
	/**
	 * Just keep a reference on it to avoid unusefull destruction/recreation of services
	 */
	ServiceLoader sLoader;

	/*
	 * (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		// be available in every class of your application.
		MAppInstance.ins.setApplication(this);
		// keep the ref
		sLoader = ServiceLoader.instance;
	}

}

/**<ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.generic</li>
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

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to declare MService.
 *        MService are business and Android service (All your business services have to be Android
 *        services)
 *        Your business service have to inherits this class
 */
public class MService extends Service {
	/******************************************************************************************/
	/** The binder process **************************************************************************/
	/******************************************************************************************/
	/**
	 * The Binder
	 */
	private final Binder binder = new LocalBinder();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to be a local Binder that keep and share a pointer on the instance of
	 *        this
	 */
	public class LocalBinder extends Binder {
		// as usual
		public MService getService() {
			// return the instance of the service
			Log.v("MService", "LocalBinder:getService(), serv: " + MService.this);
			return MService.this;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

}

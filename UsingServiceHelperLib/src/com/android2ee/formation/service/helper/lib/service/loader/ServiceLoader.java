/**
 * <ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.service.shelper</li>
 * <li>26 janv. 2013</li>
 * <li>======================================================</li>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li> /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> Produced by <strong>Dr. Mathias
 * SEGUY</strong>.</br> Delivered by <strong>http://android2ee.com/</strong></br> Belongs to
 * <strong>Mathias Seguy</strong></br> </br> This code is free for any usage except training and
 * can't be distribute.</br> The distribution is reserved to the site
 * <strong>http://android2ee.com</strong>.</br> The intelectual property belongs to <strong>Mathias
 * Seguy</strong>.</br> <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * ********************
 * ******************************************************************************
 * ***************</br> Ce code est libre de toute utilisation mais n'est pas distribuable.</br> Sa
 * distribution est reservée au site <strong>http://android2ee.com</strong>.</br> Sa propriété
 * intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************
 * ************************************************************************</br>
 */
package com.android2ee.formation.service.helper.lib.service.loader;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android2ee.formation.service.helper.lib.MAppInstance;
import com.android2ee.formation.service.helper.lib.MService;
import com.android2ee.formation.service.helper.lib.service.services.DummyService;
import com.android2ee.formation.service.helper.lib.serviceHelper.OnServiceCallBack;
import com.android2ee.formation.service.helper.lib.serviceHelper.ServiceLoaderIntf;
/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to lazy load the services and keep a reference on them avoiding
 *        destruction/recreation of services when activities die/recreate<br/>
 *        You need to declare in it all the services you want the Activities reach.
 *        You need to declare them the way dummyService is.
 *        Use the eclipse template to generate the code for your service
 *        (MSH_SL)
 */
public enum ServiceLoader implements ServiceLoaderIntf{
	/**
	 * The instance of the ServiceLoader
	 */
	instance;

	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/
	/**
	 * The list of the bound services to this used to unbind the service.
	 * A service is pointed by its serviceConnection.
	 */
	List<ServiceConnection> boundServices = null;

	/******************************************************************************************/
	/** Constructor **************************************************************************/
	/******************************************************************************************/
	/**
	 * Empty constructor to instantiate the list of bound services
	 */
	private ServiceLoader() {
		Log.e("ServiceLoader", "ServiceLoader, constructor called");
		boundServices = new ArrayList<ServiceConnection>();
	}

	/******************************************************************************************/
	/** Destructor **************************************************************************/
	/******************************************************************************************/
	/**
	 * This method is called by MApplication to unbind all the services and let them die when your
	 * application die
	 */
	public synchronized void unbindAndDie() {
		Log.w("ServiceLoader", "unbindAndDie, called: number of bound services " + boundServices.size());
		Log.w("ServiceLoader",
				"unbindAndDie, called: MAppInstance.ins.getApplication() " + MAppInstance.ins.getApplication());
		// Browse all the services and unbind them all
		for (ServiceConnection sConn : boundServices) {
			//first unbind the service
			MAppInstance.ins.getApplication().unbindService(sConn);
		}
		dService = null;
		boundServices.clear();
	}

	/***************************************/
	/** Use the template XXXX to generate code ****************/
	/******************************************/
	/******************************************************************************************/
	/** DummyService Accessors **************************************************************************/
	/******************************************************************************************/
	// TODO MSE do a template for the creation and declaration of such a service
	/**
	 * The service you want to expose to activities
	 */
	private DummyService dService = null;
	/**
	 * The list of the callback of the service instantiation
	 */
	private List<OnServiceCallBack> onDServiceCallBack;

	/**
	 * Method to start the DummyService service
	 */
	private void startDummySrv() {
		// insure the list is not null
		if (onDServiceCallBack == null) {
			onDServiceCallBack = new ArrayList<OnServiceCallBack>();
		}
		// if the service is null, launch it using the Bind process
		if (dService == null) {
			Log.v("ServiceLoader", "startDummySrv, called: " + dService);
			Intent service = new Intent(MAppInstance.ins.getApplication(), DummyService.class);
			MAppInstance.ins.getApplication().bindService(service, dummySrvConn,
					Service.START_STICKY);
		}
		Log.w("ServiceLoader", "startDummySrv, adding dummySrvConn to boundServices ");
		// add it to the list of bound services (if not in yet):
		if(!boundServices.contains(dummySrvConn)) {
			boundServices.add(dummySrvConn);
		}

	}

	/**
	 * The method to be called by the activities
	 * 
	 * @return the dService (could be null if not started yet)
	 */
	public void getdService(OnServiceCallBack onServiceCallBack) {
		// I should add activity to the listener of intent with action activityId
		// insure the service is started
		startDummySrv();
		// then if the service is null add the callBack to the list of elements waiting for the
		// service to be instantiated
		Log.v("ServiceLoader", "startDummySrv, called: " + dService);
		if (dService == null) {
			// add to the callback list
			onDServiceCallBack.add(onServiceCallBack);
		} else {
			// else, service is not null, just use directly the callback
			onServiceCallBack.onServiceCallBack(dService);
		}
	}

	/**
	 * The object to handle the connection of this with the service.
	 * So this will keep a pointer on the service not loosing it in the memory.
	 */
	private ServiceConnection dummySrvConn = new ServiceConnection() {
		// as usual deconnexion
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.w("ServiceLoader", "dummySrvConn:onServiceDisconnected, dServ=null");
			dService = null;
		}

		// as usual connection
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// use the IBinder
			dService = (DummyService) ((MService.LocalBinder) service).getService();
			Log.v("ServiceLoader", "dummySrvConn:onServiceConnected, dServ: " + dService);
			// notify callback the service is awake
			for (OnServiceCallBack callBack : onDServiceCallBack) {
				Log.v("ServiceLoader", "dummySrvConn:onServiceConnected, callBack: " + callBack);
				callBack.onServiceCallBack(dService);
			}
			// and clear the list
			onDServiceCallBack.clear();
			onDServiceCallBack = null;
		}
	};

}

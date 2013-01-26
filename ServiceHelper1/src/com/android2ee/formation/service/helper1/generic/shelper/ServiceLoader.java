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
package com.android2ee.formation.service.helper1.generic.shelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android2ee.formation.service.helper1.MAppInstance;
import com.android2ee.formation.service.helper1.generic.MActivity;
import com.android2ee.formation.service.helper1.generic.MService;
import com.android2ee.formation.service.helper1.service.services.DummyService;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to:
 * <ul><li></li></ul>
 */
public enum ServiceLoader{
	
	instance;

	/******************************************************************************************/
	/** DummyService Accessors **************************************************************************/
	/******************************************************************************************/

	/**
	 * 
	 */
	private DummyService dService = null;

	/**
	 * @param name
	 * @param ordinal
	 */
	ServiceLoader() {
		//should start service here
		//or add callBack to activity when first calling it
		//it's fucking not lazy loading this way
//		startDummySrv();
		onDServiceCallBack=new ArrayList<OnServiceCallBack>();
	}

	/**
	 * @param activity
	 */
	public void startDummySrv() {
		if (dService == null) {
			Log.e("ServiceLoader","startDummySrv, called: "+dService);
			Intent service = new Intent(MAppInstance.ins.getApplication(), DummyService.class);
			MAppInstance.ins.getApplication().bindService(service, dummySrvConn, Service.START_STICKY);
		}

	}
	List<OnServiceCallBack> onDServiceCallBack;
	/**
	 * @return the dService (could be null if not started yet)
	 */
	public void  getdService(OnServiceCallBack onServiceCallBack) {
		//I should add activity to the listener of intent with action activityId
		startDummySrv() ;
		Log.e("ServiceLoader","startDummySrv, called: "+dService);
		if(dService == null) {
			onDServiceCallBack.add(onServiceCallBack);
		}else {
			onServiceCallBack.onServiceCallBack(dService);
		}
	}

	/**
	 * 
	 */
	ServiceConnection dummySrvConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			dService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			dService = (DummyService) ((MService.LocalBinder) service).getService();
			Log.e("ServiceLoader","dummySrvConn:onServiceConnected, dServ: "+dService);
			for(OnServiceCallBack callBack:onDServiceCallBack) {
				Log.e("ServiceLoader","dummySrvConn:onServiceConnected, callBack: "+callBack);
				callBack.onServiceCallBack(dService);
			}
			
		}
	};

}

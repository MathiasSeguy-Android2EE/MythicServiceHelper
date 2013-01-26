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

import com.android2ee.formation.service.helper1.generic.shelper.ServiceHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to:
 * <ul><li></li></ul>
 */
public abstract class MActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	/**
	 * @return a unique identifier of the activity to be attached to intent that have to be calledBack by the activity
	 */
	public abstract String getActivityId();
	/**
	 * 
	 */
	private BroadcastReceiver serviceCallBackReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("MActivity","serviceCallBackReceiver:onReceive, called: "+intent.getAction());
			if (intent.getAction() == getActivityId()) {
				String resType=intent.getStringExtra(ServiceHelper.SRV_MTH_RES_TYPE);
				if (resType.equals(ServiceHelper.Parcelable)) {
					onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID,-1),intent.getParcelableExtra(ServiceHelper.SRV_MTH_RES));
				}else if(resType.equals(ServiceHelper.String)) {
					onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID,-1),intent.getStringExtra(ServiceHelper.SRV_MTH_RES));
				}//and so on
				
			}
		}
	};

	/**
	 * @param serviceMethodId
	 * @param result
	 */
	protected abstract void onServiceCallBack(int serviceMethodId, Object result);

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("MActivity","onResume, registering "+getActivityId());
		registerReceiver(serviceCallBackReceiver, new IntentFilter(getActivityId()));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(serviceCallBackReceiver);
	}
}

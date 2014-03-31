/**<ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.generic</li>
 * <li>10 avr. 2013</li>
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
package com.android2ee.formation.service.helper.lib.support;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android2ee.formation.service.helper.lib.serviceHelper.ServiceHelper;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims toThis class aims to be the main FragmentActivty to inherits when using the ServiceHelper<br/>
 *        You have to take care of the import of the activity especially if your using the compat
 *        library.<br/>
 *        This class just hide the fact that your activity will be listening for intent named with
 *        the activityID
 *        that you pass when implementing the method getActivityId.
 */
public abstract class MFragmentActivity extends FragmentActivity {
	/**
	 * @return a unique identifier of the activity to be attached to intents that have to be
	 *         calledBack by the activity
	 */
	public String getActivityId() {
		// so use the canonical name it should be enough
		return getClass().getCanonicalName();
	}

	/**
	 * This method has to be implemented by your own activity<br/>
	 * It's quite simple, when you call a service, the return object is broadcast to you using
	 * intents.<br/>
	 * This method is the method where you root the methodId, meaning which method of the service
	 * has been called, with the method to execute
	 * This works the same way of the OnClickListener.<br/>
	 * Usually we do something like that Object obj=MyService.instance.doSomething(Param 1 p1,
	 * Param2 p2...)<br/>
	 * Here we do a stuff in two times first we call the method execution:<br/>
	 * MyService.instance.doSomething(Param 1 p1, Param2 p2...)<br/>
	 * Then we listen for the return using this method. This method, the one of the service called,
	 * has an Id
	 * switch (serviceMethodId) {<br/>
	 * case DummyService.doSomethingID:<br/>
	 * //call your result method doSomethingResult(result)<br/>
	 * onServiceResult((ConstantData)result);<br/>
	 * break;<br/>
	 * default:<br/>
	 * break;<br/>
	 * }<br/>
	 * 
	 * @param serviceMethodId
	 * @param result
	 */
	protected abstract void onServiceCallBack(int serviceMethodId, Object result);

	/**
	 * The main object to listen for service's callback.<br/>
	 * When the service has done the job an intent is thrown in the system with name
	 * getActivityId().<br/>
	 * This register is listening for those intents, catching back the service's method return
	 * intent.<br/>
	 * TODO MSE implement all the types
	 */
	private BroadcastReceiver serviceCallBackReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("MActivity", "serviceCallBackReceiver:onReceive, called: " + intent.getAction());
			// first be sure to listen for the right intent

			if (intent.getAction().equals(getActivityId())) {
				// retrieve the type of the result object
				String resType = intent.getStringExtra(ServiceHelper.SRV_MTH_RES_TYPE);
				// using that type, retrieve the result object
				// there will be a lot of case, should be the same case than the number of
				// intent.get**Extra method

				if (resType != null && resType.equals(ServiceHelper.Parcelable)) {
					// Parcelable case: It should be the case you use for your object (your POJO)
					// You should implement Parcelable on all the business objects you want to be
					// returned by the any services.
					// Why? Because every returned object of a service's method is sent to the GUI
					// using an Intent
					// and Intent only accept Parcelable in their bundle. So, you have to implement
					// it.
					// an exemple of implemented Parcelable is @see
					// com.android2ee.formation.service.helper1.transverse.pojo.ConstantData
					onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
							intent.getParcelableExtra(ServiceHelper.SRV_MTH_RES));

				} else if (resType != null && resType.equals(ServiceHelper.SERIALIZABLE)) {
					// serializable works too
					onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
							intent.getSerializableExtra(ServiceHelper.SRV_MTH_RES));
				} else {
					// just call back with an null object
					onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
							null);
				}

			} else {
				for (MFragmentSup mFrag : listeningFragments) {
					if (intent.getAction().equals(mFrag.getFragmentId())) {

						// retrieve the type of the result object
						String resType = intent.getStringExtra(ServiceHelper.SRV_MTH_RES_TYPE);
						// using that type, retrieve the result object
						// there will be a lot of case, should be the same case than the number of
						// intent.get**Extra method
						if (resType.equals(ServiceHelper.Parcelable)) {
							// Parcelable case: It should be the case you use for your object (your POJO)
							// You should implement Parcelable on all the business objects you want to be
							// returned by the any services.
							// Why? Because every returned object of a service's method is sent to the GUI
							// using an Intent
							// and Intent only accept Parcelable in their bundle. So, you have to implement
							// it.
							// an exemple of implemented Parcelable is @see
							// com.android2ee.formation.service.helper1.transverse.pojo.ConstantData
							mFrag.onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
									intent.getParcelableExtra(ServiceHelper.SRV_MTH_RES));
						} else if (resType.equals(ServiceHelper.SERIALIZABLE)) {
							// serializable works too
							mFrag.onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
									intent.getSerializableExtra(ServiceHelper.SRV_MTH_RES));

						} else {
							// just call back with an null object
							onServiceCallBack(intent.getIntExtra(ServiceHelper.SRV_MTH_ID, -1),
									null);
						}
					}
				}
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 * Just here to register the activity's serviceCallBackReceiver as the listener of Intent with
	 * name the activityID
	 * This insure your activity listens only for intents the activity has asked when calling
	 * business services methods (through the ServiceHelper).
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("MActivity", "onResume, registering " + getActivityId());
		registerReceiver(serviceCallBackReceiver, new IntentFilter(getActivityId()));

		if (listeningFragments != null) {
			for (MFragmentSup fragment : listeningFragments) {
				registerReceiver(serviceCallBackReceiver, new IntentFilter(fragment.getFragmentId()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 * Just here to unregister the activity's serviceCallBackReceiver as the listener of Intent with
	 * name the activityID
	 * This insure your activity listens only for intents the activity has asked when calling
	 * business services methods (through the ServiceHelper).
	 */
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(serviceCallBackReceiver);
	}

	/******************************************************************************************/
	/** Managing the link with the MFragmentActivity **************************************************************************/
	/******************************************************************************************/
	/**
	 * The list of fragment that are listening for intents
	 */
	List<MFragmentSup> listeningFragments = new ArrayList<MFragmentSup>();

	/**
	 * This method is called by the MFragmentSup to be register as a broadcast receiever of the intent
	 * 
	 * @param fragment
	 *            The fragment for registration
	 */
	void addMFragment(MFragmentSup fragment) {
		// add the fragment to the list of listening fragments
		listeningFragments.add(fragment);
		// add the fragmentId as a listening callback
		registerReceiver(serviceCallBackReceiver, new IntentFilter(fragment.getFragmentId()));
	}

	/**
	 * This method is called by the MFragmentSup to be unregister as a broadcast receiever of the intent
	 * 
	 * @param fragment
	 */
	void removeMFragment(MFragmentSup fragment) {
		// add the fragment to the list of listening fragments
		listeningFragments.remove(fragment);
	}

}

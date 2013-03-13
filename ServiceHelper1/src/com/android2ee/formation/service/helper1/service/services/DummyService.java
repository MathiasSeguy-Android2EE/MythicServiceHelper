/**<ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.service.services</li>
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
package com.android2ee.formation.service.helper1.service.services;

import android.os.AsyncTask;
import android.util.Log;

import com.android2ee.formation.service.helper1.generic.MService;
import com.android2ee.formation.service.helper1.generic.shelper.ServiceHelper;
import com.android2ee.formation.service.helper1.transverse.pojo.ConstantData;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to show how to declare your own business services
 *        There is no trick.
 *        Just declare your method and for each of it choose if you want an asynch treatment (using
 *        asyncTask or Handler)
 *        Don't forget for each method to declare its unique id (the Method Unique ID)
 */
public class DummyService extends MService {

	/******************************************************************************************/
	/** First exemple method **************************************************************************/
	/******************************************************************************************/
	/**
	 * The id of the method doSomething
	 * It has to be unique for your whole application and for each service'method of it.
	 */
	public static final int doSomethingID = 10000101;

	/**
	 * A dummy method with the return sent to the calling activity
	 * 
	 * @param activityId
	 *            The unique id of the calling activity
	 */
	public void doSomething(String activityId) {
		// do your treatment
		ConstantData data = new ConstantData("SomeResuls", 11021974, true);
		// And the important part is here
		// When your treatment is over, just use the serviceHelper to contact the calling Activity
		// and to send the method return to that activity
		ServiceHelper.instance.callBack(doSomethingID, activityId, data);
	}

	/******************************************************************************************/
	/** Second example method **************************************************************************/
	/******************************************************************************************/
	/**
	 * The id of the method doSomethingWithoutCallBack
	 * It has to be unique for your whole application and for each service'method of it.
	 */
	public static final int doSomethingWithourCBID = 10000102;
	/**
	 * unusued
	 */
	ConstantData unusedData;
	/**
	 * A dummy method that don't need to send data back to the calling activity
	 */
	public void doSomethingWithoutCallBack() {
		// do your treatment
		unusedData = new ConstantData("SomeResuls", 11021974, true);
		// And that's all no need to use the service helper
	}

	/******************************************************************************************/
	/** Third example method **************************************************************************/
	/******************************************************************************************/
	/**
	 * The id of the method doSomethingAsynch
	 * It has to be unique for your whole application and for each service'method of it.
	 */
	public static final int doSomethingAsyncID = 10000103;

	/**
	 * A dummy method that don't need to send data back to the calling activity
	 */
	public void doSomethingAsynch(String activityId) {
		// do your treatment
		new DoSomethingAsyncTask().execute(activityId);
	}

	/**
	 * Just a dummy counter (it also checks that the service die if it reset to 0 each time the application is launched)
	 */
	private int asyncCallNumber = 0;;

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to do a dummy async treatment just to show you how to code it.
	 *        The only trick is to have a field String activityID to call back the calling activity
	 *        and to give this parameter to the execute method of the asyncTask
	 */
	private class DoSomethingAsyncTask extends AsyncTask<String, Void, Object> {
		/**
		 * The activityId to call back it
		 * Needed on every AsyncTask you declare
		 */
		private String activityID = null;

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Object doInBackground(String... activityId) {
			// ok do a sleep
			ConstantData data = null;
			activityID = activityId[0];
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e("DummyService", "doSomethingAsyncTask:Exception", e);
			} finally {
				asyncCallNumber++;
				data = new ConstantData("Some async Results", asyncCallNumber, false);
			}
			return data;
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Object result) {
			// And the important part is here
			// When your treatment is over, just use the serviceHelper to contact the calling Activity
			// and to send the method return to that activity
			ServiceHelper.instance.callBack(doSomethingAsyncID, activityID, result);
			super.onPostExecute(result);
		}
	}

	/******************************************************************************************/
	/** Fourth example method **************************************************************************/
	/******************************************************************************************/
	/**
	 * The id of the method doSomethingAsynch
	 * It has to be unique for your whole application and for each service'method of it.
	 */
	public static final int doSomethingAsyncSerialID = 10000104;

	/**
	 * A dummy method that don't need to send data back to the calling activity
	 */
	public void doSomethingAsynchSerial(String activityId) {
		// do your treatment
		new DoSomethingAsyncSerialTask().execute(activityId);
	}

	/**
	 * Just a dummy counter (it also checks that the service die if it reset to 0 each time the application is launched)
	 */
	private int asyncSerialCallNumber = 0;;

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to do a dummy async treatment just to show you how to code it.
	 *        The only trick is to have a field String activityID to call back the calling activity
	 *        and to give this parameter to the execute method of the asyncTask
	 */
	private class DoSomethingAsyncSerialTask extends AsyncTask<String, Void, Object> {
		/**
		 * The activityId to call back it
		 * Needed on every AsyncTask you declare
		 */
		private String activityID = null;

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Object doInBackground(String... activityId) {
			// ok do a sleep
			String data = null;
			activityID = activityId[0];
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e("DummyService", "doSomethingAsyncTask:Exception", e);
			} finally {
				asyncSerialCallNumber++;
				data = Integer.valueOf(asyncSerialCallNumber).toString();
			}
			return data;
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Object result) {
			// And the important part is here
			// When your treatment is over, just use the serviceHelper to contact the calling Activity
			// and to send the method return to that activity
			ServiceHelper.instance.callBack(doSomethingAsyncSerialID, activityID, result);
			super.onPostExecute(result);
		}
	}
}

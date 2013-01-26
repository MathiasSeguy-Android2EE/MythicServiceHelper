/**
 * <ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.generic</li>
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

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.android2ee.formation.service.helper1.MAppInstance;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to:
 * <ul><li></li></ul>
 */
public enum ServiceHelper {
	instance;
	/******************************************************************************************/
	/** Constants **************************************************************************/
	/******************************************************************************************/
	/**
	 * The constant to be used by intent to carry the service's method's id
	 */
	public static final String SRV_MTH_ID = "com.android2ee.service.method.id";
	/**
	 * The constant to be used by intent to carry the service's method's result
	 */
	public static final String SRV_MTH_RES = "com.android2ee.service.method.result";
	/**
	 * The constant to be used by intent to carry the service's method's result
	 */
	public static final String SRV_MTH_RES_TYPE = "com.android2ee.service.method.result.type";

	/**
	 * Constant to be used to type the intent to set the data instanceof,
	 * Here the String
	 */
	public static final String String = "String";
	/**
	 * Constant to be used to type the intent to set the data instanceof,
	 * Here the Parcelable
	 */
	public static final String Parcelable = "Parcelable";

	/******************************************************************************************/
	/** CallBack **************************************************************************/
	/******************************************************************************************/

	/**
	 * @param serviceMethodId
	 * @param activityId
	 * @param result
	 */
	public void callBack(int serviceMethodId, String activityId, Object result) {
		Log.e("ServiceHelper","callBack, called: "+activityId+" res : "+result);
		Intent callBack = new Intent(activityId);
		callBack.putExtra(SRV_MTH_ID, serviceMethodId);
		//your object should implements Parcelable
		if (result instanceof Parcelable) {
			callBack.putExtra(SRV_MTH_RES, (Parcelable) result);
			callBack.putExtra(SRV_MTH_RES_TYPE,Parcelable);
		}else if( result instanceof String) {
			callBack.putExtra(SRV_MTH_RES, (String) result);
			callBack.putExtra(SRV_MTH_RES_TYPE,String);
		}//and so on
		MAppInstance.ins.get().sendBroadcast(callBack);
	}
}

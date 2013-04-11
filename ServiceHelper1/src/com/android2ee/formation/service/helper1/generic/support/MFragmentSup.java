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
package com.android2ee.formation.service.helper1.generic.support;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims toThis class aims to be the Fragment to inherits when using the ServiceHelper<br/>
 *        You have to take care of the import of the activity especially if your using the compat
 *        library.<br/>
 *        This class just hide the fact that your fragment will be listening for intent named with
 *        the fragmentID using its mother activity
 *        that you pass when implementing the method getFragmentId.
 *        
 *        TO BE USED ONLY BY FRAGMENT FROM SUPPORT LIBRARY
 */
public abstract class MFragmentSup extends Fragment {

	/**
	 * @return a unique identifier of the fragment to be attached to intents that have to be
	 *         calledBack by the fragment
	 */
	public String getFragmentId() {
		// so use the canonical name it should be enough
		return getClass().getCanonicalName();
	}

	/**
	 * This method has to be implemented by your own fragment<br/>
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

	/******************************************************************************************/
	/** Managing the link with the MFragmentActivity **************************************************************************/
	/******************************************************************************************/
	/**
	 * The parent activity
	 */
	MFragmentActivity parentActivity = null;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MFragmentActivity) {
			parentActivity = ((MFragmentActivity) activity);
			parentActivity.addMFragment(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		if (null != parentActivity) {
			parentActivity.removeMFragment(this);
			parentActivity = null;
		}
	}

}

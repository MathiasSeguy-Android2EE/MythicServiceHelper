/**
 * <ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.generic.shelper</li>
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
package com.android2ee.formation.service.helper.lib.serviceHelper;

import com.android2ee.formation.service.helper.lib.MService;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be used to lazy load services.
 */
public abstract class OnServiceCallBack {
	/**
	 * Just implement the call of the method of the service for exemple:<br/>
	 * public void onServiceCallBack(MService service) {<br/>
	 * //When the service is instanciate this method is called<br/>
	 * //so just call the method you want of your service<br/>
	 * ((DummyService) service).doSomething(getActivityId());<br/>
	 * }
	 * 
	 * @param service
	 */
	public abstract void onServiceCallBack(MService service);
}

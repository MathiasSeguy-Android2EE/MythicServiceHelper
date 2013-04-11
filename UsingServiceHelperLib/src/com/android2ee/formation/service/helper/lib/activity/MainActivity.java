package com.android2ee.formation.service.helper.lib.activity;

import java.io.Serializable;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android2ee.formation.service.helper.lib.MAppInstance;
import com.android2ee.formation.service.helper.lib.MService;
import com.android2ee.formation.service.helper.lib.R;
import com.android2ee.formation.service.helper.lib.legacy.MActivityLeg;
import com.android2ee.formation.service.helper.lib.service.loader.ServiceLoader;
import com.android2ee.formation.service.helper.lib.service.services.DummyService;
import com.android2ee.formation.service.helper.lib.serviceHelper.OnServiceCallBack;
import com.android2ee.formation.service.helper.lib.transverse.pojo.ConstantData;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to explain how to implements the librairy in your own code.<br/>
 *        So it's just a simple activity that launch a service when clicking on a button and when
 *        the service returns (using callback and intent, transparent for you) you use that return
 *        to do a stuff (displaying it in a tewtView for exemple)<br/>
 */
public class MainActivity extends MActivityLeg {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The TextView that display the result of the simple service call
	 */
	TextView txvResultSimple = null;
	/**
	 * The progress bar that is displayed when waiting for service call back for resultSimple
	 */
	ProgressBar prbResultSimple = null;
	/**
	 * To count the number of call done to the simple service
	 * This helps displaying or not the ProgressBar
	 */
	private int simpleServiceCall = 0;
	/**
	 * The TextView that display the result of the service call where the method do an
	 * asyncTreatment
	 */
	TextView txvResultAsync = null;
	/**
	 * The progress bar that is displayed when waiting for service call back for resultAsync
	 */
	ProgressBar prbResultAsync = null;
	/**
	 * To count the number of call done to the simple service.
	 * This helps displaying or not the ProgressBar
	 */
	private int asyncServiceCall = 0;
	/**
	 * The TextView that display the result of the service call where the method do an
	 * asyncTreatment
	 */
	TextView txvResultAsyncSerial = null;
	/**
	 * The progress bar that is displayed when waiting for service call back for resultAsync
	 */
	ProgressBar prbResultAsyncSerial = null;
	/**
	 * To count the number of call done to the simple service.
	 * This helps displaying or not the ProgressBar
	 */
	private int asyncSerialCall = 0;
	

	/******************************************************************************************/
	/** Life cycle methods **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// usual
		Log.d("MainActivity", "onCreate, serv: ");
		setContentView(R.layout.activity_main);
		// Call a simple method of a business service
		txvResultSimple = (TextView) findViewById(R.id.txvServiceResult);
		prbResultSimple = (ProgressBar) findViewById(R.id.pbrTxvServiceSimpleResult);
		Button btnLaunxchServ = (Button) findViewById(R.id.btnCallService);
		btnLaunxchServ.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchService();
			}
		});
		// Call an async method (which uses a treatment thread) of a business service
		txvResultAsync = (TextView) findViewById(R.id.txvServiceResultAsync);
		prbResultAsync = (ProgressBar) findViewById(R.id.pbrTxvServiceAsyncResult);
		Button btnLaunchServAsync = (Button) findViewById(R.id.btnCallServiceAsync);
		btnLaunchServAsync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchServiceAsync();
			}
		});
		// Call an async method (which uses a treatment thread) of a business service
		//and get in return a serializabled object
		txvResultAsyncSerial = (TextView) findViewById(R.id.txvServiceResultAsyncSerial);
		prbResultAsyncSerial = (ProgressBar) findViewById(R.id.pbrTxvServiceAsyncSerialResult);
		Button btnLaunchServAsyncSerial = (Button) findViewById(R.id.btnCallServiceAsyncSerial);
		btnLaunchServAsyncSerial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchServiceAsyncSerial();
			}
		});
		
		// Call a simple method of a business service that has no return
		Button btnLaunchServButton = (Button) findViewById(R.id.btnCallServiceNoCB);
		btnLaunchServButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchServiceNoCB();
			}
		});
	}

	/**
	 * This method has to be called when the application died.<br/>
	 * You need to track the end of your application to kill all the services<br/>
	 * So find the activity that quit the application and add that method
	 */
	@Override
	public void onBackPressed() {
		Log.w("MainActivity", "MainActivity:onBackPressed() called");
		// Call the application object and release the services
		MAppInstance.ins.get().onBackPressed();
		// and the super of course
		super.onBackPressed();
	}

	/******************************************************************************************/
	/** Calling Services **************************************************************************/
	/******************************************************************************************/

	/**
	 * The method to show how to launch a service.<br/>
	 * For example we use String res=myService.doSomething;<br/>
	 * The way to do that is the following method<br/>
	 */
	private void launchService() {
		Log.v("MainActivity", "launchService called");
		// find your service...
		// hey, wait, why it's so confuse, just to find a reference on my service...
		// It's because service are lazy loaded, so getDService returns null when first called, you
		// can call it directly.
		// it's why you have to implement this pattern using a callback where you call the method on
		// it
		ServiceLoader.instance.getdService(
		// So a ServiceCallBack is a simple object with only one abstract method
				new OnServiceCallBack() {
					// In the body of this method you just have to call your service.
					// there should be only one line that is the call the the method's service
					public void onServiceCallBack(MService service) {
						// When the service is instanciate this method is called
						Log.d("MainActivity", "launchService:OnServiceCallBack, serv: " + service);
						// so just call the method you want of your service
						((DummyService) service).doSomething(getActivityId());
					}
				});
		// You can also display a indeterminate progress bar
		simpleServiceCall++;
		prbResultSimple.setVisibility(View.VISIBLE);
	}

	/**
	 * The method to show how to launch a service.<br/>
	 * The method called in the example has a beckground thread that do a stuff in an async way
	 * For example we use String res=myService.doSomething;<br/>
	 * The way to do that is the following method<br/>
	 */
	private void launchServiceAsync() {
		Log.v("MainActivity", "launchService called");
		// find your service...
		ServiceLoader.instance.getdService(new OnServiceCallBack() {
			public void onServiceCallBack(MService service) {
				// so just call the method you want of your service
				((DummyService) service).doSomethingAsynch(getActivityId());
			}
		});
		// You can also display a indeterminate progress bar
		asyncServiceCall++;
		prbResultAsync.setVisibility(View.VISIBLE);
	}

	/**
	 * The method to show how to launch a service.<br/>
	 * The method called in the example has a beckground thread that do a stuff in an async way
	 * For example we use String res=myService.doSomething;<br/>
	 * The way to do that is the following method<br/>
	 */
	private void launchServiceAsyncSerial() {
		Log.v("MainActivity", "launchService called");
		// find your service...
		ServiceLoader.instance.getdService(new OnServiceCallBack() {
			public void onServiceCallBack(MService service) {
				// so just call the method you want of your service
				((DummyService) service).doSomethingAsynchSerial(getActivityId());
			}
		});
		// You can also display a indeterminate progress bar
		asyncSerialCall++;
		prbResultAsyncSerial.setVisibility(View.VISIBLE);
	}

	
	/**
	 * The method to show how to launch a service.<br/>
	 * For example we use String res=myService.doSomething;<br/>
	 * The way to do that is the following method<br/>
	 */
	private void launchServiceNoCB() {
		Log.v("MainActivity", "launchService called");
		// find your service...
		ServiceLoader.instance.getdService(new OnServiceCallBack() {
			public void onServiceCallBack(MService service) {
				// so just call the method you want of your service
				((DummyService) service).doSomethingWithoutCallBack();
			}
		});
	}

	/******************************************************************************************/
	/** Retrieving results from business services calls *********************************************/
	/******************************************************************************************/

	/**
	 * The method to implement to root your service call with the method that handle the return.<br/>
	 * It's called back via intent through MActivity<br/>
	 * So you just need to check your method id and do something with your result<br/>
	 * 
	 * @param serviceMethodId
	 * @param result
	 */
	protected void onServiceCallBack(int serviceMethodId, Object result) {
		switch (serviceMethodId) {
		case DummyService.doSomethingID:
			// call your result method doSomethingResult(result)
			onServiceResult((ConstantData) result);
			break;
		case DummyService.doSomethingAsyncID:
			// call your result method doSomethingResult(result)
			onAysncServiceResult((ConstantData) result);
			break;
		case DummyService.doSomethingAsyncSerialID:
			// call your result method doSomethingResult(result)
			onAysncSerialServiceResult((Serializable) result);
			break;
		case DummyService.doSomethingWithourCBID:
			// This case does not exist, there is no call back done by this method
			// there is no need to do anything;
			break;
		default:
			break;
		}
	}

	/**
	 * This method is called by onServiceCallBack that root the methodId to the treatment to do
	 * when the results is obtained.<br/>
	 * This method is fake, you should do your own stuff, it's here you handle your method's service
	 * return<br/>
	 * I just update the textView with the object pass in parameter<br/>
	 * 
	 * @param message
	 */
	private void onServiceResult(ConstantData message) {
		Log.d("MainActivity", "onServiceResult, called: " + message.message + "," + message.entier + ","
				+ message.boleen);
		// You can also display a indeterminate progress bar
		simpleServiceCall--;
		if (simpleServiceCall == 0) {
			prbResultSimple.setVisibility(View.GONE);
		}
		txvResultSimple.setText(message.message + "," + message.entier + "," + message.boleen);
	}

	/**
	 * Same as onServiceResult
	 * 
	 * @param message
	 */
	private void onAysncServiceResult(ConstantData message) {
		// You can also display a indeterminate progress bar
		asyncServiceCall--;
		if (asyncServiceCall == 0) {
			prbResultAsync.setVisibility(View.GONE);
		}

		txvResultAsync.setText(message.message + "," + message.entier + "," + message.boleen);
	}
	/**
	 * Same as onServiceResult
	 * 
	 * @param message
	 */
	private void onAysncSerialServiceResult(Serializable message) {
		// You can also display a indeterminate progress bar
		asyncSerialCall--;
		if (asyncSerialCall == 0) {
			prbResultAsyncSerial.setVisibility(View.GONE);
		}

		txvResultAsyncSerial.setText("Int:"+((String)message)+"A to String on the serializable "+message.toString());
	}

	/******************************************************************************************/
	/** Others stuff of the activity **************************************************************************/
	/******************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

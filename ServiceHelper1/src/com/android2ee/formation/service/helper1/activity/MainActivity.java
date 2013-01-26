package com.android2ee.formation.service.helper1.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android2ee.formation.service.helper1.R;
import com.android2ee.formation.service.helper1.generic.MActivity;
import com.android2ee.formation.service.helper1.generic.MService;
import com.android2ee.formation.service.helper1.generic.shelper.OnServiceCallBack;
import com.android2ee.formation.service.helper1.generic.shelper.ServiceLoader;
import com.android2ee.formation.service.helper1.service.services.DummyService;
import com.android2ee.formation.service.helper1.transverse.pojo.ConstantData;

public class MainActivity extends MActivity {
	TextView txvResult=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("MainActivity","onCreate, serv: ");
		setContentView(R.layout.activity_main);
		txvResult=(TextView)findViewById(R.id.txvServiceResult);
		Button btnLaunxchServ=(Button)findViewById(R.id.btnCallService);
		btnLaunxchServ.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				launchService();
			}
		});
	}
	/**
	 * The method to implement to root your service call with the method that handle the return.
	 * It's called back via intent through MActivity
	 * So you just need to check your method id and to do something with your result
	 * @param serviceMethodId
	 * @param result
	 */
	protected void onServiceCallBack(int serviceMethodId, Object result) {
		switch (serviceMethodId) {
		case DummyService.doSomethingID:
			//call your result method doSomethingResult(result)
			onServiceResult((ConstantData)result);
			break;
		default:
			break;
		}
	}
	
	/**
	 * The method to show how to launch a service.
	 * For exemple we use String res=myService.doSomething;
	 * The way to do that is the following method
	 */
	private void launchService() {
		Log.e("MainActivity","launchService called");
		ServiceLoader.instance.getdService(new OnServiceCallBack() {
			public void onServiceCallBack(MService service) {
				//When the service is instanciate this method is called
				Log.d("MainActivity","launchService:OnServiceCallBack, serv: "+service);
				((DummyService)service).doSomething(getActivityId());
			}
		});
		
	}
	/**
	 * 
	 * @param message
	 */
	private void onServiceResult(ConstantData message) {
		Log.e("MainActivity","onServiceResult, called: "+message.message+","+message.entier+","+message.boleen);
		txvResult.setText(message.message+","+message.entier+","+message.boleen);
	}

	

	/*
	 * (non-Javadoc)
	 * @see com.android2ee.formation.service.helper1.generic.MActivity#getActivityId()
	 */
	@Override
	public String getActivityId() {
		return "com.android2ee.formation.service.helper1.generic.MActivity";
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

package com.example.w3c_school.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;

public class DataTypeService extends Service{

	private ConnectivityManager cm;
	private NetworkInfo netInfo;
	
	private OnListener listener;
	
	
	public void setListener(OnListener listener) {
		this.listener = listener;
	}

	public class LocalService extends Binder {

        public DataTypeService getService() {
            return DataTypeService.this;
        }
    }
	
	@Override
	public IBinder onBind(Intent arg0) {
		return new LocalService();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		
	}

	public void getDataType(){
		netInfo = cm.getActiveNetworkInfo(); 
		String type = "";
		if(!netInfo.isAvailable())
			return ;
		if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){
			type = "m";
		}else if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
			type = "w";
		}else{
			type = "o";
		}
		listener.getTip(type);
		
		
	}

	public interface OnListener{
		public void getTip(String type);
	}
}

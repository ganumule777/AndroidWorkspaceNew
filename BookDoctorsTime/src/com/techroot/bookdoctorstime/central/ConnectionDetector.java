package com.techroot.bookdoctorstime.central;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class ConnectionDetector 
{	     
	    private Context _context;
	     
	    public ConnectionDetector(Context context){
	        this._context = context;
	    }
	 
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              
	              NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
	              if (null != activeNetwork) {
	                  if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
	                      return true;
	                   
	                  if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
	                      return true;
	              } 
	              else
	              {
	            	  return false;
	              }
	              

	 
	          }
	          return false;
	    }
	
}

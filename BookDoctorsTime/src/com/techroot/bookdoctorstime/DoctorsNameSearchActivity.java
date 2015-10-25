package com.techroot.bookdoctorstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class DoctorsNameSearchActivity extends Activity {

	ArrayList<Doctor>  serverDocListByName;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors_name_search);
		EditText etSearchDoctorName=(EditText)findViewById(R.id.etSearchDocName);
		etSearchDoctorName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		  
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				 if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
			            
					 // if keyboard search is click on
					 	EditText etDocName=(EditText)findViewById(R.id.etSearchDocName);
						
					 	if(etDocName.getText().toString().isEmpty())
					 	{
					 		// WebServer Request URL
						    String serverURL = LeverageConstants.URL+"getDocByName/"+"-1";
							// Use AsyncTask execute Method To Prevent ANR Problem
							new GetDocByName().execute(serverURL);
					 	}
					 	else
					 	{
					 	// WebServer Request URL
					 		String strName=etDocName.getText().toString();
					 		strName=strName.replace(" ","%20");
					 		
					 	// WebServer Request URL
						    String serverURL = LeverageConstants.URL+"getDocByName/"+strName;
							// Use AsyncTask execute Method To Prevent ANR Problem
							new GetDocByName().execute(serverURL);
					 	}
		
			            return true;
			        }
			        return false;
			}
		});
		
		serverDocListByName=new ArrayList<Doctor>();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors_name_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_doctors_name_search, container, false);
			return rootView;
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	onSearchClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to open tab window
	 ** Creation Date	:	06/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void onSearchClick(View v) 
	{
		try 
		{
			EditText etDocName=(EditText)findViewById(R.id.etSearchDocName);
			
			if(etDocName.getText().toString().isEmpty())
		 	{
		 		// WebServer Request URL
			    String serverURL = LeverageConstants.URL+"getDocByName/"+"-1";
				// Use AsyncTask execute Method To Prevent ANR Problem
				new GetDocByName().execute(serverURL);
		 	}
		 	else
		 	{
		 	// WebServer Request URL
		 		String strName=etDocName.getText().toString();
		 		strName=strName.replace(" ","%20");
		 		
			    String serverURL = LeverageConstants.URL+"getDocByName/"+strName;
				// Use AsyncTask execute Method To Prevent ANR Problem
				new GetDocByName().execute(serverURL);
		 	}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetCategoryDetails
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	private class GetDocByName extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(DoctorsNameSearchActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);

		}
	
		@Override
		protected Void doInBackground(String... arg0) {
			/************ Make Post Call To Web Server ***********/
	
			// HttpGet request = new HttpGet(arg0[0]);
			HttpClient httpClient;
			httpClient = new DefaultHttpClient();
			HttpResponse response;		
			try 
			{
				HttpContext localContext = new BasicHttpContext();
				HttpGet httpGet = new HttpGet(arg0[0]);
				response= httpClient.execute(httpGet, localContext);				 
				HttpEntity entity = response.getEntity();							
				InputStream inputStream = null;
				inputStream= entity.getContent();					
			
				if(inputStream != null)
				result = convertStreamToString(inputStream);							
						
				serverDocListByName.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDocListByName=li;
				}
		
				Content=result;				
												
			} 
			catch (Exception e) 
			{		
				Content=result;
				System.out.println(e.getMessage());
			}
			return null;		
		}
	
		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.
	
			// Close progress dialog
			Dialog.dismiss();
			try 
			{
				if(!(null==Content))
				{
				if (!("error").equals(Content.trim())) 
					{													
							
						if(serverDocListByName.isEmpty())
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoctorsNameSearchActivity.this);
				     		alertDialogBuilder.setTitle("No Details Found");
				     		alertDialogBuilder.setMessage("Record not found for entered criteria. Please try again.");
				     	// setup a dialog window
				     		alertDialogBuilder
				     				.setCancelable(false)
				     				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				     					public void onClick(DialogInterface dialog, int id) {
				     					
			     						
			     					}
			    					});		      
					 		// create an alert dialog
					 		AlertDialog alert = alertDialogBuilder.create();
					 		alert.show();
						}
						else
						{
							Intent intent=new Intent(DoctorsNameSearchActivity.this,DoctorsProfileActivity.class);							
						    intent.putExtra("docSearchList",serverDocListByName );				
							startActivity(intent);		
						}
				    }					
					else 
					{
						// Show Response Json On Screen (activity)
						throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
					}
			
				}
				else
				{
					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
				}
			} catch (LeverageNonLethalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
			} catch (IOException e) {
			}
			Log.i("*************Response*************** :: ", sb.toString());
			return sb.toString();
		}
		
	}
	
	

}




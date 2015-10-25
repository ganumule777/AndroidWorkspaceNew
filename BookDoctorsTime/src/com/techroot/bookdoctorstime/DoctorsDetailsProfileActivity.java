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
import com.techroot.bookdoctorstime.AppointmentActivity.GetDocByDocIdDetails;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.doctorManagement.service.DoctorService;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DoctorsDetailsProfileActivity extends ActionBarActivity {

	private String strDocId;
	private ArrayList<Doctor> serverDoctorsDetails;
	 DoctorService objDoctorService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors_details_profile);

		strDocId=getIntent().getExtras().getString("docId");

		serverDoctorsDetails=new ArrayList<Doctor>();
		// WebServer Request URL
	    String serverURL = LeverageConstants.URL+"getDocDetailsPrfl/"+strDocId;
		// Use AsyncTask execute Method To Prevent ANR Problem
		new GetDocProfileDetails().execute(serverURL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors_details_profile, menu);
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
			View rootView = inflater
					.inflate(R.layout.fragment_doctors_details_profile,
							container, false);
			return rootView;
		}
	}

	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	09-12-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDocProfileDetails
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	public class GetDocProfileDetails extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(DoctorsDetailsProfileActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDocId", strDocId);

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
						
				serverDoctorsDetails.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDoctorsDetails=li;
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
							
							if(!serverDoctorsDetails.isEmpty())
							{			
								populateDocDetails(serverDoctorsDetails.get(0));
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
				finish();
	            Toast.makeText(DoctorsDetailsProfileActivity.this,e.getExceptionMsg(), Toast.LENGTH_SHORT).show();	
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
	
	
	/*******************************************************************************
	 ** Function Name   :	populateDocDetails
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to populate doc details
	 ** Creation Date	:	13/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void populateDocDetails(Doctor doctorListItem) 
	{
		try
		{
			TextView tvDocName=(TextView)findViewById(R.id.tvDocNameDetails);
			TextView tvDocProfile=(TextView)findViewById(R.id.tvProfile);
			tvDocName.setText(doctorListItem.getDocFirstName()+" "+doctorListItem.getDocLastName());
		
			if(null==doctorListItem.getDocDetailsProfile())
			{
				tvDocProfile.setText("Profile details not present.");
			}
			else
			{
				tvDocProfile.setText(doctorListItem.getDocDetailsProfile());
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

	
	
}

package com.techroot.bookdoctorstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.renderscript.Type;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.os.Build;

import com.techroot.bookdoctorstime.specialityManagement.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class SpecializationSearchActivity extends Activity {

    ArrayList<Speciality> serverSpecialityAllList;

	private ArrayList<String>  arrSpecialityList;
	
	Spinner spSpecialityList;
	Spinner spArea;
	Spinner spCity;
    ArrayAdapter<String> spinnerAdapter1;
    ArrayAdapter<String> spinnerAdapter2 ;
    ArrayAdapter<String> spinnerAdapter7;
    
    
	ArrayList<Doctor>  serverStateList;
	ArrayList<Doctor>  serverCityList;
	ArrayList<Doctor>  serverAreaList;
	ArrayList<Doctor>  serverDocListbySpeciality;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specialization_search);
	
	       spinnerAdapter1 = new ArrayAdapter<String>(SpecializationSearchActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
		   spinnerAdapter2 = new ArrayAdapter<String>(SpecializationSearchActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
		   spinnerAdapter7 = new ArrayAdapter<String>(SpecializationSearchActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);

		 // download category first from server
		   try
		   {
	       arrSpecialityList=new ArrayList<String>();
	       serverStateList=new ArrayList<Doctor>();
	       serverCityList=new ArrayList<Doctor>();

	       serverAreaList=new ArrayList<Doctor>();
	       serverDocListbySpeciality=new ArrayList<Doctor>();
	       	    
	      

	       spCity=(Spinner)findViewById(R.id.spCity);
		   spArea=(Spinner)findViewById(R.id.spArea);
	       
		   spinnerAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   spCity.setAdapter(spinnerAdapter7);
		   spinnerAdapter7.add("Select Speciality");
   
		   spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   spCity.setAdapter(spinnerAdapter1);
		   spinnerAdapter1.add("Select City");
	       
		   spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   spArea.setAdapter(spinnerAdapter2);
		   spinnerAdapter2.add("Select Area");
		   
		  
		   
		   // WebServer Request URL
		   String serverURL = LeverageConstants.URL+"getSpec/"+"-1";
		   // Use AsyncTask execute Method To Prevent ANR Problem
		   new GetSpecialityList().execute(serverURL);
		   }
		   catch(Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.specialization_search, menu);
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
					R.layout.fragment_specialization_search, container, false);
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
			
			//
			spSpecialityList=(Spinner)findViewById(R.id.spSpecializationList);
			spCity=(Spinner)findViewById(R.id.spCity);
			spArea=(Spinner)findViewById(R.id.spArea);
			
			String strArea;
			String strCity;
			String strState;
			String strSpeciality;
			
			if(("Select Speciality").equals(spSpecialityList.getSelectedItem().toString()))
			{
				strSpeciality="-1";
			}
			else
			{
				strSpeciality=spSpecialityList.getSelectedItem().toString().trim();
				strSpeciality=strSpeciality.replace(" ", "%20");
			}
			
			
			
			if(("Select Area").equals(spArea.getSelectedItem().toString()))
			{
				strArea="-1";
			}
			else
			{
				strArea=spArea.getSelectedItem().toString().trim();
				strArea=strArea.replace(" ", "%20");

			}
			
			if(("Select City").equals(spCity.getSelectedItem().toString()))
			{
				strCity="-1";
			}
			else
			{
				strCity=spCity.getSelectedItem().toString().trim();
				strCity=strCity.replace(" ", "%20");

			}
				
			// WebServer Request URL
		    String serverURL = LeverageConstants.URL+"getSpec1/"+strSpeciality+"/"+strCity+"/"+strArea;
			// Use AsyncTask execute Method To Prevent ANR Problem
			new GetDocDetailsBySpeciality().execute(serverURL);
				
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
	    private class GetSpecialityList extends AsyncTask<String, Void, Void> 
		{
			RequestParams params = new RequestParams();
			private final HttpClient Client = new DefaultHttpClient();
			private String Content;
			String result;
			private String Error = null;
			private ProgressDialog Dialog = new ProgressDialog(SpecializationSearchActivity.this);
			
			protected void onPreExecute() {
				// NOTE: You can call UI Element here.
				// Start Progress Dialog (Message)
				Dialog.setMessage("Please wait..");
				Dialog.show();
				Dialog.setIndeterminate(false);
	  			Dialog.setCancelable(false);
				params.add("mainCategory", "");
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
							
					
					if(!("error").equals(result.trim()))
					{
						// Converting gson to List of given elemnts
						Gson gson = new Gson(); 
						ArrayList<Speciality> li=gson.fromJson(result, new TypeToken<List<Speciality>>(){}.getType());									
						serverSpecialityAllList=li;
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
							populateSpeciality(serverSpecialityAllList);	
							
							populateStateCityArea();
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
	
	/*******************************************************************************
	 ** Function Name   :	populateSpeciality
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to populate speciality list
	 ** Creation Date	:	11-11-2014
	 ** Arguments		:	List Of Speciality
	 ** Return Type     :	void
	 *******************************************************************************/
	public void populateSpeciality(ArrayList<Speciality> listSpeciality) 
	{
		// call method which download post details from server and populate it on dashboard	
		//getPostListFromServer();		
		arrSpecialityList.clear();
		 // add onselected listner on spinner
		spSpecialityList=(Spinner)findViewById(R.id.spSpecializationList);
	    spinnerAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spSpecialityList.setAdapter(spinnerAdapter7);
	    spinnerAdapter7.notifyDataSetChanged();
	    // populate list in spinner
	    for(int iCount=0;iCount<serverSpecialityAllList.size();iCount++)
	    {					    					    	
	    	spinnerAdapter7.add(serverSpecialityAllList.get(iCount).getSpeciality());
	    	arrSpecialityList.add(serverSpecialityAllList.get(iCount).getSpecialityId());				    	
	    }		
	    
	    spSpecialityList.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	        	
	        	populateStateCityArea();
	            // your code here
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parentView) {
	            // your code here
	        }

	    });
	    
	}
	
	
	/*******************************************************************************
	 ** Function Name   :	populateSpeciality
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to populate speciality list
	 ** Creation Date	:	11-11-2014
	 ** Arguments		:	List Of Speciality
	 ** Return Type     :	void
	 *******************************************************************************/
	public void populateStateCityArea() 
	{		
		try
		{	
			   spSpecialityList=(Spinner)findViewById(R.id.spSpecializationList);

			   String serverURL = LeverageConstants.URL+"getCity/"+spSpecialityList.getSelectedItem().toString();
			   // Use AsyncTask execute Method To Prevent ANR Problem
			   new GetCity().execute(serverURL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
//	 /*********************************************************************************
//	    **********************************************************************************
//	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
//	    ** Author :         	Ganesh Mule
//	    ** Created on :     	28-09-2014
//	    ** Dept:            	Android Based Mobile App Development
//	    ** Class:          		GetCategoryDetails
//	    ** Description:     	This is inner class used to call suitable http method from server and get its result
//	    *						Here we call method to get category details from server
//	    ***********************************************************************************
//	    ***********************************************************************************/  
//	    private class GetState extends AsyncTask<String, Void, Void> 
//		{
//			RequestParams params = new RequestParams();
//			private final HttpClient Client = new DefaultHttpClient();
//			private String Content;
//			String result;
//			private String Error = null;
//			private ProgressDialog Dialog = new ProgressDialog(SpecializationSearchActivity.this);
//			
//			protected void onPreExecute() {
//				// NOTE: You can call UI Element here.
//				// Start Progress Dialog (Message)
//				Dialog.setMessage("Please wait..");
//				Dialog.show();
//				Dialog.setIndeterminate(false);
//	  			Dialog.setCancelable(false);		
//			}
//
//			@Override
//			protected Void doInBackground(String... arg0) {
//				/************ Make Post Call To Web Server ***********/
//
//				// HttpGet request = new HttpGet(arg0[0]);
//				HttpClient httpClient;
//				httpClient = new DefaultHttpClient();
//				HttpResponse response;		
//				try 
//				{
//					HttpContext localContext = new BasicHttpContext();
//	  				HttpGet httpGet = new HttpGet(arg0[0]);
//	  				response= httpClient.execute(httpGet, localContext);				 
//	  				HttpEntity entity = response.getEntity();							
//	  				InputStream inputStream = null;
//	  				inputStream= entity.getContent();					
//				
//					if(inputStream != null)
//					result = convertStreamToString(inputStream);							
//							
//					
//					if(!("error").equals(result.trim()))
//					{
//						// Converting gson to List of given elemnts
//						Gson gson = new Gson(); 
//						ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
//						serverStateList=li;
//					}
//			
//					Content=result;				
//													
//				} 
//				catch (Exception e) 
//				{		
//					Content=result;
//					System.out.println(e.getMessage());
//				}
//				return null;		
//			}
//
//			protected void onPostExecute(Void unused) {
//				// NOTE: You can call UI Element here.
//
//				// Close progress dialog
//				Dialog.dismiss();
//				try 
//				{
//					if(!(null==Content))
//					{
//					if (!("error").equals(Content.trim())) 
//						{													
//							 // add onselected listner on spinner
//							spState=(Spinner)findViewById(R.id.spState);
//						    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//						    spState.setAdapter(spinnerAdapter);
//						    spinnerAdapter.notifyDataSetChanged();	    
//						    // populate list in spinner
//						    for(int iCount=0;iCount<serverStateList.size();iCount++)
//						    {			
//
//						    	
//						    	if(!(null==serverStateList.get(iCount).getState()))
//						    	{
//							    	spinnerAdapter.add(serverStateList.get(iCount).getState());
//
//						    	}						    	
//					    	}										
//					    }					
//						else 
//						{
//							// Show Response Json On Screen (activity)
//							throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
//						}
//					}
//					else
//					{
//						throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
//					}
//					
//					spState.setOnItemSelectedListener(new OnItemSelectedListener() {
//					    
//						@Override
//						public void onItemSelected(AdapterView<?> arg0,
//								View arg1, int arg2, long arg3) {
//							// TODO Auto-generated method stub
//							populateCity();
//						}
//
//						@Override
//						public void onNothingSelected(AdapterView<?> arg0) {
//							// TODO Auto-generated method stub
//							
//						}
//
//					});
//					
//				} catch (LeverageNonLethalException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//			private String convertStreamToString(InputStream is) {
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(is));
//				StringBuilder sb = new StringBuilder();
//				String line = null;
//				try {
//					while ((line = reader.readLine()) != null) {
//						sb.append(line + "\n");
//					}
//					is.close();
//				} catch (IOException e) {
//				}
//				Log.i("*************Response*************** :: ", sb.toString());
//				return sb.toString();
//			}
//			
//		}
	    
	    /*********************************************************************************
		    **********************************************************************************
		    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
		    ** Author :         	Ganesh Mule
		    ** Created on :     	28-09-2014
		    ** Dept:            	Android Based Mobile App Development
		    ** Class:          		GetCity
		    ** Description:     	This is inner class used to call suitable http method from server and get its result
		    *						Here we call method to get category details from server
		    ***********************************************************************************
		    ***********************************************************************************/  
		    private class GetCity extends AsyncTask<String, Void, Void> 
			{
				RequestParams params = new RequestParams();
				private final HttpClient Client = new DefaultHttpClient();
				private String Content;
				String result;
				private String Error = null;
				private ProgressDialog Dialog = new ProgressDialog(SpecializationSearchActivity.this);
				
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
								
						
						if(!("error").equals(result.trim()))
						{
							// Converting gson to List of given elemnts
							Gson gson = new Gson(); 
							ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
							serverCityList=li;
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
								
							    
							    /// populate city
							    // add onselected listner on spinner
								spCity=(Spinner)findViewById(R.id.spCity);
							    spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							    spCity.setAdapter(spinnerAdapter1);
							    spinnerAdapter1.notifyDataSetChanged();
			    
							    // populate list in spinner
							    for(int iCount=0;iCount<serverCityList.size();iCount++)
							    {			

							    	if(!(null==serverCityList.get(iCount).getCity()))
							    	{
								    	spinnerAdapter1.add(serverCityList.get(iCount).getCity());

							    	}
							    	
						    	}	
							    if(serverCityList.isEmpty())
							    {
							    	spinnerAdapter1.clear();
							    	spinnerAdapter1.add("Select City");
							    }
							    
						    }					
							else 
							{
								// Show Response Json On Screen (activity)
								throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
							}


						spCity.setOnItemSelectedListener(new OnItemSelectedListener() {
						    
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								populateArea();
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}

						});
						
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
	private class GetArea extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(SpecializationSearchActivity.this);
		
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
				serverAreaList.clear();
				
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverAreaList=li;
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
								    
					    /// populate area
					    // add onselected listner on spinner
						spArea=(Spinner)findViewById(R.id.spArea);
					    spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    spArea.setAdapter(spinnerAdapter2);
					    spinnerAdapter2.notifyDataSetChanged();
					    
					    // populate list in spinner
					    for(int iCount=0;iCount<serverAreaList.size();iCount++)
					    {				
					    				    	
					    	if(!(null==serverAreaList.get(iCount).getArea()))
					    	{
						    	spinnerAdapter2.add(serverAreaList.get(iCount).getArea());
	
					    	}
				    	}
					    
					    if(serverAreaList.isEmpty())
					    {
					    	spinnerAdapter2.clear();
					    	spinnerAdapter2.add("Select Area");
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
	

	public void populateArea() 
	{
		try
		{
			   spSpecialityList=(Spinner)findViewById(R.id.spSpecializationList);
			   spCity=(Spinner)findViewById(R.id.spCity);
			   String serverURL = LeverageConstants.URL+"getArea/"+spCity.getSelectedItem().toString()+"/"+spSpecialityList.getSelectedItem().toString();
			   // Use AsyncTask execute Method To Prevent ANR Problem
			   new GetArea().execute(serverURL);
		}
		catch(Exception e)
		{
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
	private class GetDocDetailsBySpeciality extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(SpecializationSearchActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);

			params.add("strSpeciality", spSpecialityList.getSelectedItem().toString().trim());
			params.add("strCity", spCity.getSelectedItem().toString());
			params.add("strArea", spArea.getSelectedItem().toString());

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
						
				serverDocListbySpeciality.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDocListbySpeciality=li;
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
							
						if(serverDocListbySpeciality.isEmpty())
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpecializationSearchActivity.this);
				     		alertDialogBuilder.setTitle("No Details Found");
				     		alertDialogBuilder.setMessage("Record not found for selected criteria. Please try again.");
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
							Intent intent=new Intent(SpecializationSearchActivity .this,DoctorsProfileActivity.class);							
						    intent.putExtra("docSearchList",serverDocListbySpeciality );				
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

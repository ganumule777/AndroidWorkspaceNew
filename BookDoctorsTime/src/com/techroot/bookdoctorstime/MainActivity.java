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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.doctorManagement.service.DoctorService;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity  implements AnimationListener{

	// Animation
    Animation animFadein;
	
    ImageButton imButton;
    
	private ArrayList<Doctor> serverDoctorsDetails;
    
    private String strDocMobNo;
    private String strDocId;

    DoctorService objDoctorService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try 
        {
			objDoctorService=new DoctorService(this);
			
		} catch (LeverageNonLethalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        serverDoctorsDetails=new ArrayList<Doctor>();
        
        imButton=(ImageButton)findViewById(R.id.imageButton1);
//        Spinner spSpeciality=(Spinner)findViewById(R.id.spSpecciality);
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
//	    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	    spSpeciality.setAdapter(spinnerAdapter);
//	    spinnerAdapter.add("Select Speciality");
//	    spinnerAdapter.add("Dentist");
        
     // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        
        // set animation listener
        animFadein.setAnimationListener(this);
        
              
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    
    /*******************************************************************************
	 ** Function Name   :	openTabWindow
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to open tab window
	 ** Creation Date	:	04/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void openTabWindow(View v) 
	{
		try 
		{
			imButton.startAnimation(animFadein);
			
		
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this,TabMainActivity.class);
		
		startActivity(intent);
	}


	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	/*******************************************************************************
	 ** Function Name   :	docRegClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to take reg details for
	 ** Creation Date	:	27/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void docRegClick(View v) 
	{
		try 
		{
			Doctor objDoctor=objDoctorService.getDoctorDetails();
			if(null==objDoctor.getDocId())
			{
				showAlertDialog();
			}
			else
			{
					strDocMobNo=objDoctor.getDocMobileNo();
					// WebServer Request URL
				    String serverURL = LeverageConstants.URL+"getDocmblNo/"+strDocMobNo;
					// Use AsyncTask execute Method To Prevent ANR Problem
					new GetDocMobNo().execute(serverURL);
					
				
			}
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	   /* *************************************
    function name : showAlertDialog()
    return type   : void
    arguments     : No arguments
    description   : This function used to show alert dialog which is having active distributor  list in spainner
    created by    : Ganesh Mule
    ****************************************** */
	public void showAlertDialog() throws LeverageNonLethalException 
	{

		 
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
 		final View promptView = layoutInflater.inflate(R.layout.loginitem, null);
 		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
 		alertDialogBuilder.setTitle("Login");
 		alertDialogBuilder.setView(promptView);
				
 		
 	    // setup a dialog window
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {					
						
						
					}
				})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) 
						{						
																				
						}
					});
	 		// create an alert dialog
	 		final AlertDialog alert = alertDialogBuilder.create(); 		
	 		alert.show();
	 		
	 		
	 		 //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
	 		alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	          {            
	                  @Override
	                  public void onClick(View v)
	                  {
	                	  try 
							{
								EditText etmblNo=(EditText)promptView.findViewById(R.id.etDocMblNo);
								strDocMobNo=etmblNo.getText().toString();
								if(etmblNo.getText().toString().isEmpty())
								{
						            Toast.makeText(MainActivity.this, "Enter mobile No.", Toast.LENGTH_SHORT).show();	

								}
								else
								{
									alert.dismiss();
									// WebServer Request URL
								    String serverURL = LeverageConstants.URL+"getDocmblNo/"+strDocMobNo;
									// Use AsyncTask execute Method To Prevent ANR Problem
									new GetDocMobNo().execute(serverURL);
									
								}	
							
							}  												
							catch (Exception e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	                  }
	              });

	        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @Override
	              public void onClick(View v)
	              {
	                  
	                 alert.dismiss();
	                  //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
	              }
	          });
	 		
	}
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDocMobNo
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	private class GetDocMobNo extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strMobNo", strDocMobNo);
						
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
								
								strDocId=serverDoctorsDetails.get(0).getDocId();
								
								Doctor objDoctor=objDoctorService.getDoctorDetails();
								if(null==objDoctor.getDocId())
								{
									if(-1==objDoctorService.addDoctorsDetails(strDocId, strDocMobNo))
									{
										//	Error in entering data in database
										throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Adding Doctors Mobile No Details");
									}
									else
									{
							            Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_SHORT).show();			            					            							            
									}
								}
								 Intent intent=new Intent(MainActivity .this,DocAppmntDetailsActivity.class);							
							     intent.putExtra("docId",serverDoctorsDetails.get(0).getDocId());				
								 startActivity(intent);
								
							}	
							else
							{
					            Toast.makeText(MainActivity.this, "Please enter correct mobile no.", Toast.LENGTH_SHORT).show();	
								showAlertDialog();
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
	
	/*******************************************************************************
	 ** Function Name   :	cancelAppmntClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to take cancel appointment
	 ** Creation Date	:	27/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void cancelAppmntClick(View v) 
	{
		try 
		{
		    showAlertDialog();
		
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* *************************************
    function name : showAlertDialog()
    return type   : void
    arguments     : No arguments
    description   : This function used to show alert dialog which is having active distributor  list in spainner
    created by    : Ganesh Mule
    ****************************************** */
	public void showCancelAppmntDialog() throws LeverageNonLethalException 
	{

		 
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
 		final View promptView = layoutInflater.inflate(R.layout.loginitem, null);
 		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
 		alertDialogBuilder.setTitle("Login");
 		alertDialogBuilder.setView(promptView);
				
 		
 	    // setup a dialog window
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {					
						
						
					}
				})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) 
						{						
																				
						}
					});
	 		// create an alert dialog
	 		final AlertDialog alert = alertDialogBuilder.create(); 		
	 		alert.show();
	 		
	 		
	 		 //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
	 		alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	          {            
	                  @Override
	                  public void onClick(View v)
	                  {
	                	  try 
							{
								EditText etmblNo=(EditText)promptView.findViewById(R.id.etDocMblNo);
								strDocMobNo=etmblNo.getText().toString();
								if(etmblNo.getText().toString().isEmpty())
								{
						            Toast.makeText(MainActivity.this, "Enter mobile No.", Toast.LENGTH_SHORT).show();	

								}
								else
								{
									alert.dismiss();
									// WebServer Request URL
								    String serverURL = LeverageConstants.URL+"getDocmblNo/"+strDocMobNo;
									// Use AsyncTask execute Method To Prevent ANR Problem
									new GetDocMobNo().execute(serverURL);
									
								}	
							
							}  												
							catch (Exception e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	                  }
	              });

	        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @Override
	              public void onClick(View v)
	              {
	                  
	                 alert.dismiss();
	                  //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
	              }
	          });
	 		
	}
	
	
}

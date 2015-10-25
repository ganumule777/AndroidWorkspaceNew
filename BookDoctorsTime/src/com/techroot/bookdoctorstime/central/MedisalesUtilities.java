/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Anshuman Singh
** Created on :     	22-08-2014
** Dept:            	Android Based Mobile App Development
** Class:          		MedisalesUtilities
** Description:     	This is the MedisalesUtilities class for the mobile app.
**
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime.central;

import java.util.Random;

import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.util.LeverageUtilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class MedisalesUtilities 
{

	String className = MedisalesUtilities.class.getName();
	String methodName = null;
	
	/*******************************************************************************
	 ** Function Name   :	hideKeyboard
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to hide keypad at the time showing toast message to user
	 ** Creation Date	:	21-04-2014
	 ** Arguments		:	SQLiteDatabase
	 ** Return Type     :	void
	 *******************************************************************************/
	public void hideKeyboard(Activity activity)
	    {
	        try
	        {
	        	// Get input method service
	            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	           
	            // then on current focus or on current activity hide keypad to show toast message
	            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	         }
	        catch (Exception e)
	        {
	            
	        }
	    }
	
	 /*******************************************************************************
		 ** Function Name   :	generateRandomId
		 ** Created By      :	Ganesh Mule
		 ** Description		:	This function is used to generate random string
		 **                     return random generated string 
		 **                     sucess mail
		 ** Creation Date	:	
		 ** Arguments		:	N.A
		 ** Return Type     :	String
	 *******************************************************************************/
	public String generateRandomId()
	{
		this.methodName="generateRandomId";
	    Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    
	    final String dCase = "abcdefghijklmnopqrstuvwxyz";
	    final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    final String intChar = "0123456789";
	    final Random r = new Random();
	    String randomId = "";
	  	
		 while (randomId.length () != 6){
	            int rPick = r.nextInt(4);
	            if (rPick == 0)
	            {
	                int spot = r.nextInt(25);
	                randomId += dCase.charAt(spot);
	            } else if (rPick == 1) 
	            {
	                int spot = r.nextInt (25);
	                randomId += uCase.charAt(spot);
	            } else if (rPick == 2) 
	            {
	                int spot = r.nextInt (9);
	                randomId += intChar.charAt(spot);
	            } 
	        }
		 
		 Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		return randomId;
	}


	
	int round (int number,int multiple){

	    int result = multiple;

	    //If not already multiple of given number

	    if (number % multiple != 0){

	        int division = (number / multiple)+1;

	        result = division * multiple;

	    }

	    return result;

	}
	
}

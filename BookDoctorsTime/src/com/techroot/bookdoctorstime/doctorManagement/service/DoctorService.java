/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Rupali Thorat
** Created on :     	
** Dept:            	Android Based Mobile App Development
** Class:          		RetailerService
** Description:     	Service class for retailer
**
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime.doctorManagement.service;

import java.text.ParseException;
import java.util.List;

import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;
import com.techroot.bookdoctorstime.doctorManagement.dao.DoctorDAO;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.util.LeverageUtilities;

import android.content.Context;
import android.util.Log;


public class DoctorService {
	
	private DoctorDAO objDoctorDAO;
	
	String className = DoctorService.class.getName();
	String methodName = null;
	
	/*******************************************************************************
	 ** Function Name   :	RetailerService
	 ** Created By      :	Rupali thorat
	 ** Description		:	Constructor 
	 ** Creation Date	:	
	 ** Arguments		:	Context context
	 ** Return Type     :	N.A
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public DoctorService(Context context) throws LeverageNonLethalException
	{
		try
		{
			this.methodName="Constructor";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	objDoctorDAO = new DoctorDAO(context);
	    	objDoctorDAO.open();
			
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		}
		catch(LeverageNonLethalException lnle)
		{
			throw lnle;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Error Connecting Database");
		}
	}
	
	
	
	

	/*******************************************************************************
	 ** Function Name   :	addDoctorsDetails
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to add Retailer details in DB
	 ** Creation Date	:	
	 ** Arguments		:	strName (Name), strAddress (Address), strVatNo(VAT Number), 
	 **						strCstTinNumber (CST Tin Number), strContactNo(Contact No)
	 **                     strEmailId(Email Id), strOTPNo(OTP Number), strStatus(OTP Status)
	 ** Return Type     :	long (primary key of the newly inserted row)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public long addDoctorsDetails(String strDocid, String strmblNo) throws LeverageNonLethalException
	{
		try
		{
		
			this.methodName="addDoctorsDetails";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
			//	Calling the DAO function for populating Details in Database
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
			//	Populating the Retailer POJO
			/* put otp number as a retailer id to keep retailer uniqueness */			
			long iResult=objDoctorDAO.addDocDetails(strDocid,strmblNo);
						
			return iResult;
		}
		catch(LeverageNonLethalException lnle)
		{
			throw lnle;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Adding Doctor Details");
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	updateRetailer
	 ** Created By      :	Rupali Thorat
	 ** Description		:	This function is called to add Retailer details in DB
	 ** Creation Date	:	
	 ** Arguments		:	strRetailerId (Retailer Id), strName (Name), strAddress (Address), 
	 **                     strVatNo(VAT Number), strCstTinNumber (CST Tin Number), 
	 **						strContactNo(Contact No), strEmailId(Email Id), 
	 **                     strOtp(OTP Number), strStatus(OTP Status)
	 ** Return Type     :	long (primary key of the newly inserted row)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public long updateRetailer(Doctor objDoctor,String otp) throws LeverageNonLethalException
	{
		try
		{
			this.methodName="updateRetailer";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));	    	
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
	    	int iResult=objDoctorDAO.updateDoctor(objDoctor);	    
	    	//	Update Retailer Distributor linkage also	    	
	    	
	    	return iResult;
		}
		catch(LeverageNonLethalException lnle)
		{
			throw lnle;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Modifying Retailer Details");
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	getDoctorDetails
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function Retailer details present in DB based on
	 **						Retailer ID
	 ** Creation Date	:	23-06-2014
	 ** Arguments		:	strRetailerId
	 ** Return Type     :	Retailer
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public Doctor getDoctorDetails() throws LeverageNonLethalException
	{
		try
		{
			this.methodName="getDoctorDetails";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
			
	    	//	Getting the staff details from DB based on Staff ID
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
			return objDoctorDAO.getDocDetailsByMobile();
		}
		catch(LeverageNonLethalException lnle)
		{
			throw lnle;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Retrieving Retailer Details");
		}
	}
	
}

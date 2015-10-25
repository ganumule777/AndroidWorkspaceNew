/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Rupali Thorat
** Created on :     	
** Dept:            	Android Based Mobile App Development
** Class:          		RetailerDAO
** Description:     	DAO class for retailer
**
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime.doctorManagement.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techroot.bookdoctorstime.central.BookDocAppmntSQLiteOpenHelper;
import com.techroot.bookdoctorstime.central.BookDocAppmntSQLiteOpenHelper;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.util.LeverageUtilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class DoctorDAO {
	
	private SQLiteDatabase database;
	private BookDocAppmntSQLiteOpenHelper objMediSalsSQLiteOpenHelper;
	String[] strRetailerColumnList = {"DOC_ID","MOBILE_NO"};
	
	String className = DoctorDAO.class.getName();
	String methodName = null;
	
	public DoctorDAO(Context context) throws LeverageNonLethalException
	{
		try
		{
			this.methodName="Constructor";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
	    	objMediSalsSQLiteOpenHelper = new BookDocAppmntSQLiteOpenHelper(context);
			
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Error Connecting Database");
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	open
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to get a writable copy of 
	 **						the database.
	 ** Creation Date	:	11-12-2014
	 ** Arguments		:	void
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void open() throws SQLException, LeverageNonLethalException 
	{
		try
		{
			this.methodName="open";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
	    	//	Getting a writable copy of database
			database = objMediSalsSQLiteOpenHelper.getWritableDatabase();
			
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_OPENING_DATABASE,"Error Opening Database");
		}
	}

	/*******************************************************************************
	 ** Function Name   :	close
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to close the database.
	 ** Creation Date	:	11-12-2014
	 ** Arguments		:	void
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void close() throws LeverageNonLethalException 
	{
		try
		{
			this.methodName="close";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
	    	objMediSalsSQLiteOpenHelper.close();
			
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Closing Database");
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	getListBasedOnCategory
	 ** Created By      :	Rupali Thorat
	 ** Description		:	This function is called to get Retailer Details
	 ** Creation Date	:	27-04-2014
	 ** Arguments		:	Retailer objRetailer
	 ** Return Type     :	List<Retailer> (return retailer object list)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public List<Doctor> getListStatus(Doctor objRetailer) throws LeverageNonLethalException 
	{
		try
		{
			this.methodName="getListBasedOnCategory";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
	    	//	Preparing the Where Clause
	    	StringBuffer strBfWhereClause = new StringBuffer("");
	    	boolean bCriteriaSpecifid=false;
	    	
	    	if(objRetailer.getDocId()!="")
	    	{
	    		strBfWhereClause.append("DOC_ID = '"+objRetailer.getDocId()+"'");
	    		bCriteriaSpecifid = true;
	    	}
//	    	if((objRetailer.getName()!="")&&(objRetailer.getName()!=null))
//	    	{
//	    		if(bCriteriaSpecifid)
//	    		{
//	    			strBfWhereClause.append("AND NAME = '"+objRetailer.getName()+"'");
//	    		}
//	    		else
//	    		{
//	    			strBfWhereClause.append("NAME = '"+objRetailer.getName()+"'");
//	    		}
//	    		bCriteriaSpecifid = true;
//	    	} 	
			List<Doctor> ltRetailerList = new ArrayList<Doctor>();
			
			//	Firing the SQL query and populating result in the cursor (ordered by STAFF_ID column)
			Cursor cursor = database.query("doc_info",strRetailerColumnList, strBfWhereClause.toString(), null, null, null, "RETAILER_ID");
			
			if(cursor.getCount()!=0)
			{
		
			//	Checking if the Query returned any result or not
				if(cursor.moveToFirst())
				{
					//	Cursor is non-empty
					//	Traversing the cursor and populating array list of staff members
					while (!cursor.isAfterLast()) 
				    {
						Doctor objResRetailer= cursorToRetailer(cursor);
						ltRetailerList.add(objResRetailer);
				    	cursor.moveToNext();
				    }
				    //	Closing the cursor
				    cursor.close();
				    
				    Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
				    
				    return ltRetailerList;
				}
				else
				{
					//	Throwing exception message
					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Retailer Details Not Found");
				}
			}
			else
			{
				 return ltRetailerList;
			}
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
	
	/*******************************************************************************
	 ** Function Name   :	cursorToUser
	 ** Created By      :	Anshuman Singh
	 ** Description		:	This function is called to convert the cursor object
	 **						to user object.
	 ** Creation Date	:	21-04-2014
	 ** Arguments		:	Cursor object
	 ** Return Type     :	User object
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	private Doctor cursorToRetailer(Cursor cursor) throws LeverageNonLethalException 
	{
		try
		{
			this.methodName="cursorToRetailer";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
			//	Populating Cursor Contents in Staff Object
	    	Doctor objRetailer = new Doctor();
		    
	    	objRetailer.setDocId(cursor.getString(0));
	    	objRetailer.setDocMobileNo(cursor.getString(1));    
		    Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		    
		    return objRetailer;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Converting Cursor to Doctor");
		}
		
	}

	/*******************************************************************************
	 ** Function Name   :	addDocDetails
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to add Retailer Details in DB
	 ** Creation Date	:	27-04-2014
	 ** Arguments		:	Retailer objRetailer
	 ** Return Type     :	long (primary key of the newly inserted row)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public long addDocDetails(String strDocId,String strmblNo) throws LeverageNonLethalException
	{
		try
		{
			this.methodName="addRetailerDetails";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
			ContentValues cvRetailer = new ContentValues();
			
			cvRetailer.put("DOC_ID", strDocId);
			cvRetailer.put("MOBILE_NO", strmblNo);

			//	Populating retailer Details in Database
			long retailerIdAfterInsert = database.insert("doc_info", null,cvRetailer);
			
			Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
			
			return retailerIdAfterInsert;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Adding Dcotor Details");
		}
	}
	
	
	
	/*******************************************************************************
	 ** Function Name   :	getDocDetailsByMobile
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to get Retailer Details
	 ** Creation Date	:	27-04-2014
	 ** Arguments		:	String strRetailerId
	 ** Return Type     :	Retailer (return retailer object)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public Doctor getDocDetailsByMobile() throws LeverageNonLethalException
	{
		try
		{
			this.methodName="getDocDetailsByMobile";
	    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
	    	
	    	//database=objMediSalsSQLiteOpenHelper.openDataBase();
	    	
			String selectQuery = "SELECT  * FROM  doc_info ";
			
			//	Firing the SQL query and populating result in the cursor (ordered by STAFF_ID column)
			Cursor cursor = database.rawQuery(selectQuery , null);
	    			
			Doctor objDoctor = new Doctor();
			//	Checking if the Query returned any result or not
			if(cursor.getCount()!=0)
			{
				if(cursor.moveToFirst())
				{
					//	Cursor is non-empty
					//	Traversing the cursor and populating the staff object
					objDoctor = cursorToRetailer(cursor);
				    
			    	//	Closing the cursor
				    cursor.close();
				    
				    Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
				    
				    return objDoctor;
				}
				else
				{
					//	Throwing exception message
					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Doctor Details Not Found");
				}
			}
			else
			{
				 return objDoctor;
			}
		}
		catch(LeverageNonLethalException lnle)
		{
			throw lnle;
		}
		catch(Exception e)
		{
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Retrieving Staff Details");
		}
	}
	
	
	/*******************************************************************************
	 ** Function Name   :	updateDoctor
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is called to update Retailer Details in DB
	 ** Creation Date	:	27-04-2014
	 ** Arguments		:	Dcotor objDoctor
	 ** Return Type     :	int (return integer value 1 if successfully updated)
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public int updateDoctor(Doctor objDoctor) throws LeverageNonLethalException
	{
			try
			{
				this.methodName="updateRetailer";
		    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
		    	
		    	int i=0;
		    	boolean flag=false;
		    	
		    	Doctor objDoctor1=this.getDocDetailsByMobile();
		    
		    	ContentValues cvRetailer = new ContentValues();
		    	
		    	
		    	if(!objDoctor.getDocMobileNo().equals(objDoctor1.getDocMobileNo()))
		    	{
		    		cvRetailer.put("MOBILE_NO", objDoctor.getDocMobileNo());
		    		flag=true;
		    	}
		    
		    	if(flag==true)
				{
				    return database.update("doc_info", cvRetailer, "DOC_ID='"+String.valueOf(objDoctor.getDocId())+"'", null);
				}
				else
				{
					return 0;
				}  
		    
	    				
		}
		catch(Exception e)
		{
			String str=e.getMessage();
			Log.d("Error", str);
			throw new LeverageNonLethalException(LeverageExceptions.ERROR_CLOSING_DATABASE,"Error Updating Retailer Details");
		}
	}

}



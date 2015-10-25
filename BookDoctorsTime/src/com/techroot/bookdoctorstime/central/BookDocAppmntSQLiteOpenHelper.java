/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Anshuman Singh
** Created on :     	21-04-2014
** Dept:            	Android Based Mobile App Development
** Class:          		ClockInSQLiteOpenHelper
** Description:     	This is the SQLiteOpenHelper class for the mobile app.
**
***********************************************************************************
***********************************************************************************/

package com.techroot.bookdoctorstime.central;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.util.LeverageUtilities;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDocAppmntSQLiteOpenHelper extends SQLiteOpenHelper 
{
	
	public static String DB_NAME; 
	public static final int DATABASE_VERSION = 1;
	//Path to the device folder with databases
    public static String DB_PATH;
    //Database file name
    public SQLiteDatabase database;
    public final Context context;
    
    
    private BookDocAppmntSQLiteOpenHelper objMediSalesSQLiteOpenHelper;
    
    private static final String DATABASE_NAME = LeverageConstants.APP_DB_NAME;
    
    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE doc_info (DOC_ID varchar(10) not null, MOBILE_NO varchar(0) not null);";
   

     public SQLiteDatabase getDb() 
     {
        return database;
     }


  	String className = BookDocAppmntSQLiteOpenHelper.class.getName();
	String methodName = null;
	

	public BookDocAppmntSQLiteOpenHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
		
     	this.methodName="Constructor";
     	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
   		
    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
	}
	

    
	/*******************************************************************************
	 ** Function Name   :	onCreate
	 ** Created By      :	Anshuman Singh
	 ** Description		:	This function is called when SQLiteOpenHelper 
	 **						object is created.
	 ** Creation Date	:	21-04-2014
	 ** Arguments		:	SQLiteDatabase
	 ** Return Type     :	void
	 *******************************************************************************/
	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		this.methodName="onCreate";
    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));
    	
    	database.execSQL(CREATE_DOCTOR_TABLE);
		Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
		
	}

	/*******************************************************************************
	 ** Function Name   :	onUpgrade
	 ** Created By      :	Anshuman Singh
	 ** Description		:	This function is called when the database version
	 **						is updated.
	 ** Creation Date	:	21-04-2014
	 ** Arguments		:	SQLiteDatabase, Old Version of DB, New Version of DB
	 ** Return Type     :	void
	 *******************************************************************************/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		this.methodName="onUpgrade";
    	Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.ENTERING, this.className, this.methodName));	
	    Log.w(BookDocAppmntSQLiteOpenHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS doc_info;");
	    onCreate(db);    
	    Log.d(LeverageConstants.APP_NAME,LeverageUtilities.fromatLogMsg(LeverageConstants.EXITING, this.className, this.methodName));
	}
	

}
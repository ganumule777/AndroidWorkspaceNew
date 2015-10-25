/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Anshuman Singh
** Created on :     	29-04-2014
** Dept:            	Android Based Mobile App Development
** Class:          		LeverageUtilities
** Description:     	This is the class having all application level utilities
**
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime.util;

public class LeverageUtilities 
{
	/*******************************************************************************
	 ** Function Name   :	fromatLogMsg
	 ** Created By      :	Anshuman Singh
	 ** Description		:	This function is called to format the log message.
	 ** Creation Date	:	29-04-2014
	 ** Arguments		:	strMessage, strClassName, strMethodName
	 ** Return Type     :	String (Formatted message)
	 *******************************************************************************/
	public static String fromatLogMsg(String strMessage, String strClassName,  String strMethodName)
	{
		String strLogMessage="Class: "+strClassName+"||Method: "+strMethodName+"||Message: "+strMessage;
		return strLogMessage;
	}

}
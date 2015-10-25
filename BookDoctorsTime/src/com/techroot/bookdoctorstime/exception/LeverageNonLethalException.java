/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Anshuman Singh
** Created on :     	29-04-2014
** Dept:            	Android Based Mobile App Development
** Class:          		LeverageNonLethalException
** Description:     	This is the class for LeverageNonLethalException. This
**						class will be used to carry the exception details.
**
***********************************************************************************
***********************************************************************************/

package com.techroot.bookdoctorstime.exception;

public class LeverageNonLethalException extends Exception
{
	int exceptionCode;
	String exceptionMsg;
	
	public LeverageNonLethalException(int expCode, String expMsg)
	{
		this.exceptionCode = expCode;
		this.exceptionMsg = expMsg;
	}

	public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
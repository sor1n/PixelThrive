package com.base.engine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time 
{
	private static final long SECOND = 1000000000L;
	
	public static double getTime()
	{
		return (double)System.nanoTime() / (double)SECOND;
	}
	
	public static String getDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("y-MMM-d-HH.mm.s.S");
		return String.valueOf(sdf.format(cal.getTime()));
	}
}

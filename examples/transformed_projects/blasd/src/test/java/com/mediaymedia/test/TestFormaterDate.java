package com.mediaymedia.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.annotations.Test;

public class TestFormaterDate {

	@Test
	public void prueba(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
		simpleDateFormat.applyLocalizedPattern("ddMMyyHHmmss");
		String format = simpleDateFormat.format(Calendar.getInstance().getTime());
		System.out.println(format);
	}
	
}

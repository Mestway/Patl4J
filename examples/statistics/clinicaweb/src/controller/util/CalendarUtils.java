package controller.util;

import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtils {

	
	public static Date data(int dia, int mes, int ano){
		GregorianCalendar cal = new GregorianCalendar(ano,mes,dia);
		Date data = cal.getTime();
		return data;
	}
	
	public static int getAno(Date data){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(data);
		return cal.get(GregorianCalendar.YEAR);
	}
	
	public static int getMes(Date data){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(data);
		return cal.get(GregorianCalendar.MONTH);
	}
	
	public static int getDia(Date data){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(data);
		return cal.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public static Date data(Date data, int hours, int minutes){
		int ano = CalendarUtils.getAno(data);
		int mes = CalendarUtils.getMes(data);
		int dia = CalendarUtils.getDia(data);
		
		GregorianCalendar cal = new GregorianCalendar(ano,mes,dia,hours,minutes);
		return cal.getTime();
		
	}
}

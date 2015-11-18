package goofs.calendar;

import goofs.GoofsService;

import java.util.Date;
import java.util.List;

import com.google.gdata.data.ExtensionProfile;
import com.google.api.services.calendar.model;

public interface ICalendar extends GoofsService {

	public abstract List<CalendarListEntry> getCalendars() throws Exception; 

	public abstract CalendarListEntry createCalendar(String title) throws Exception; 

	public abstract CalendarListEntry getCalendarById(String id) throws Exception; 

	public abstract Event getCalendarEventById(String id) 
			throws Exception;

	public abstract CalendarListEntry updateCalendar(String id, CalendarListEntry in) 
			throws Exception;

	public abstract Event updateCalendarEvent(String id, 
			Event in) throws Exception;

	public abstract void deleteCalendar(String id) throws Exception;

	public abstract void deleteCalendarEvent(String id) throws Exception;

	public abstract CalendarListEntry subscribeToCalendar(String id) 
			throws Exception;

	public abstract List<Event> getEvents(CalendarListEntry cal) 
			throws Exception;

	public abstract List<Event> getEvents(CalendarListEntry cal, 
			Date start, Date end) throws Exception;

	public abstract List<Event> getEvents(CalendarListEntry cal, 
			String query) throws Exception;

	public abstract Event createQuickEvent(CalendarListEntry cal, 
			String event) throws Exception;

	public abstract ExtensionProfile getExtensionProfile();

}

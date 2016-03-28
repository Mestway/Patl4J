package goofs.fs.calendar;

import fuse.Errno;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.calendar.ICalendar;
import goofs.fs.Dir;
import goofs.fs.Node;

import com.google.api.services.calendar.model;

public class CalendarEventDir extends Dir implements Identifiable, Fetchable {

	protected String calendarEventId;

	public CalendarEventDir(Dir parent, Event event) 
			throws Exception {

		super(parent, event.getSummary(), 0777); 

		////setCalendarEventId(event.getSelfLink().getHref()); 

		////if (event.getTimes() != null && !event.getTimes().isEmpty()) { 
			add(new CalendarEventWhenFile(this, event));
		////}

		////if (event.getRecurrence() != null) { 
			add(new CalendarEventRecurrenceFile(this, event));
		////}

		////if (event.getLocations() != null) { 
			add(new CalendarEventWhereFile(this, event));
		////}

		////if (event.getSummary() != null) { 
			add(new CalendarEventSummaryFile(this, event));
		////}
	}

	public String getId() {
		return getCalendarEventId();
	}

	public Object fetch() throws Exception {

		return getCalendarEvent();

	}

	protected Event getCalendarEvent() throws Exception { 

		return getCalendarService().getCalendarEventById(getCalendarEventId());

	}

	protected ICalendar getCalendarService() {

		return ((CalendarsDir) getParent().getParent().getParent())
				.getCalendarService();
	}

	protected String getCalendarEventId() {
		return calendarEventId;
	}

	protected void setCalendarEventId(String calendarEventId) {
		this.calendarEventId = calendarEventId;
	}

	@Override
	public int createChild(String name, boolean isDir) {
		return Errno.EROFS;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

	@Override
	public int createTempChild(String name) {
		return Errno.EROFS;
	}

	@Override
	public int delete() {
		try {
			getCalendarService().deleteCalendarEvent(getCalendarEventId());

			remove();

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {
		if (getParent() == newParent) {
			try {
				Event e = new Event(); 
                e.setSummary(name);
				getCalendarService().updateCalendarEvent(getCalendarEventId(),
						e);
				setName(name);
				return 0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Errno.EROFS;
	}

}

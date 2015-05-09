package goofs.fs.calendar;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.calendar.ICalendar;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.calendar.model;

public class CalendarsDir extends Dir implements EntryContainer {

	private ICalendar calendarService;

	protected Set<String> entryIds = new HashSet<String>();

	public CalendarsDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.calendar.calendars"), 0777);

		calendarService = (ICalendar) ServiceFactory
				.getService(ICalendar.class);

		List<CalendarListEntry> cals = calendarService.getCalendars(); 

		for (CalendarListEntry e : cals) { 
			CalendarDir dir = new CalendarDir(this, e);
			add(dir);
			////getEntryIds().add(e.getSelfLink().getHref()); 
		}

	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	public void addNewEntryById(String entryId) throws Exception {

		CalendarListEntry cal = getCalendarService().getCalendarById(entryId); 

		CalendarDir calDir = new CalendarDir(this, cal);

		add(calDir);

		////getEntryIds().add(cal.getSelfLink().getHref()); 

	}

	public Set<String> getCurrentEntryIds() throws Exception {
		Set<String> ids = new HashSet<String>();

		List<CalendarListEntry> cals = getCalendarService().getCalendars(); 

		for (CalendarListEntry e : cals) { 
			////ids.add(e.getSelfLink().getHref()); 
		}

		return ids;
	}

	protected ICalendar getCalendarService() {
		return calendarService;
	}

	protected void setCalendarService(ICalendar calendarService) {
		this.calendarService = calendarService;
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
		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {
		return Errno.EROFS;
	}

}

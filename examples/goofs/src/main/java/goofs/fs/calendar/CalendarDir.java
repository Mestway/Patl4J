package goofs.fs.calendar;

import fuse.Errno;
import goofs.Fetchable;
import goofs.GoofsProperties;
import goofs.Identifiable;
import goofs.calendar.ICalendar;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.api.services.calendar.model;

public class CalendarDir extends Dir implements Identifiable, Fetchable {

	protected String calendarId;

	protected static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

	public CalendarDir(Dir parent, CalendarListEntry cal) throws Exception { 

		super(parent, cal.getSummary(), 0777); 

		////setCalendarId(cal.getSelfLink().getHref()); 

		java.util.Calendar start = java.util.Calendar.getInstance();
		java.util.Calendar end = java.util.Calendar.getInstance();

		end.set(java.util.Calendar.HOUR_OF_DAY, 23);
		end.set(java.util.Calendar.MINUTE, 59);
		end.set(java.util.Calendar.SECOND, 59);
		end.set(java.util.Calendar.MILLISECOND, 999);

		CalendarEventDurationDir next = new CalendarEventDurationDir(this,
				GoofsProperties.INSTANCE.getProperty("goofs.calendar.today"),
				cal, start.getTime(), end.getTime());

		add(next);

		start.setTime(end.getTime());
		end.add(java.util.Calendar.DATE, 6);

		next = new CalendarEventDurationDir(this, GoofsProperties.INSTANCE
				.getProperty("goofs.calendar.next7"), cal, start.getTime(), end
				.getTime());

		add(next);

		start.setTime(end.getTime());
		end.add(java.util.Calendar.DATE, 23);

		next = new CalendarEventDurationDir(this, GoofsProperties.INSTANCE
				.getProperty("goofs.calendar.next30"), cal, start.getTime(),
				end.getTime());

		add(next);

	}

	public Object fetch() throws Exception {
		return getCalendar();
	}

	public String getId() {
		return getCalendarId();
	}

	protected ICalendar getCalendarService() {

		return ((CalendarsDir) getParent()).getCalendarService();
	}

	protected CalendarListEntry getCalendar() throws Exception { 

		return getCalendarService().getCalendarById(getCalendarId());

	}

	protected String getCalendarId() {
		return calendarId;
	}

	protected void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (!isDir && "quick".equals(name)) {

			try {
				add(new QuickEventFile(this, name));

				return 0;

			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (isDir) {

			// check for a date range
			// the format is yyyyMMdd-yyyyMMdd
			if (name.length() == 17 && name.indexOf('-') == 8) {

				try {
					String s1 = name.substring(0, 8);
					String s2 = name.substring(9, 17);
					java.util.Calendar start = java.util.Calendar.getInstance();
					java.util.Calendar end = java.util.Calendar.getInstance();
					start.setTime(DF.parse(s1));
					end.setTime(DF.parse(s2));
					start.set(java.util.Calendar.HOUR_OF_DAY, 0);
					start.set(java.util.Calendar.MINUTE, 0);
					start.set(java.util.Calendar.SECOND, 0);
					start.set(java.util.Calendar.MILLISECOND, 0);
					end.set(java.util.Calendar.HOUR_OF_DAY, 23);
					end.set(java.util.Calendar.MINUTE, 59);
					end.set(java.util.Calendar.SECOND, 59);
					end.set(java.util.Calendar.MILLISECOND, 999);

					add(new CalendarEventDurationDir(this, name, getCalendar(),
							start.getTime(), end.getTime()));

					return 0;

				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// otherwise do a text search
			try {

				add(new CalendarEventByTextDir(this, name, getCalendar()));

				return 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return Errno.EROFS;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

	@Override
	public int createTempChild(String name) {

		try {
			add(new QuickEventTempFile(this, name));

			return 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return Errno.EROFS;

	}

	@Override
	public int delete() {

		try {
			getCalendarService().deleteCalendar(getCalendarId());

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
				CalendarListEntry c = new CalendarListEntry(); 
                c.setSummary(name);
				getCalendarService().updateCalendar(getCalendarId(), c);
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

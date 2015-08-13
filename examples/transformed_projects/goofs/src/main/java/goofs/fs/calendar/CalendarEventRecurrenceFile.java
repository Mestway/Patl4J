package goofs.fs.calendar;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.calendar.ICalendar;
import goofs.fs.Dir;
import goofs.fs.File;

import java.io.StringWriter;

import com.google.api.services.calendar.model;
import com.google.gdata.util.common.xml.XmlWriter;

public class CalendarEventRecurrenceFile extends File {

	public CalendarEventRecurrenceFile(Dir parent, Event event) 
			throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.calendar.recurrence"), 0777, "");

		setContent(getRecurrence(event).getBytes());

	}

	protected ICalendar getCalendarService() {

		return ((CalendarEventDir) getParent()).getCalendarService();
	}

	protected String getRecurrence(Event event) throws Exception { 

		StringWriter sw = new StringWriter();
		XmlWriter writer = new XmlWriter(sw);

		////if (event.getRecurrence() != null) {

			////event.getRecurrence().generate(writer, 
					////getCalendarService().getExtensionProfile());

		////}
		String when = sw.getBuffer().toString();

		writer.close();

		return when;

	}

	@Override
	public int save() {
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

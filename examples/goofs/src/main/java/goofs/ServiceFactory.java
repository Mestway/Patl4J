package goofs;

import goofs.blogger.Blogger;
import goofs.blogger.IBlogger;
import goofs.calendar.Calendar;
import goofs.calendar.ICalendar;
import goofs.contacts.Contacts;
import goofs.contacts.IContacts;
import goofs.docs.Documents;
import goofs.docs.IDocuments;
import goofs.photos.IPicasa;
import goofs.photos.Picasa;

import java.lang.reflect.Proxy;

public class ServiceFactory {

	public static <T extends GoofsService> Object getService(Class<T> clazz)
			throws Exception {

		GoofsService gs = null;

		String u = GoofsProperties.INSTANCE.getProperty("username");
		String p = GoofsProperties.INSTANCE.getProperty("password");

		if (clazz == IBlogger.class) {
			gs = new Blogger(u, p);
		} else if (clazz == ICalendar.class) {
			gs = new Calendar(u, p);
		} else if (clazz == IContacts.class) {
			gs = new Contacts(u, p);
		} else if (clazz == IPicasa.class) {
			gs = new Picasa(u, p);
		} else if (clazz == IDocuments.class) {
			gs = new Documents(u, p);
		} else {
			throw new IllegalArgumentException(clazz.getName());
		}

		return Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[] { clazz }, new ServiceInvocationHandler(gs));

	}
}

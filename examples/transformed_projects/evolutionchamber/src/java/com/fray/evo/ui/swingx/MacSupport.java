package com.fray.evo.ui.swingx;

import java.awt.Image;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessControlException;

import javax.swing.ImageIcon;

public class MacSupport {

	public static void init(String title, boolean enablePreferences, String dockImage, final MacHandler handler) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");

			System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);

			Class<?> applicationListenerInterface = Class.forName("com.apple.eawt.ApplicationListener");
			Object applicationListenerInstance = Proxy.newProxyInstance(MacSupport.class.getClassLoader(), new Class<?>[] { applicationListenerInterface }, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
					String methodName = method.getName();

					Object applicationEvent = arguments[0];

					if (methodName.equals("handleQuit")) {
						handler.handleQuit(applicationEvent);
					} else if (methodName.equals("handleAbout")) {
						handler.internalHandleAbout(applicationEvent);
					} else if (methodName.equals("handlePreferences")) {
						handler.handlePreferences(applicationEvent);
					}
					return null;
				}
			});

			Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
			Method getApplicationMethod = applicationClass.getMethod("getApplication");
			Object applicationInstance = getApplicationMethod.invoke(null);

			if (enablePreferences){
				Method setEnabledPreferencesMenuMethod = applicationClass.getMethod("setEnabledPreferencesMenu", boolean.class);
				setEnabledPreferencesMenuMethod.invoke(applicationInstance, enablePreferences);
			}

			Method addApplicationListenerMethod = applicationClass.getMethod("addApplicationListener", applicationListenerInterface);
			addApplicationListenerMethod.invoke(applicationInstance, applicationListenerInstance);

			if (dockImage != null) {
				Method setDockIconImageMethod = applicationClass.getMethod("setDockIconImage", Image.class);
				ImageIcon icon = new ImageIcon(MacSupport.class.getResource(dockImage));
				setDockIconImageMethod.invoke(applicationInstance, icon.getImage());
			}
		} catch (AccessControlException e) {
		} catch (Throwable e) {
		}
	}

	public static boolean isMac() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.startsWith("mac os x");
	}

	public static void initIfMac(String title, boolean enablePreferences, String dockImage, MacHandler handler) {
		if (isMac()) {
			init(title, enablePreferences, dockImage, handler);
		}
	}
}

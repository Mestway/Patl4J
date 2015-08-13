package goofs;

import goofs.fs.GoofsFS;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.gdata.client.GoogleService;

public class ServiceInvocationHandler implements InvocationHandler {

	public GoofsService target;

	public ServiceInvocationHandler(GoofsService target) {

		setTarget(target);

	}

	protected GoofsService getTarget() {
		return target;
	}

	protected void setTarget(GoofsService target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// make sure that activation.jar included in the boot classpath in java
		// 6 is able to load the google data handlers

		Thread.currentThread().setContextClassLoader(
				GoofsFS.class.getClassLoader());

		try {

			return method.invoke(getTarget(), args);

		} catch (InvocationTargetException e) {

			if (e.getCause() instanceof GoogleService.SessionExpiredException) {

				// let's try to re-establish a connection

				getTarget().acquireSessionTokens(
						GoofsProperties.INSTANCE.getProperty("username"),
						GoofsProperties.INSTANCE.getProperty("password"));

				// try one more time

				return method.invoke(getTarget(), args);
			}

			throw e;

		}

	}
}

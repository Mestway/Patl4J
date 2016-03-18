package com.fray.evo.ui.swingx;

public abstract class MacHandler {
	private boolean aboutOverriden = true;

	public void handleQuit(Object applicationEvent) {
	}

	public final void internalHandleAbout(Object applicationEvent) {
		handleAbout(applicationEvent);
		if (aboutOverriden) {
			try {
				applicationEvent.getClass().getMethod("setHandled", boolean.class).invoke(applicationEvent, true);
			} catch (Exception e) {
			}
		}
	}

	public void handleAbout(Object applicationEvent) {
		aboutOverriden = false;
	}

	public void handlePreferences(Object applicationEvent) {
	}
}

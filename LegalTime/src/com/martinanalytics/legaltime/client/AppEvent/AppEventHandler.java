package com.martinanalytics.legaltime.client.AppEvent;

import com.google.gwt.event.shared.EventHandler;

public interface AppEventHandler extends EventHandler {
	public void onAction(AppEvent event);
}

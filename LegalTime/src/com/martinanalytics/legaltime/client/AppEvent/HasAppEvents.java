package com.martinanalytics.legaltime.client.AppEvent;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasAppEvents {
	public HandlerRegistration addAppEventHandler(AppEventHandler handler);
}

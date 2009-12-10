package com.martinanalytics.legaltime.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.model.bean.SecurityUserBean;

public interface ApplicationSecurityServiceAsync {
	void authenticateUser(String userId_, String password_, AsyncCallback<SecurityUserBean> callback);

}



package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import java.util.Date;
//private final LaborRegisterServiceAsync laborRegisterService = 
//	GWT.create(LaborRegisterService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LaborRegisterServiceAsync{
	void  insertLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_, AsyncCallback<LaborRegisterBean> callback);
	void  saveLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_, AsyncCallback<LaborRegisterBean> callback);
	void  updateLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_, AsyncCallback<LaborRegisterBean> callback);
	void  deleteLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_, AsyncCallback<Boolean> result);
	void  selectLaborRegister(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<LaborRegisterBean>> callback);
	void  saveLaborRegisterBeanBatch(UserProfile userProfile_, ArrayList<LaborRegisterBean> laborRegisterBeanList_, AsyncCallback<ArrayList<LaborRegisterBean>> callback);

	void  getLaborRegisterByPrKey(UserProfile userProfile_ , Integer laborRegisterId_ , Integer customerId_ , AsyncCallback<LaborRegisterBean> callback);
	void AssessMonthlyCharges(UserProfile userProfile_, Date assessDt_,
			AsyncCallback<Integer> callback);
	void RetrieveLastMonthlycharge(UserProfile userProfile_,
			AsyncCallback<Date> callback);


}

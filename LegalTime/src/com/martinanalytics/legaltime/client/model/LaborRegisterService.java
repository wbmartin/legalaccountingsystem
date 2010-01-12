

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("laborRegister")
public interface LaborRegisterService extends RemoteService{
	 LaborRegisterBean insertLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_);
	 LaborRegisterBean updateLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_);
	 LaborRegisterBean saveLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_);
	 Boolean deleteLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_);
	 ArrayList<LaborRegisterBean> selectLaborRegister(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<LaborRegisterBean> saveLaborRegisterBeanBatch(UserProfile userProfile_, ArrayList<LaborRegisterBean> laborRegisterBeanList_);
	 LaborRegisterBean getLaborRegisterByPrKey(UserProfile userProfile_ , Integer laborRegisterId_ , Integer customerId_ );
	 Integer AssessMonthlyCharges(UserProfile userProfile_, java.util.Date assessDt_) throws GWTCustomException;
	 java.util.Date RetrieveLastMonthlycharge(UserProfile userProfile_) throws GWTCustomException;
}

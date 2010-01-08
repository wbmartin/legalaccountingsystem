package com.martinanalytics.legaltime.server.rpt.addresslabels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.server.model.CustomerServiceImpl;
import com.martinanalytics.legaltime.server.model.VwCustomerFollowupServiceImpl;

public class CustomerAddressLabelServlet extends HttpServlet {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

	public void doPost(HttpServletRequest request,
              HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter out = response.getWriter();
		ArrayList<CustomerBean> beans = new  ArrayList<CustomerBean>();
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId(request.getParameter("userId"));
		userProfile.setSessionId(request.getParameter("sessionId"));
		userProfile.setClientId(Integer.parseInt(request.getParameter("clientId")));
		String whereClause ="";
		String orderByClause ="order by last_name desc";
	    
		beans = customerServiceImpl.selectCustomer(userProfile, whereClause, orderByClause);
        boolean success = false;
        JasperPrint jasperPrint;	
        java.util.HashMap params = new java.util.HashMap();
        InputStream jasperFile = this.getServletContext().getResourceAsStream(
        		"/WEB-INF/classes/com/martinanalytics/legaltime/server/rpt/addresslabels/ClientAddressLabels.jasper");
        try {
			jasperPrint = JasperFillManager.fillReport(
			    jasperFile, params,new JRBeanCollectionDataSource(beans,false));
			
			ServletOutputStream outputStream = response.getOutputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        JRPdfExporter exporter = new JRPdfExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
	        exporter.exportReport();
			byte[] byteStream = baos.toByteArray(); 
	        outputStream.write(byteStream);
	        outputStream.flush();
	        outputStream.close();
	        success = true;
        } catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.err.println("Followup Report Created Successfully");
		
	}
	
	
}

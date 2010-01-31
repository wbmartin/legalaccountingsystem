package com.martinanalytics.legaltime.server.rpt.customerhistory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.google.gwt.dev.js.rhino.Context;
import com.martinanalytics.legaltime.client.model.ExpenseInvoiceItemService;
import com.martinanalytics.legaltime.client.model.LaborInvoiceItemService;
import com.martinanalytics.legaltime.client.model.VwInvoiceDisplayService;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.CustomerServiceImpl;
import com.martinanalytics.legaltime.server.model.ExpenseInvoiceItemServiceImpl;
import com.martinanalytics.legaltime.server.model.ExpenseRegisterServiceImpl;
import com.martinanalytics.legaltime.server.model.InvoiceServiceImpl;
import com.martinanalytics.legaltime.server.model.LaborInvoiceItemServiceImpl;
import com.martinanalytics.legaltime.server.model.LaborRegisterServiceImpl;
import com.martinanalytics.legaltime.server.model.PaymentLogServiceImpl;
import com.martinanalytics.legaltime.server.model.VwInvoiceDisplayServiceImpl;
import com.martinanalytics.legaltime.server.model.VwLaborInvoiceItemDisplayServiceImpl;

public class CustomerHistoryReportServlet extends HttpServlet{
	// static LaborRegisterServiceImpl laborRegisterService = new LaborRegisterServiceImpl();
	static VwLaborInvoiceItemDisplayServiceImpl laborInvoiceItemService = new VwLaborInvoiceItemDisplayServiceImpl();
	static ExpenseInvoiceItemServiceImpl expenseInvoiceItemService = new ExpenseInvoiceItemServiceImpl();
	static PaymentLogServiceImpl paymentLogService = new PaymentLogServiceImpl();
	 //static ExpenseRegisterServiceImpl expenseRegisterService = new ExpenseRegisterServiceImpl();
	// static InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
	// static CustomerServiceImpl customerService = new CustomerServiceImpl();
	 
	 static CustomerServiceImpl customerService = new CustomerServiceImpl();
	private static Double expenseTotal;
	private static double laborTotal;
	private static Double paymentTotal;
	 static final String REPORT_PATH ="/WEB-INF/classes/com/martinanalytics/legaltime/server/rpt/";
	public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		UserProfile userProfile = new UserProfile();
		//test
		userProfile.setUserId(request.getParameter("userId"));
		userProfile.setSessionId(request.getParameter("sessionId"));
		userProfile.setClientId(Integer.parseInt(request.getParameter("clientId")));
        boolean success = false;
        ArrayList<CustomerBean> beans = new ArrayList<CustomerBean>();
		try{
		  Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		  beans.add(customerService.selectCustomer(userProfile, "where customer_id= " +customerId, "").get(0));
		}catch(Exception e){
			try {
				beans = customerService.selectCustomer(userProfile, "where customer_id in (" +request.getParameter("invoiceIdList") +")", "");
			} catch (GWTCustomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

        
        
        ArrayList<JasperPrint> jasperPrintList = buildCustomerHistory(userProfile, beans);
        try {
        	
			ServletOutputStream outputStream = response.getOutputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        JRPdfExporter exporter = new JRPdfExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
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
        System.err.println("Invoices Report Created Successfully");
		
        
        
	}
	
	private ArrayList<JasperPrint> buildCustomerHistory(UserProfile userProfile_, ArrayList<CustomerBean> beans){
		 
		InputStream jasperFile ;//= this.getServletContext().getResourceAsStream(REPORT_PATH + "invoice/BogerInvoice.jasper");
		//InputStream jasperFile = this.getServletContext().getResourceAsStream(REPORT_PATH + "invoice/BogerInvoice_ExpenseSubReport.jasper");
		ArrayList<CustomerBean> single = new ArrayList<CustomerBean>();
		ArrayList<JasperPrint> result = new ArrayList<JasperPrint>();

		
		for(CustomerBean bean :beans){
			single.clear();
			single.add(bean);
			
			//System.err.println("Invoice ID: " +bean.getInvoiceId());
			try {
				
					
					jasperFile = this.getServletContext().getResourceAsStream(REPORT_PATH + "customerhistory/BogerCustomerHistory.jasper");
					result.add(JasperFillManager.fillReport(
						    jasperFile, getParams(userProfile_, bean),new JRBeanCollectionDataSource(single,false)));
				
			} catch (JRException e) {
				
				e.printStackTrace();
			}
			
		}
		return result;
			
        
	}
	
	
	
	 public static JRDataSource  getExpenseBeans(UserProfile userProfile_, int customerId_) throws GWTCustomException{
		   ArrayList<ExpenseInvoiceItemBean> expenseLines = expenseInvoiceItemService.selectExpenseInvoiceItem(userProfile_, "where customer_id = " + customerId_, "order by expense_dt");
		   expenseTotal = 0D;
		   for(int ndx =0; ndx< expenseLines.size();ndx++){
			   expenseTotal += expenseLines.get(ndx).getAmount();
		   }
	       return new JRBeanCollectionDataSource(expenseLines);

	 }
	 public static JRDataSource  getLaborBeans(UserProfile userProfile_, int customerId_) throws GWTCustomException{
	       ArrayList<VwLaborInvoiceItemDisplayBean> laborLines = laborInvoiceItemService.selectVwLaborInvoiceItemDisplay(userProfile_, "where customer_id = " + customerId_, "order by activity_dt");
	       laborTotal = 0D;
	       for(int ndx =0; ndx< laborLines.size();ndx++){
			   laborTotal += laborLines.get(ndx).getHoursBilled()* laborLines.get(ndx).getBillRate();
		   }
	       return new JRBeanCollectionDataSource(laborLines);

	 }
	 
	 public static JRDataSource  getPaymentBeans(UserProfile userProfile_, int customerId_)throws GWTCustomException{
	       ArrayList<PaymentLogBean> paymentLines = paymentLogService.selectPaymentLog(userProfile_, "where customer_id = " + customerId_, "order by effective_dt");
	       paymentTotal = 0D;
	       for(int ndx =0; ndx< paymentLines.size();ndx++){
			   paymentTotal += paymentLines.get(ndx).getAmount();
		   }
	       return new JRBeanCollectionDataSource(paymentLines);

	 }
	 
	 public java.util.HashMap getParams(UserProfile userProfile_, CustomerBean bean_){
	       java.util.HashMap params = new java.util.HashMap();
	       
	       try{
	    	   
		    	   String reportsDirPath = this.getServletContext().getRealPath(REPORT_PATH+"customerhistory/");
		    	   
		    	   params.put("SUBREPORT_DIR",reportsDirPath);  
		    	   params.put("LaborRecords", getLaborBeans(userProfile_, bean_.getCustomerId()));
		    	   params.put("Expenses", getExpenseBeans(userProfile_, bean_.getCustomerId()));
		    	   params.put("Payments",getPaymentBeans(userProfile_, bean_.getCustomerId()));
		    	   params.put("CurrentBalance", paymentTotal + laborTotal + expenseTotal);
		    	   
	    	   
	       }catch(Exception e){
	           Log.debug( "Error General Exception getParams"+ e.getMessage());
	       }
	   
	        return params;
	   }

}

package com.martinanalytics.legaltime.server.rpt.invoice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;
import com.martinanalytics.legaltime.server.model.CustomerServiceImpl;
import com.martinanalytics.legaltime.server.model.ExpenseInvoiceItemServiceImpl;
import com.martinanalytics.legaltime.server.model.ExpenseRegisterServiceImpl;
import com.martinanalytics.legaltime.server.model.InvoiceServiceImpl;
import com.martinanalytics.legaltime.server.model.LaborInvoiceItemServiceImpl;
import com.martinanalytics.legaltime.server.model.LaborRegisterServiceImpl;
import com.martinanalytics.legaltime.server.model.VwInvoiceDisplayServiceImpl;
import com.martinanalytics.legaltime.server.model.VwLaborInvoiceItemDisplayServiceImpl;

public class InvoiceReportServlet extends HttpServlet{
	// static LaborRegisterServiceImpl laborRegisterService = new LaborRegisterServiceImpl();
	static VwLaborInvoiceItemDisplayServiceImpl laborInvoiceItemService = new VwLaborInvoiceItemDisplayServiceImpl();
	static ExpenseInvoiceItemServiceImpl expenseInvoiceItemService = new ExpenseInvoiceItemServiceImpl();
	 static ExpenseRegisterServiceImpl expenseRegisterService = new ExpenseRegisterServiceImpl();
	// static InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
	// static CustomerServiceImpl customerService = new CustomerServiceImpl();
	 
	 static VwInvoiceDisplayServiceImpl vwInvoiceDisplayService = new VwInvoiceDisplayServiceImpl();
	 static final String REPORT_PATH ="/WEB-INF/classes/com/martinanalytics/legaltime/server/rpt/";
	public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		UserProfile userProfile = new UserProfile();
		//test
		userProfile.setUserId(request.getParameter("userId"));
		userProfile.setSessionId(request.getParameter("sessionId"));
		userProfile.setClientId(Integer.parseInt(request.getParameter("clientId")));
		Integer invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
        boolean success = false;
        ArrayList<VwInvoiceDisplayBean> beans = new ArrayList<VwInvoiceDisplayBean>();
        beans.add(vwInvoiceDisplayService.selectVwInvoiceDisplay(userProfile, "where invoice_id= " +invoiceId, "").get(0));
        
        ArrayList<JasperPrint> jasperPrintList = buildInvoices(userProfile, beans);
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
        System.err.println("Followup Report Created Successfully");
		
        
        
	}
	
	private ArrayList<JasperPrint> buildInvoices(UserProfile userProfile_, ArrayList<VwInvoiceDisplayBean> beans){
		 
		InputStream jasperFile = this.getServletContext().getResourceAsStream(REPORT_PATH + "invoice/BogerInvoice.jasper");
		//InputStream jasperFile = this.getServletContext().getResourceAsStream(REPORT_PATH + "invoice/BogerInvoice_ExpenseSubReport.jasper");
		ArrayList<VwInvoiceDisplayBean> single = new ArrayList<VwInvoiceDisplayBean>();
		ArrayList<JasperPrint> result = new ArrayList<JasperPrint>();

		
		for(VwInvoiceDisplayBean bean :beans){
			single.clear();
			single.add(bean);
			try {
				result.add(JasperFillManager.fillReport(
					    jasperFile, getParams(userProfile_, bean.getInvoiceId()),new JRBeanCollectionDataSource(single,false)));
//				result.add(JasperFillManager.fillReport(
//					    jasperFile, null,getExpenseBeans(userProfile_, bean.getInvoiceId())));
			} catch (JRException e) {
				
				e.printStackTrace();
			}
			
		}
		return result;
			
        
	}
	
	
	
	 public static JRDataSource  getExpenseBeans(UserProfile userProfile_, int invoiceId_){
		   ArrayList<ExpenseInvoiceItemBean> expenseLines = expenseInvoiceItemService.selectExpenseInvoiceItem(userProfile_, "where invoice_id = " + invoiceId_, "order by expense_dt");
	       return new JRBeanCollectionDataSource(expenseLines);

	 }
	 public static JRDataSource  getLaborBeans(UserProfile userProfile_, int invoiceId_){
	       ArrayList<VwLaborInvoiceItemDisplayBean> laborLines = laborInvoiceItemService.selectVwLaborInvoiceItemDisplay(userProfile_, "where invoice_id = " + invoiceId_, "order by activity_dt");
	       
	       return new JRBeanCollectionDataSource(laborLines);

	 }
	 
	 public static JRDataSource  getPaymentBeans(UserProfile userProfile_, int invoiceId_){
	      // ArrayList<PaymentBean> laborLines = laborRegisterService.selectLaborRegister(userProfile_, "where invoice_id = " + invoiceId_, "order by activity_dt");
	       return new JRBeanCollectionDataSource(null);

	 }
	 
	 public java.util.HashMap getParams(UserProfile userProfile_, int invoiceId_){
	       java.util.HashMap params = new java.util.HashMap();
	       double pleaseRemit =0;
	       try{
	    	   String reportsDirPath = this.getServletContext().getRealPath(REPORT_PATH+"invoice/");
	    	   params.put("SUBREPORT_DIR", reportsDirPath );  
	    	   params.put("LaborRecords", getLaborBeans(userProfile_, invoiceId_));
	    	   params.put("Expenses", getExpenseBeans(userProfile_, invoiceId_));
	    	   params.put("Payments",getPaymentBeans(userProfile_, invoiceId_));
	    	   params.put("PreviousBalance",0D);
	       }catch(Exception e){
	           Log.debug( "Error General Exception getParams"+ e.getMessage());
	       }
	   
	        return params;
	   }

}

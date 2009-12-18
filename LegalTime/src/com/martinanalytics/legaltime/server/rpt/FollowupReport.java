///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.martinanalytics.legaltime.server.rpt;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//
//import com.martinanalytics.legaltime.client.AppPref;
//import com.martinanalytics.legaltime.client.model.bean.UserProfile;
//import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
//import com.martinanalytics.legaltime.client.widget.TextUtils;
//import com.martinanalytics.legaltime.server.model.VwCustomerFollowupServiceImpl;
//
//
///**
// *
// * @author bmartin
// */
//public class FollowupReport   {
////com.google.gwt.user.client.HTTPRequest.asyncPost()
//
//
//   private static AppPref appPrefs;
//   private String reportPath;
//   private VwCustomerFollowupServiceImpl vwCustomerFollowupServiceImpl;
//   
//   public FollowupReport(){
//	   vwCustomerFollowupServiceImpl= new VwCustomerFollowupServiceImpl();
//   }
//   public JRDataSource createDataSource_OpenFollowups(UserProfile userProfile_,String whereClause_,String  orderByClause_){
//       ArrayList<VwCustomerFollowupBean> beans = new  ArrayList<VwCustomerFollowupBean>();
//       beans = vwCustomerFollowupServiceImpl.selectVwCustomerFollowup(userProfile_, whereClause_, orderByClause_);
//       return new JRBeanCollectionDataSource(beans);
//   }
//   
////    public boolean makeFollowUpReport(UserProfile userProfile_,String whereClause_,String  orderByClause_){
////           java.util.Date now = new java.util.Date();
////           String outputPath = appPrefs.getValue(AppPrefs.REPORT_OUTPUT_PATH)
////                + File.separatorChar ;
////
////           String fileName = "FollowupReport_"
////                +Integer.toString(1900+now.getYear())
////                +TextUtils.frontZeroFill( Integer.toString(now.getMonth()+1),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getDate()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getHours()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getMinutes()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getSeconds()),2)
////                +".pdf";
////           JRDataSource clientDataSource = createDataSource_OpenFollowups(userProfile_, whereClause_, orderByClause_);
////           return makeReport( outputPath, fileName, clientDataSource);
////       }
//
//  
//   public  boolean makeReport( JRDataSource clientDataSource_){
//       boolean success = false;
//    JasperPrint jasperPrint;
//    java.util.Date now = new java.util.Date();
//    try{
//        File dir = new File(outputPath_);
//        success =dir.exists();
//        if(!success){
//            success =(dir).mkdir();
//        }
//        if(!success){
//            System.err.println( "Error Creating Report Directory " +
//                    "Not Present and Could Not Create"
//                    + "Client Address Report"+outputPath_ );
//            return false;
//        }
//
//
//        ClassLoader cl = ResourceAnchor.class.getClassLoader();
//        InputStream jasperFile = cl.getResourceAsStream("legaltime/reports/Followup.jasper");
//        reportPath =cl.getResource("legaltime/reports/").toString();
//        String temp = cl.getResource("legaltime/reports/").toString();
//        //easyLog.addEntry(9,"FilePath", temp,"");
//         
//         
//         
//        
//        jasperPrint = JasperFillManager.fillReport(
//          jasperFile, getParams(),clientDataSource_ );
//
////        JasperExportManager.exportReportToPdfFile(
////          jasperPrint, outputPath_+File.separatorChar + fileName_);
//        OutputStream outputStream = servletResponse.getOutputStream();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        JRPdfExporter exporter = new JRPdfExporter();
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//        exporter.exportReport();
//        ByteArray byteStream = baos.toByteArray(); 
//        outputStream.write(byteStream);
//        outputStream.flush();
//        outputStream.close();
//        success = true;
//        System.err.println("Followup Report Created Successfully");
//    }
//    catch (JRException e)    {
//    	System.err.println( "Error Building Report"+ e.getMessage());
//        success = false;
//      
//    }catch(Exception e){
//      System.err.println( "Error General Exception Building report" + e.getMessage());
//      success = false;
//    }
//
//    return success;
//  }
//
//  
//
//
//   public java.util.HashMap getParams(){
//       java.util.HashMap params = new java.util.HashMap();
//       try{
//        params.put("SUBREPORT_DIR",reportPath);
//        
//       }catch(Exception e){
//           easyLog.addEntry(EasyLog.INFO, "Error General Exception getParams"
//                    , "Client Address Label Report", e);
//       }
//        return params;
//   }
//
//
//
//
//   
//
//}

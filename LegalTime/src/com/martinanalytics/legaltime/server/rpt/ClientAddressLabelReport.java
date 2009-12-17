///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.martinanalytics.legaltime.server.rpt;
//
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//import java.io.InputStream;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//
///**
// *
// * @author bmartin
// */
//public class ClientAddressLabelReport   {
////   LaborInvoiceItemManager laborInvoiceItemManager;
// 
//  
//   static ClientCache clientCache;
//   
//   private static AppPrefs appPrefs;
//   private String reportPath;
//   private ClientManager clientManager;
//   public ClientAddressLabelReport(){
//        easyLog = EasyLog.getInstance();
//        clientCache = ClientCache.getInstance();
//        appPrefs = AppPrefs.getInstance();
//        clientManager = ClientManager.getInstance();
//   }
//   
//   public JRDataSource createDataSource_AllClients(){
//       //ArrayList<LaborInvoiceItemBean> clientsArrayList;
//        ClientBean[] beans = null;
//        try {
//            beans = ClientManager.getInstance().loadByWhere("where active_yn ='Y'");
//        } catch (DAOException ex) {
//            Logger.getLogger(ClientAddressLabelReport.class.getName()).log(Level.SEVERE, null, ex);
//            easyLog.addEntry(EasyLog.INFO, "DAO Exception getting Clients"
//                    , "Client Address Labels Report", ex);
//        }
//
//
//        clientsArrayList = new ArrayList(java.util.Arrays.asList(beans));
//        
//       return new JRBeanCollectionDataSource(clientsArrayList);
//   }
////   public JRDataSource createDataSource_InvoicedClients(java.util.Date invoiceDt_){
////       ArrayList<LaborInvoiceItemBean> clientsArrayList;
////        ClientBean[] beans = null;
////        try {
////            //beans = ClientManager.getInstance().loadByWhere("where active_yn ='Y'");
////            PreparedStatement ps = null;
////            ResultSet rs;
////            ps = Manager.getInstance().getConnection().prepareStatement(SQLGarage.getInvoicedClientsSQL(invoiceDt_));
////            beans = clientManager.loadByPreparedStatement(ps);
////        } catch (SQLException ex) {
////            Logger.getLogger(ClientAddressLabelReport.class.getName()).log(Level.SEVERE, null, ex);
////            easyLog.addEntry(EasyLog.INFO, "DAO Exception getting Clients"
////                    , "Client Address Labels Report", ex);
////        }
////
////
////        clientsArrayList = new ArrayList(java.util.Arrays.asList(beans));
////
////       return new JRBeanCollectionDataSource(clientsArrayList);
////   }
//    public boolean makeAllAddressLabelReport(){
//           java.util.Date now = new java.util.Date();
//           String outputPath = appPrefs.getValue(AppPrefs.REPORT_OUTPUT_PATH)
//                + File.separatorChar ;
//
//           String fileName = "ClientAddressLabels_"
//                +Integer.toString(1900+now.getYear())
//                +TextUtils.frontZeroFill( Integer.toString(now.getMonth()+1),2)
//                +TextUtils.frontZeroFill( Integer.toString(now.getDate()),2)
//                +TextUtils.frontZeroFill( Integer.toString(now.getHours()),2)
//                +TextUtils.frontZeroFill( Integer.toString(now.getMinutes()),2)
//                +TextUtils.frontZeroFill( Integer.toString(now.getSeconds()),2)
//                +".pdf";
//           JRDataSource clientDataSource = createDataSource_AllClients();
//           return makeReport( outputPath, fileName, clientDataSource);
//       }
//
////  public boolean makeInvoicedAddressLabelReport(java.util.Date invoiceDt_){
////           java.util.Date now = new java.util.Date();
////           String outputPath = appPrefs.getValue(AppPrefs.REPORT_OUTPUT_PATH)
////                + File.separatorChar ;
////
////           String fileName = "ClientAddressLabels_"
////                +Integer.toString(1900+now.getYear())
////                +TextUtils.frontZeroFill( Integer.toString(now.getMonth()+1),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getDate()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getHours()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getMinutes()),2)
////                +TextUtils.frontZeroFill( Integer.toString(now.getSeconds()),2)
////                +".pdf";
////           JRDataSource clientDataSource = createDataSource_InvoicedClients(invoiceDt_);
////           return makeReport( outputPath, fileName, clientDataSource);
////       }
//   public  boolean makeReport(String outputPath_, String fileName_, JRDataSource clientDataSource_){
//       boolean success = false;
//    JasperPrint jasperPrint;
//    java.util.Date now = new java.util.Date();
//    try
//    {
//        
//
//          File dir = new File(outputPath_);
//        success =dir.exists();
//        if(!success){
//            success =(dir).mkdir();
//        }
//        if(!success){
//            easyLog.addEntry(EasyLog.INFO, "Error Creating Report Directory " +
//                    "Not Present and Could Not Create"
//                    , "Client Address Report",outputPath_ );
//            return false;
//        }
//
//
//        ClassLoader cl = ResourceAnchor.class.getClassLoader();
//        InputStream jasperFile = cl.getResourceAsStream("legaltime/reports/ClientAddressLabels.jasper");
//        reportPath =cl.getResource("legaltime/reports/").toString();
//        String temp = cl.getResource("legaltime/reports/").toString();
//        easyLog.addEntry(9,"FilePath", temp,"");
//         
//         
//         
//        
//        jasperPrint = JasperFillManager.fillReport(
//          jasperFile, getParams(),clientDataSource_ );
//
//        JasperExportManager.exportReportToPdfFile(
//          jasperPrint, outputPath_+File.separatorChar + fileName_);
//        success = true;
//        easyLog.addEntry(EasyLog.INFO, "Client Address Labels Created Successfully"
//                    , "Client Address Label Report",outputPath_ );
//    }
//    catch (JRException e)    {
//        easyLog.addEntry(EasyLog.INFO, "Error Building Report", getClass().getName(), e);
//        success = false;
//      
//    }catch(Exception e){
//      easyLog.addEntry(EasyLog.INFO, "Error General Exception Building report", getClass().getName(), e);
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
////   public static ArrayList getAllClientBeans(){
////        ArrayList<LaborInvoiceItemBean> Clients;
////        ClientBean[] beans = null;
////        try {
////            beans = ClientManager.getInstance().loadByWhere("where active_yn ='Y'");
////        } catch (DAOException ex) {
////            Logger.getLogger(ClientAddressLabelReport.class.getName()).log(Level.SEVERE, null, ex);
////            easyLog.addEntry(EasyLog.INFO, "DAO Exception getting Clients"
////                    , "Client Address Labels Report", ex);
////        }
////
////
////        Clients = new ArrayList(java.util.Arrays.asList(beans));
////        return Clients;
////    }
//
//
//   
//
//}

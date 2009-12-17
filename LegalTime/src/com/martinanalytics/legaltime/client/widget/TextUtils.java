package com.martinanalytics.legaltime.client.widget;

public class TextUtils {

    public static String frontZeroFill(Object value_, int charCount_){
     
       StringBuffer value = new StringBuffer(value_.toString());
       for(int ndx =value.length();ndx<charCount_; ndx ++){
           value.insert(0, "0");
       }

       return value.toString();
   }

//    public static String getStackTrace(Throwable aThrowable) {
//       final Writer result = new StringWriter();
//       final PrintWriter printWriter = new PrintWriter(result);
//       aThrowable.printStackTrace(printWriter);
//       return result.toString();
//   }

    public static String prepareFileName(String fileName_){
        fileName_ = fileName_.replaceAll("\\\\","" );
        fileName_ = fileName_.replaceAll("<","" );
        fileName_ = fileName_.replaceAll(">","" );
        fileName_ = fileName_.replaceAll(":","" );
        fileName_ = fileName_.replaceAll("\"","" );
        fileName_ = fileName_.replaceAll("/","" );
        fileName_ = fileName_.replaceAll("|","" );

        return fileName_;
        
    }

}

package com.martinanalytics.legaltime.client.widget;

import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

/**
*
* @author bmartin
*/
public class SimpleDateFormat {
   String fmt;
   public SimpleDateFormat(String fmt_){
       fmt = fmt_;
  
   }
   public java.util.Date parse(String dateValue_){
       return DateTimeFormat.getFormat(fmt).parse(dateValue_);
   }
   public String format(Date dt_){
       return DateTimeFormat.getFormat(fmt).format(dt_);
   }

}

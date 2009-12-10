/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.martinanalytics.legaltime.client.widget.richtext;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;


public class PrettyRichText extends Composite {
	String widgetDescription="";
	String widgetName="";
	RichTextArea area;

  /**
   * Constructor.
   * 
   * @param constants the constants
   */
  public PrettyRichText() {
	  initWidget(createWidget());
  }
  public String getText(){
	  return area.getText();
  }
  public String getHTML(){
	  return area.getHTML();
  }


  public String getDescription() {
    return widgetDescription;
  }


  public String getName() {
    return widgetName;
  }

  /**
   * Initialize this example.
   */

  



protected Widget createWidget() {
	  // Create the text area and toolbar
    area = new RichTextArea();
    area.ensureDebugId("cwRichText-area");
    area.setSize("500px", "14em");
    RichTextToolbar toolbar = new RichTextToolbar(area);
    toolbar.ensureDebugId("cwRichText-toolbar");
    toolbar.setWidth("100%");

    // Add the components to a panel
    Grid grid = new Grid(2, 1);
    
    grid.setStyleName("cw-RichText");
    grid.setWidget(0, 0, toolbar);
    grid.setWidget(1, 0, area);
    return grid;
}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ScheduleItemAdderUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    
    private String type;
    private Date date;
    private String title;
    private String topic;
    
    public ScheduleItemAdderUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        this.type = workspace.getTypeBox().getSelectionModel().getSelectedItem().toString();
        // Minor fixes needed for getting the Date portion?
        LocalDate ld = workspace.getDatePicker().getValue();
        Calendar c = Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        this.date = c.getTime();
        
        this.title = workspace.getScheduleItemTitleTextField().getText();
        this.topic = workspace.getTopicTextField().getText();
    }
    
    @Override
    public void doTransaction() {
        data.addScheduleItem(type, date, title, topic);
    }
    
    @Override
    public void undoTransaction() {
        data.removeScheduleItem(title, date);
    }
}

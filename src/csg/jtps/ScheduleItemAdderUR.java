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
import javafx.scene.control.DatePicker;
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
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    
    public ScheduleItemAdderUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        this.type = workspace.getTypeBox().getSelectionModel().getSelectedItem().toString();
        // Minor fixes needed for getting the Date portion?
        LocalDate ld = workspace.getDatePicker().getValue();
        Calendar c = Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
        this.date = c.getTime();
        this.time = workspace.getTimeTextField().getText();
        this.title = workspace.getScheduleItemTitleTextField().getText();
        this.topic = workspace.getTopicTextField().getText();
        this.link = workspace.getLinkTextField().getText();
        this.criteria = workspace.getCriteriaTextField().getText();
    }
    
    @Override
    public void doTransaction() {
        data.addScheduleItem(type, date, time, title, topic, link, criteria);
        
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
    }
    
    @Override
    public void undoTransaction() {
        data.removeScheduleItem(title, date);
    }
}

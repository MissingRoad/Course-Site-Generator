/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ScheduleItem;
import csg.workspace.CSGWorkspace;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ScheduleItemEditUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private ScheduleItem s;
    
    private String type;
    private Date date;
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    
    private String newType;
    private Date newDate;
    private String newTime;
    private String newTitle;
    private String newTopic;
    private String newLink;
    private String newCriteria;
    
    public ScheduleItemEditUR(CourseSiteGeneratorApp app) {
        this.app = app;
        
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView scheduleItems = workspace.getScheduleItems();
        
        Object selectedItem = scheduleItems.getSelectionModel().getSelectedItem();
        this.s = (ScheduleItem)selectedItem;
        
        this.type = s.getType();
        this.date = s.getDate();
        this.time = s.getTime();
        this.title = s.getTitle();
        this.topic = s.getTopic();
        this.link = s.getLink();
        this.criteria = s.getCriteria();
        
        ComboBox typeTextField = workspace.getTypeBox();
        DatePicker datePicker = workspace.getDatePicker();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();
        
        this.newType = typeTextField.getSelectionModel().getSelectedItem().toString();
        LocalDate ld = datePicker.getValue();
        Calendar c = Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        this.newDate = c.getTime();
        this.time = timeTextField.getText();
        this.title = titleTextField.getText();
        this.topic = topicTextField.getText();
        this.link = linkTextField.getText();
        this.criteria = criteriaTextField.getText();
    }
    
    @Override
    public void doTransaction() {
        this.s.setType(newType);
        this.s.setDate(newDate);
        this.s.setTime(newTime);
        this.s.setTitle(newTitle);
        this.s.setTopic(newTopic);
        this.s.setLink(newLink);
        this.s.setCriteria(newCriteria);
    }
    
    @Override
    public void undoTransaction() {
        this.s.setType(type);
        this.s.setDate(date);
        this.s.setTime(time);
        this.s.setTitle(title);
        this.s.setTopic(topic);
        this.s.setLink(link);
        this.s.setCriteria(criteria);
    }
}

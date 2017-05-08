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
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class DatePickerChangeUR implements jTPS_Transaction {

    private CourseSiteGeneratorApp app;
    private CSGData data;
    //private TableView scheduleItems;
    private Date startMondayDate;
    private Date endFridayDate;
    private Date newStartMondayDate;
    private Date newEndFridayDate;

    private ObservableList<ScheduleItem> oldObservableList;
    private ObservableList<ScheduleItem> newObservableList;
    
    public DatePickerChangeUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.startMondayDate = data.getStartDate();
        this.endFridayDate = data.getEndDate();
        
        DatePicker startMondayPicker = workspace.getStartingMondayPicker();
        DatePicker endFridayPicker = workspace.getEndingFridayPicker();

        LocalDate ldMonday = startMondayPicker.getValue();
        LocalDate ldFriday = endFridayPicker.getValue();

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.set(ldMonday.getYear(), ldMonday.getMonthValue() - 1, ldMonday.getDayOfMonth());
        c2.set(ldFriday.getYear(), ldFriday.getMonthValue() - 1, ldFriday.getDayOfMonth());
        
        this.newStartMondayDate = c1.getTime();
        this.newEndFridayDate = c2.getTime();
        
        this.oldObservableList = FXCollections.observableArrayList();
        this.newObservableList = FXCollections.observableArrayList();
        
        ObservableList<ScheduleItem> scheduleItemsList = data.getObservableScheduleItems();
        
        for (ScheduleItem s : scheduleItemsList) {
            int a = s.compareTo(startMondayDate);
            int b = s.compareTo(endFridayDate);
            if (a >= 0 && b <= 0) {
                oldObservableList.add(s);
            }
        }
        
        for (ScheduleItem s : scheduleItemsList) {
            int a = s.compareTo(newStartMondayDate);
            int b = s.compareTo(newEndFridayDate);
            if (a >= 0 && b <= 0) {
                newObservableList.add(s);
            }
        }
    }

    @Override
    public void doTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        DatePicker startMondayPicker = workspace.getStartingMondayPicker();
        DatePicker endFridayPicker = workspace.getEndingFridayPicker();
        
        LocalDate newStartLocalDate = newStartMondayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate newEndLocalDate = newEndFridayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        workspace.maskDatePickerTriggered();
        
        startMondayPicker.setValue(newStartLocalDate);
        endFridayPicker.setValue(newEndLocalDate);
        
        data.setStartDate(newStartMondayDate);
        data.setEndDate(newEndFridayDate);

        data.clearObservableScheduleItemsList();

        ObservableList<ScheduleItem> scheduleItemsList = data.getScheduleItems();

        for (ScheduleItem s : newObservableList) {
            data.addObservableScheduleItem(s);
        }

        workspace.maskDatePickerTriggered();
    }
    
    @Override
    public void undoTransaction() {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        DatePicker startMondayPicker = workspace.getStartingMondayPicker();
        DatePicker endFridayPicker = workspace.getEndingFridayPicker();
        
        workspace.maskDatePickerTriggered();
        
        data.setStartDate(startMondayDate);
        data.setEndDate(endFridayDate);
        
        LocalDate startLocalDate = startMondayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endFridayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        startMondayPicker.setValue(startLocalDate);
        endFridayPicker.setValue(endLocalDate);
        
        data.clearObservableScheduleItemsList();
        
        for (ScheduleItem s: oldObservableList) {
            data.addObservableScheduleItem(s);
        }
        
        workspace.maskDatePickerTriggered();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import csg.CourseSiteGeneratorApp;
//import csg.data.TAData;
import csg.file.TimeSlot;

/**
 *
 * @author zhaotingyi
 */
public class TAhourschangeUR implements jTPS_Transaction{
    
    private CourseSiteGeneratorApp app;
    private int startTime;
    private int endTime;
    private int newStartTime;
    private int newEndTime;
    private ArrayList<TimeSlot> officeHours;
    
    public TAhourschangeUR(CourseSiteGeneratorApp app){
        this.app = app;
        CSGData data = (CSGData)app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        startTime = data.getStartHour();
        endTime = data.getEndHour();
        newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        officeHours = TimeSlot.buildOfficeHoursList(data);
    }

    @Override
    public void doTransaction() {
        ((CSGWorkspace)app.getWorkspaceComponent()).getTADataOfficeHoursGridPane().getChildren().clear();
        ((CSGData)app.getDataComponent()).changeTime(newStartTime, newEndTime, officeHours);
    }

    @Override
    public void undoTransaction() {
        ((CSGWorkspace)app.getWorkspaceComponent()).getTADataOfficeHoursGridPane().getChildren().clear();
        ((CSGData)app.getDataComponent()).changeTime(startTime, endTime, officeHours);
    }
    
}

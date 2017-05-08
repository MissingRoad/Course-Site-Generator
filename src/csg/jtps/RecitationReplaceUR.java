/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class RecitationReplaceUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private CSGWorkspace workspace;
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private TeachingAssistant ta1;
    private TeachingAssistant ta2;
    
    // The values to be replaced
    private String newSection;
    private String newInstructor;
    private String newDayTime;
    private String newLocation;
    private TeachingAssistant newTA1;
    private TeachingAssistant newTA2;
    
    Recitation recitation;
    
    public RecitationReplaceUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        this.workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView recitationTable = workspace.getRecitationData();
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        this.recitation = (Recitation)selectedItem;
        this.section = recitation.getSection();
        this.instructor = recitation.getInstructor();
        this.dayTime = recitation.getDayTime();
        this.location = recitation.getLocation();
        this.ta1 = recitation.getSupervisingTA1();
        this.ta2 = recitation.getSupervisingTA2();
        
        // The new data
        this.newSection = workspace.getSectionTextField().getText();
        this.newInstructor = workspace.getInstructorTextField().getText();
        this.newDayTime = workspace.getDayTimeTextField().getText();
        this.newLocation = workspace.getLocationTextField().getText();
        this.newTA1 = (TeachingAssistant)workspace.getSupervisingTa1Box().getSelectionModel().getSelectedItem();
        this.newTA2 = (TeachingAssistant)workspace.getSupervisingTa2Box().getSelectionModel().getSelectedItem();
    }
    
    @Override
    public void doTransaction() {
        TableView recitationTable = workspace.getRecitationData();
        recitation.setInstructor(newInstructor);
        recitation.setSection(newSection);
        recitation.setDayTime(newDayTime);
        recitation.setLocation(newLocation);
        recitation.setSupervisingTA1(newTA1);
        recitation.setSupervisingTA2(newTA2);
        recitationTable.refresh();
    }
    
    @Override
    public void undoTransaction() {
        TableView recitationTable = workspace.getRecitationData();
        recitation.setInstructor(instructor);
        recitation.setSection(section);
        recitation.setDayTime(dayTime);
        recitation.setLocation(location);
        recitation.setSupervisingTA1(ta1);
        recitation.setSupervisingTA2(ta2);
        recitationTable.refresh();
    }
}

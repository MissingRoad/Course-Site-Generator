/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class RecitationAdderUR implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private TeachingAssistant ta1;
    private TeachingAssistant ta2;
    
    private CourseSiteGeneratorApp app;
    private CSGWorkspace workspace;
    
    public RecitationAdderUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.section = workspace.getSectionTextField().getText();
        this.instructor = workspace.getInstructorTextField().getText();
        this.dayTime = workspace.getDayTimeTextField().getText();
        this.location = workspace.getLocationTextField().getText();
        this.ta1 = (TeachingAssistant)workspace.getSupervisingTa1Box().getSelectionModel().getSelectedItem();
        this.ta2 = (TeachingAssistant)workspace.getSupervisingTa2Box().getSelectionModel().getSelectedItem();
    }
    
    @Override
    public void doTransaction() {
        CSGData data = (CSGData)app.getDataComponent();
        data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
    }
    
    @Override
    public void undoTransaction() {
        CSGData data = (CSGData)app.getDataComponent();
        data.removeRecitation(section, instructor);
    }
}

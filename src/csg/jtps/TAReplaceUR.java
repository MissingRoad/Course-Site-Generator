/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.CourseSiteGeneratorApp;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAReplaceUR implements jTPS_Transaction{
    private String TAname;
    private String TAemail;
    private String newName;
    private String newEmail;
    private CourseSiteGeneratorApp app;
    private TAData data;
    
    public TAReplaceUR(CourseSiteGeneratorApp app){
        this.app = app;
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        data = (TAData)app.getDataComponent();
        newName = workspace.getTANameTextField().getText();
        newEmail = workspace.getTAEmailTextField().getText();
        TableView taTable = workspace.getTAInformationTable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        TAname = ta.getName();
        TAemail = ta.getEmail();
    }

    @Override
    public void doTransaction() {
        data.replaceTAName(TAname, newName);
        data.removeTA(TAname);
        data.addTA(newName, newEmail);
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();
        taTable.getSelectionModel().select(data.getTA(newName));
    }

    @Override
    public void undoTransaction() {
        data.replaceTAName(newName, TAname);
        data.removeTA(newName);
        data.addTA(TAname, TAemail);
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();
        taTable.getSelectionModel().select(data.getTA(TAname));
    }
    
    
    
}

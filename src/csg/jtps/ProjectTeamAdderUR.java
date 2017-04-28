/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ProjectTeamAdderUR implements jTPS_Transaction {
    CourseSiteGeneratorApp app;
    
    private String name;
    private Color teamColor;
    private Color textColor;
    private String link;
    
    private CSGData data;
    private CSGWorkspace workspace;
    
    public ProjectTeamAdderUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.data = (CSGData)app.getDataComponent();
        TextField nameTextField = this.workspace.getTeamNameTextField();
        ColorPicker teamColorPicker = this.workspace.getTeamColorPicker();
        ColorPicker textColorPicker = this.workspace.getTeamTextColorPicker();
        TextField linkTextField = this.workspace.getLinkTextField();
        
        this.name = nameTextField.getText();
        this.teamColor = teamColorPicker.getValue();
        this.textColor = textColorPicker.getValue();
        this.link = linkTextField.getText();
    }
    
    @Override
    public void doTransaction() {
        this.data.addProjectTeam(name, teamColor, teamColor, link);
    }
    
    @Override
    public void undoTransaction() {
        this.data.removeProjectTeam(name);
    }
}

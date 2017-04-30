/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ProjectTeamReplaceUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private ProjectTeam team;
    
    private String name;
    private Color teamColor;
    private Color teamTextColor;
    private String link;
    
    private String newName;
    private Color newTeamColor;
    private Color newTeamTextColor;
    private String newLink;
    
    public ProjectTeamReplaceUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView projectTeamsTable = workspace.getProjectTeams();
        Object selectedObject = projectTeamsTable.getSelectionModel().getSelectedItem();
        this.team = (ProjectTeam)selectedObject;
        this.name = team.getName();
        this.teamColor = team.getColor();
        this.teamTextColor = team.getTextColor();
        this.link = team.getLink();
        
        this.newName = workspace.getTeamNameTextField().getText();
        this.newTeamColor = workspace.getTeamColorPicker().getValue();
        this.newTeamTextColor = workspace.getTeamTextColorPicker().getValue();
        this.newLink = workspace.getTeamLinkTextField().getText();
    }
    
    @Override
    public void doTransaction() {
        team.setName(newName);
        team.setColor(newTeamColor);
        team.setTextColor(newTeamTextColor);
        team.setLink(newLink);
    }
    
    @Override
    public void undoTransaction() {
        team.setName(name);
        team.setColor(teamColor);
        team.setTextColor(teamTextColor);
        team.setLink(link);
    }
}

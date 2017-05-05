/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.data.Student;
import csg.workspace.CSGWorkspace;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ProjectTeamDeleteUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private ProjectTeam team;
    private ObservableList<Student> teamMembers;
    private CSGData data;
    private String name;
    private Color teamColor;
    private Color teamTextColor;
    private String link;
    
    public ProjectTeamDeleteUR(CourseSiteGeneratorApp app, String name) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TableView projectTeamData = workspace.getProjectTeams();
        this.team = data.findProjectTeam(name);
        this.teamMembers = team.getTeamMembers();
        this.name = name;
        this.teamColor = team.getColor();
        this.teamTextColor = team.getTextColor();
        
    }
    
    @Override
    public void doTransaction() {
        for (Student s: teamMembers) {
            this.data.removeStudent(s);
        }
        this.data.removeProjectTeam(name);
    }
    
    @Override
    public void undoTransaction() {
        this.data.addProjectTeam(team);
        for (Student s: teamMembers) {
            this.team.addStudent(s);
            this.data.addStudent(s);
        }
    }
}

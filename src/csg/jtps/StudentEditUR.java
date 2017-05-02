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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class StudentEditUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private Student s;
    
    private String firstName;
    private String lastName;
    private ProjectTeam team;
    private String role;
    
    private String newFirstName;
    private String newLastName;
    private ProjectTeam newTeam;
    private String newRole;
    
    public StudentEditUR(CourseSiteGeneratorApp app) {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.data = (CSGData)app.getDataComponent();
        TableView teamMembers = workspace.getTeamMembers();
        Object selectedTeamMember = teamMembers.getSelectionModel().getSelectedItem();
        Student s = (Student)selectedTeamMember;
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.team = s.getTeam();
        this.role = s.getRole();
        
        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamBox = workspace.getTeamBox();
        TextField roleTextField = workspace.getRoleTextField();
        
        this.newFirstName = firstNameTextField.getText();
        this.newLastName = lastNameTextField.getText();
        this.newTeam = (ProjectTeam)teamBox.getSelectionModel().getSelectedItem();
        this.newRole = roleTextField.getText();
    }
    
    @Override
    public void doTransaction() {
        data.editStudent(newFirstName, newLastName, newTeam, newRole);
        /*s.setFirstName(newFirstName);
        s.setLastName(newLastName);
        s.setTeam(newTeam);
        s.setRole(newRole);*/
    }
    
    @Override
    public void undoTransaction() {
        data.editStudent(firstName, lastName, team, role);
        /*s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setTeam(team);
        s.setRole(role);*/
    }
}

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
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class StudentAdderUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private ProjectTeam p;
    private String firstName;
    private String lastName;
    private String role;
    
    public StudentAdderUR(CourseSiteGeneratorApp app) {
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        this.data = (CSGData)app.getDataComponent();
        ComboBox projectTeamBox = workspace.getTeamBox();
        Object selectedItem = projectTeamBox.getSelectionModel().getSelectedItem();
        this.p = (ProjectTeam)selectedItem;
        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        TextField roleTextField = workspace.getRoleTextField();
        
        this.firstName = firstNameTextField.getText();
        this.lastName = lastNameTextField.getText();
        this.role = roleTextField.getText();
    }
    
    @Override
    public void doTransaction() {
        Student s = new Student(firstName, lastName, p, role);
        p.addStudent(s);
        data.addStudent(s);
    }
    
    @Override
    public void undoTransaction() {
        p.removeStudent(firstName + lastName);
        data.removeStudent(firstName, lastName);
    }
}

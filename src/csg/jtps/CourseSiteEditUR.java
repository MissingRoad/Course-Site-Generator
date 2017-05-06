/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.CourseSite;
import csg.workspace.CSGWorkspace;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class CourseSiteEditUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private CourseSite cs;
    private CSGWorkspace workspace;
    
    private String oldSubject;
    private String oldNumber;
    private String oldSemester;
    private int oldYear;
    private String oldTitle;
    private String oldInstName;
    private String oldInstHome;
    
    private String newSubject;
    private String newNumber;
    private String newSemester;
    private int newYear;
    private String newTitle;
    private String newInstName;
    private String newInstHome;
    
    public CourseSiteEditUR(CourseSiteGeneratorApp app) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        this.cs = data.getCourseSiteInfo();
        this.workspace = (CSGWorkspace)app.getWorkspaceComponent();
        
        ComboBox subjectComboBox = workspace.getSubjectComboBox();
        ComboBox numberComboBox = workspace.getNumberComboBox();
        ComboBox semesterComboBox = workspace.getSemesterComboBox();
        ComboBox yearComboBox = workspace.getYearComboBox();
        TextField titleTextField = workspace.getTitleTextField();
        TextField instNameTextField = workspace.getInstructorNameTextField();
        TextField instHomeTextField = workspace.getInstructorHomeTextField();
        
        this.oldSubject = cs.getCourseSubject();
        this.oldNumber = cs.getCourseNumber();
        this.oldSemester = cs.getCourseSemester();
        this.oldYear = cs.getCourseYear();
        this.oldTitle = cs.getCourseTitle();
        this.oldInstName = cs.getInstName();
        this.oldInstHome = cs.getInstHome();
        
        this.newSubject = subjectComboBox.getSelectionModel().getSelectedItem().toString();
        this.newNumber = numberComboBox.getSelectionModel().getSelectedItem().toString();
        this.newSemester = semesterComboBox.getSelectionModel().getSelectedItem().toString();
        this.newYear = Integer.parseInt(yearComboBox.getSelectionModel().getSelectedItem().toString());
        this.newTitle = titleTextField.getText();
        this.newInstName = instNameTextField.getText();
        this.newInstHome = instHomeTextField.getText();
    }
    
    @Override
    public void doTransaction() {
        cs.setCourseSubject(newSubject);
        cs.setCourseNumber(newNumber);
        cs.setCourseSemester(newSemester);
        cs.setCourseYear(newYear);
        cs.setCourseTitle(newTitle);
        cs.setInstName(newInstName);
        cs.setInstHome(newInstHome);
        
        ComboBox subjectComboBox = workspace.getSubjectComboBox();
        ComboBox numberComboBox = workspace.getNumberComboBox();
        ComboBox semesterComboBox = workspace.getSemesterComboBox();
        ComboBox yearComboBox = workspace.getYearComboBox();
        Label titleValue = workspace.getTitleValueLabel();
        Label instNameValue = workspace.getInstructorNameValueLabel();
        Label instHomeValue = workspace.getInstructorHomeValueLabel();
        
        subjectComboBox.getSelectionModel().select(newSubject);
        numberComboBox.getSelectionModel().select(newNumber);
        semesterComboBox.getSelectionModel().select(newSemester);
        yearComboBox.getSelectionModel().select(newYear);
        titleValue.setText(newTitle);
        instNameValue.setText(newInstName);
        instHomeValue.setText(newInstHome);
    }
    
    @Override
    public void undoTransaction() {
        cs.setCourseSubject(oldSubject);
        cs.setCourseNumber(oldNumber);
        cs.setCourseSemester(oldSemester);
        cs.setCourseYear(oldYear);
        cs.setCourseTitle(oldTitle);
        cs.setInstName(oldInstName);
        cs.setInstHome(oldInstHome);
        
        ComboBox subjectComboBox = workspace.getSubjectComboBox();
        ComboBox numberComboBox = workspace.getNumberComboBox();
        ComboBox semesterComboBox = workspace.getSemesterComboBox();
        ComboBox yearComboBox = workspace.getYearComboBox();
        Label titleValue = workspace.getTitleValueLabel();
        Label instNameValue = workspace.getInstructorNameValueLabel();
        Label instHomeValue = workspace.getInstructorHomeValueLabel();
        
        subjectComboBox.getSelectionModel().select(oldSubject);
        numberComboBox.getSelectionModel().select(oldNumber);
        semesterComboBox.getSelectionModel().select(oldSemester);
        yearComboBox.getSelectionModel().select(oldYear);
        titleValue.setText(oldTitle);
        instNameValue.setText(oldInstName);
        instHomeValue.setText(oldInstHome);
    }
}

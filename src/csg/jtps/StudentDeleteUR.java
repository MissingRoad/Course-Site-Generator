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
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class StudentDeleteUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private Student s;
    private String firstName;
    private String lastName;
    private ProjectTeam team;
    private String role;
    
    public StudentDeleteUR(CourseSiteGeneratorApp app, Student s) {
        this.app = app;
        this.s = s;
        this.data = (CSGData)app.getDataComponent();
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.team = s.getTeam();
        this.role = s.getRole();
    }
    
    @Override
    public void doTransaction() {
        team.removeStudent(s);
        data.removeStudent(s);
    }
    
    @Override
    public void undoTransaction() {
        team.addStudent(s);
        data.addStudent(s);
    }
}

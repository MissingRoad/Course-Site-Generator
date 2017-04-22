/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsli
 */
public class Student {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty teamName;
    private final StringProperty role;
    private ProjectTeam team;
    
    public Student(String firstName, String lastName, ProjectTeam t, String role) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.teamName = new SimpleStringProperty(t.getName());
        this.team = t;
        this.role = new SimpleStringProperty(role);
    }
    
    public String getFirstName() {
        return this.firstName.get();
    }
    
    public void setFirstName(String newFirstName) {
        this.firstName.set(newFirstName);
    }
    
    public String getLastName() {
        return this.lastName.get();
    }
    
    public void setLastName(String newLastName) {
        this.lastName.set(newLastName);
    }
    
    public String getTeamName() {
        return this.teamName.get();
    }
    
    public void setTeamName(String newTeamName) {
        this.teamName.set(newTeamName);
    }
    
    public String getRole() {
        return this.role.get();
    }
    
    public void setRole(String newRole) {
        this.role.set(newRole);
    }
}

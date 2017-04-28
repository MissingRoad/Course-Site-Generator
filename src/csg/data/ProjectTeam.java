/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.scene.paint.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dsli
 */
public class ProjectTeam {
    private final StringProperty name;
    private Color color;
    private Color textColor;
    // (The above values are HEX String representations of the Color)
    private final StringProperty link;
    private ObservableList<Student> teamMembers;
    
    public ProjectTeam(String name, Color c, Color t, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = c;
        this.textColor = t;
        this.link = new SimpleStringProperty(link);
        this.teamMembers = FXCollections.observableArrayList();
    }
    
    public void addStudent(Student s) {
        this.teamMembers.add(s);
    }
    
    public void removeStudent(Student s) {
        if (this.teamMembers.contains(s)) {
            this.teamMembers.remove(s);
        }
    }
    
    public ObservableList<Student> getTeamMembers() {
        return teamMembers;
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public void setName(String newName) {
        this.name.set(newName);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(Color newC) {
        this.color = newC;
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setTextColor(Color newTextColor) {
        this.textColor = newTextColor;
    }
    
    public String getLink() {
        return this.link.get();
    }
    
    public void setLink(String newLink) {
        this.link.set(newLink);
    }
    
    // Compare and toString methods to be implemented later
}

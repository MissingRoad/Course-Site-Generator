/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.awt.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsli
 */
public class ProjectTeam {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    // (The above values are HEX String representations of the Color)
    private final StringProperty link;
    
    public ProjectTeam(String name, Color c, Color t, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(c.toString());
        this.textColor = new SimpleStringProperty(t.toString());
        this.link = new SimpleStringProperty(link);
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public void setName(String newName) {
        this.name.set(newName);
    }
    
    public String getColor() {
        return this.color.get();
    }
    
    public void setColor(Color newC) {
        this.color.set(newC.toString());
    }
    
    public String getTextColor() {
        return this.textColor.get();
    }
    
    public void setTextColor(Color newTextColor) {
        this.textColor.set(newTextColor.toString());
    }
    
    public String getLink() {
        return this.link.get();
    }
    
    public void setLink(String newLink) {
        this.link.set(newLink);
    }
    
    // Compare and toString methods to be implemented later
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsli
 */
public class CourseSitePage {
    private final BooleanProperty exists;
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    
    public CourseSitePage(boolean exists, String navbarTitle, String fileName, String script) {
        this.exists = new SimpleBooleanProperty(exists);
        this.navbarTitle = new SimpleStringProperty(navbarTitle);
        this.fileName = new SimpleStringProperty(fileName);
        this.script = new SimpleStringProperty(script);
        
        this.exists.addListener(e -> {
            //System.out.println(this.navbarTitle.get() + this.exists.get());
        });
    }
    
    public void setExists(boolean exists) {
        this.exists.set(exists);
    }
    
    public boolean getExists() {
        return this.exists.get();
    }
    
    public BooleanProperty existsProperty() {
        return this.exists;
    }
    
    public void setNavbarTitle(String navbarTitle) {
        this.navbarTitle.set(navbarTitle);
    }
    
    public String getNavbarTitle() {
        return this.navbarTitle.get();
    }
    
    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
    
    public String getFileName() {
        return this.fileName.get();
    }
    
    public void setScript(String script) {
        this.script.set(script);
    }
    
    public String getScript() {
        return this.script.get();
    }
}

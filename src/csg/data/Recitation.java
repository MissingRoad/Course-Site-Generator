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
public class Recitation {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private TeachingAssistant supervisingTA1;
    private TeachingAssistant supervisingTA2;
    
    public Recitation(String section, String instructor, String dayTime, String location, TeachingAssistant ta1, TeachingAssistant ta2) {
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayTime = new SimpleStringProperty(dayTime);
        this.location = new SimpleStringProperty(location);
        this.supervisingTA1 = ta1;
        this.supervisingTA2 = ta2;
    }
    
    public void setSection(String newSection) {
        section.set(newSection);
    }
    
    public String getSection() {
        return section.get();
    }
    
    public void setInstructor(String newInstructor) {
        instructor.set(newInstructor);
    }
    
    public String getInstructor() {
        return instructor.get();
    }
    
    public void setDayTime(String newDayTime) {
        dayTime.set(newDayTime);
    }
    
    public String getDayTime() {
        return dayTime.get();
    }
    
    public void setLocation(String newLocation) {
        location.set(newLocation);
    }
    
    public String getLocation() {
        return location.get();
    }

    public TeachingAssistant getSupervisingTA1() {
        return supervisingTA1;
    }

    public void setSupervisingTA1(TeachingAssistant supervisingTA1) {
        this.supervisingTA1 = supervisingTA1;
    }

    public TeachingAssistant getSupervisingTA2() {
        return supervisingTA2;
    }

    public void setSupervisingTA2(TeachingAssistant supervisingTA2) {
        this.supervisingTA2 = supervisingTA2;
    }

    // MAKE SURE TO IMPLEMENT COMPARETO() and TOSTRING() eventually
}

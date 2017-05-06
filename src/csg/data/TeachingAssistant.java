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
public class TeachingAssistant <E extends Comparable<E>> implements Comparable<E> {
    private final StringProperty name;
    private final StringProperty email;
    private final BooleanProperty isUndergrad;
    
    public TeachingAssistant(String initName, String initEmail, boolean isUndergrad) {
        this.name = new SimpleStringProperty(initName);
        this.email = new SimpleStringProperty(initEmail);
        this.isUndergrad = new SimpleBooleanProperty(isUndergrad);
        
        
    }
    
        // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    public boolean isUndergrad() {
        return isUndergrad.get();
    }
    
    public BooleanProperty isUndergradProperty() {
        return isUndergrad;
    }
    
    public void setIsUndergrad(boolean isUndergrad) {
        this.isUndergrad.set(isUndergrad);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}

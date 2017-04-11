/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author dsli
 */
public class CourseSite {
    private final BooleanProperty hasHomePage;
    private final BooleanProperty hasSyllabusPage;
    private final BooleanProperty hasSchedulePage;
    private final BooleanProperty hasHWPage;
    private final BooleanProperty hasProjectPage;
    
    public CourseSite(boolean hasHomePage, boolean hasSyllabusPage, boolean hasSchedulePage, boolean hasHWPage, boolean hasProjectPage) {
        this.hasHomePage = new SimpleBooleanProperty(hasHomePage);
        this.hasSyllabusPage = new SimpleBooleanProperty(hasSyllabusPage);
        this.hasSchedulePage = new SimpleBooleanProperty(hasSchedulePage);
        this.hasHWPage = new SimpleBooleanProperty(hasHWPage);
        this.hasProjectPage = new SimpleBooleanProperty(hasProjectPage);
    }
    
    //Getters and setters

    public boolean getHasHomePage() {
        return hasHomePage.get();
    }
    
    public void setHasHomePage(boolean hasHomePage) {
        this.hasHomePage.set(hasHomePage);
    }

    public boolean getHasSyllabusPage() {
        return hasSyllabusPage.get();
    }
    
    public void setHasSyllabusPage(boolean hasSyllabusPage) {
        this.hasSyllabusPage.set(hasSyllabusPage);
    }

    public boolean getHasSchedulePage() {
        return hasSchedulePage.get();
    }
    
    public void setHasSchedulePage(boolean hasSchedulePage) {
        this.hasSchedulePage.set(hasSchedulePage);
    }

    public boolean getHasHWPage() {
        return hasHWPage.get();
    }
    
    public void setHasHWPage(boolean hasHWPage) {
        this.hasHWPage.set(hasHWPage);
    }

    public boolean getHasProjectPage() {
        return hasProjectPage.get();
    }
    
    public void setHasProjectPage(boolean hasProjectPage) {
        this.hasProjectPage.set(hasProjectPage);
    }
    
}

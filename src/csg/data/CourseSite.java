/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsli
 */
public class CourseSite {
    private final StringProperty courseSubject;
    private final IntegerProperty courseNumber;
    private final StringProperty courseSemester;
    private final IntegerProperty courseYear;
    private final StringProperty courseTitle;
    private final StringProperty instName;
    private final StringProperty instHome;
    private final BooleanProperty hasHomePage;
    private final BooleanProperty hasSyllabusPage;
    private final BooleanProperty hasSchedulePage;
    private final BooleanProperty hasHWPage;
    private final BooleanProperty hasProjectPage;
    
    public CourseSite() {
        this.courseSubject = new SimpleStringProperty("");
        this.courseNumber = new SimpleIntegerProperty(219);
        this.courseSemester = new SimpleStringProperty("");
        this.courseYear = new SimpleIntegerProperty(2017);
        this.courseTitle = new SimpleStringProperty("");
        this.instName = new SimpleStringProperty("");
        this.instHome = new SimpleStringProperty("");
        this.hasHomePage = new SimpleBooleanProperty(false);
        this.hasSyllabusPage = new SimpleBooleanProperty(false);
        this.hasSchedulePage = new SimpleBooleanProperty(false);
        this.hasHWPage = new SimpleBooleanProperty(false);
        this.hasProjectPage = new SimpleBooleanProperty(false);
    }
    
    //Getters and setters
    public void setCourseSubject(String newCourseSubject) {
        this.courseSubject.set(newCourseSubject);
    }
    
    public String getCourseSubject() {
        return this.courseSubject.get();
    }
    
    public void setCourseNumber(int newCourseNumber) {
        this.courseNumber.set(newCourseNumber);
    }
    
    public int getCourseNumber() {
        return this.courseNumber.get();
    }
    
    public void setCourseSemester(String newCourseSemester) {
        this.courseSemester.set(newCourseSemester);
    }
    
    public String getCourseSemester() {
        return this.courseSemester.get();
    }
    
    public void setCourseYear(int newCourseYear) {
        this.courseYear.set(newCourseYear);
    }
    
    public int getCourseYear() {
        return this.courseYear.get();
    }
    
    public void setCourseTitle(String newCourseTitle) {
        this.courseTitle.set(newCourseTitle);
    }

    public String getCourseTitle() {
        return this.courseTitle.get();
    }
    
    public void setInstName(String newInstName) {
        this.instName.set(newInstName);
    }
    
    public String getInstName() {
        return this.instName.get();
    }
    
    public void setInstHome(String newInstHome) {
        this.instHome.set(newInstHome);
    }
    
    public String getInstHome() {
        return this.instHome.get();
    }
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

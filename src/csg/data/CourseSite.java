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
    private final StringProperty courseNumber;
    private final StringProperty courseSemester;
    private final IntegerProperty courseYear;
    private final StringProperty courseTitle;
    private final StringProperty instName;
    private final StringProperty instHome;
    private final StringProperty exportDir;
    private final BooleanProperty hasHomePage;
    private final BooleanProperty hasSyllabusPage;
    private final BooleanProperty hasSchedulePage;
    private final BooleanProperty hasHWPage;
    private final BooleanProperty hasProjectPage;
    private CourseSitePage homePage;
    private CourseSitePage schedulePage;
    private CourseSitePage syllabusPage;
    private CourseSitePage hwPage;
    private CourseSitePage projectsPage;
    //private CSGWorkspace workspace;
    //private CourseSiteGeneratorApp app;
    
    public CourseSite() {
        this.courseSubject = new SimpleStringProperty("");
        this.courseNumber = new SimpleStringProperty("");
        this.courseSemester = new SimpleStringProperty("");
        this.courseYear = new SimpleIntegerProperty();
        this.courseTitle = new SimpleStringProperty("");
        this.instName = new SimpleStringProperty("");
        this.instHome = new SimpleStringProperty("");
        this.exportDir = new SimpleStringProperty("");
        this.hasHomePage = new SimpleBooleanProperty(false);
        this.hasSyllabusPage = new SimpleBooleanProperty(false);
        this.hasSchedulePage = new SimpleBooleanProperty(false);
        this.hasHWPage = new SimpleBooleanProperty(false);
        this.hasProjectPage = new SimpleBooleanProperty(false);
        this.homePage = new CourseSitePage(false, "Home", "index.html", "HomeBuilder.js");
        this.syllabusPage = new CourseSitePage(false, "Syllabus", "syllabus.html", "SyllabusBuilder.js");
        this.schedulePage = new CourseSitePage(false, "Schedule", "schedule.html", "ScheduleBuilder.js");
        this.hwPage = new CourseSitePage(false, "HWs", "hws.html", "HWsBuilder.js");
        this.projectsPage = new CourseSitePage(false, "Projects", "projects.html", "ProjectsBuilder.js");
        //this.app = app;
        //this.workspace = (CSGWorkspace)app.getWorkspaceComponent();
    }

    public CourseSitePage getHomePage() {
        return homePage;
    }

    public CourseSitePage getSchedulePage() {
        return schedulePage;
    }

    public CourseSitePage getSyllabusPage() {
        return syllabusPage;
    }

    public CourseSitePage getHwPage() {
        return hwPage;
    }

    public CourseSitePage getProjectsPage() {
        return projectsPage;
    }
    
    //Getters and setters
    public void setCourseSubject(String newCourseSubject) {
        this.courseSubject.set(newCourseSubject);
        
    }
    
    public String getCourseSubject() {
        return this.courseSubject.get();
    }
    
    public void setCourseNumber(String newCourseNumber) {
        this.courseNumber.set(newCourseNumber);
    }
    
    public String getCourseNumber() {
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
        //this.workspace.getInstructorNameValueLabel().setText(newInstName);
    }
    
    public String getInstName() {
        return this.instName.get();
    }
    
    public void setInstHome(String newInstHome) {
        this.instHome.set(newInstHome);
        //this.workspace.getInstructorHomeValueLabel().setText(newInstHome);
    }
    
    public String getInstHome() {
        return this.instHome.get();
    }
    
    public void setExportDir(String newExportDir) {
        this.exportDir.set(newExportDir);
        //((CSGData)app.getDataComponent()).setExportDir(newExportDir);
    }
    
    public String getExportDir() {
        return this.exportDir.get();
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

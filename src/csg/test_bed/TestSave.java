/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.file.CSGFiles;
import csg.style.CSGStyle;
import csg.workspace.CSGWorkspace;
import djf.settings.AppStartupConstants;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import static javafx.application.Application.launch;
//import javafx.stage.Stage;

/**
 *
 * @author dsli
 */
public class TestSave {

    CourseSiteGeneratorApp app;
    CSGData dataComponent;
    CSGFiles fileComponent;
    CSGWorkspace workspaceComponent;
    CSGStyle styleComponent;

    public TestSave() {
        // Modify this.  Construct data and file without needing the AppTemplate/CSGApp
        app = new CourseSiteGeneratorApp();
        app.loadProperties("app_properties.xml");
        testBuildHookComponents();
    }

    public void testBuildHookComponents() {
        this.dataComponent = new CSGData(app);
        this.fileComponent = new CSGFiles(app);
    }

    public void testSaveData() {
        // First, hard code the cell values of "" (assuming an empty gridPane first)
        /*for (int row = 1; row < dataComponent.getNumRows(); row++) {
            for (int col = 2; col < 7; col++) {
                StringProperty testProp = new SimpleStringProperty("");
                dataComponent.setCellProperty(col, row, testProp);
            }
        }*/
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();
        
        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            
            addCellToGrid(dataComponent, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, col, row);
                addCellToGrid(dataComponent, col, row + 1);
                col++;
            }
            row += 2;
        }
        
        dataComponent.addTA("Jacob Evans", "jacob.evans@stonybrook.edu", true);
        
        TeachingAssistant jacobEvans = new TeachingAssistant("Jacob", "Evans", true);
        TeachingAssistant jamesHoffman = new TeachingAssistant("James", "Hoffman", true);
        
        dataComponent.getRecitations().add(new Recitation("R01", "McKenna", "Monday 5:30 - 6:23 PM", "Old CS 2120", jacobEvans, jamesHoffman));
        GregorianCalendar g = new GregorianCalendar(2017, 2, 17);
        Date testDate = g.getTime();
        dataComponent.getScheduleItems().add(new ScheduleItem("Holiday", testDate, "Snow Day", ""));
        dataComponent.getScheduleItems().add(new ScheduleItem("Lecture", testDate, "Lecture 3", "Event Programming"));
        Color c1 = Color.BLACK;
        Color c2 = Color.BLUE;
        ProjectTeam sampleProjectTeam = new ProjectTeam("Team 1", c1, c2, "www.team1.com");
        dataComponent.getProjectTeams().add(sampleProjectTeam);
        dataComponent.getStudents().add(new Student("David", "Li", sampleProjectTeam, "Lead Developer"));

        // Save the hard coded data from above
        try {
            fileComponent.saveData(dataComponent, AppStartupConstants.PATH_WORK + "/SiteSaveTest.json"); // What is filePath?
        } catch (IOException e) {
            System.out.println("Bad file saving.");
        }
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public void addCellToGrid(CSGData data, int col, int row) {
        StringProperty stringProp = new SimpleStringProperty("");
        
        data.setCellProperty(col, row, stringProp);
    }
}

class TestSaveDriver {

    public static void main(String[] args) {
        TestSave t = new TestSave();

        t.testSaveData();
    }
}

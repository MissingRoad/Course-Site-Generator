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
import csg.data.TeachingAssistant;
import csg.file.CSGFiles;
import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dsli
 */
public class TestSaveTest {
    CourseSiteGeneratorApp app;
    CSGData dataComponent;
    CSGFiles fileComponent;
    
    public TestSaveTest() {
        app = new CourseSiteGeneratorApp();
        app.loadProperties("app_properties.xml");
        dataComponent = new CSGData(app);
        fileComponent = new CSGFiles(app);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of testBuildHookComponents method, of class TestSave.
     */
    @Test
    public void testTestBuildHookComponents() {
        System.out.println("testBuildHookComponents");
        TestSave instance = new TestSave();
        instance.testBuildHookComponents();
    }

    /**
     * Test of testSaveData method, of class TestSave.
     */
    @Test
    public void testTestSaveData() {
        System.out.println("testSaveData");
        TestSave instance = new TestSave();
        instance.testSaveData();
    }
    
    
    
    @Test
    public void testLoadData() throws IOException, ParseException {
        TeachingAssistant jacobEvans = new TeachingAssistant("Jacob Evans", "jacob.evans@stonybrook.edu", true);
        TeachingAssistant jamesHoffman = new TeachingAssistant("James Hoffman", "james.hoffman@stonybrook.edu", true);
        GregorianCalendar g = new GregorianCalendar(2017, 2, 17);
        Date testDate = g.getTime();
        ScheduleItem s1 = new ScheduleItem("Holiday", testDate, "Snow Day", "");
        ScheduleItem s2 = new ScheduleItem("Lecture", testDate, "Lecture 3", "Event Programming");
        Color c1 = Color.BLACK;
        Color c2 = Color.BLUE;
        ProjectTeam sampleProjectTeam = new ProjectTeam("Team 1", c1, c2, "www.team1.com");
        Recitation r = new Recitation("R01", "McKenna", "Monday 5:30 - 6:23 PM", "Old CS 2120", jacobEvans, jamesHoffman);
        fileComponent.loadData(dataComponent, "./work/SiteSaveTest.json");
        
        assertEquals(dataComponent.findTeachingAssistant("Jacob Evans"), jacobEvans);
        assertEquals(dataComponent.findTeachingAssistant("James Hoffman"), jamesHoffman);
        
        assertEquals(dataComponent.getRecitations().get(0), r);
        assertEquals(dataComponent.getProjectTeams().get(0), sampleProjectTeam);
        
        assertEquals(dataComponent.getScheduleItems().get(0), s1);
        assertEquals(dataComponent.getScheduleItems().get(1), s2);
        
        
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

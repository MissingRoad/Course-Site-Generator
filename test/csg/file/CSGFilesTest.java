/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import djf.components.AppDataComponent;
import djf.settings.AppStartupConstants;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dsli
 */
public class CSGFilesTest {

    CourseSiteGeneratorApp app;
    CSGData data;
    CSGFiles instance ;

    public CSGFilesTest() {
        app = new CourseSiteGeneratorApp();
        app.loadProperties("app_properties.xml");
        data = new CSGData(app);
        instance = new CSGFiles(app);
        
        ArrayList<String> gridHeaders = data.getGridHeaders();
        StringProperty testProp = new SimpleStringProperty("");
        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            data.setCellProperty(i, 0, testProp);
            data.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {

            data.setCellProperty(i, 0, testProp);
            data.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = data.getStartHour(); i < data.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            data.setCellProperty(col, row, testProp);
            data.getCellTextProperty(col, row).set(buildCellText(i, "00"));

            data.setCellProperty(col, row + 1, testProp);
            data.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            data.setCellProperty(col, row, testProp);
            data.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            data.setCellProperty(col, row + 1, testProp);
            data.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                data.setCellProperty(col, row, testProp);
                data.setCellProperty(col, row + 1, testProp);
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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class CSGFiles.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");

        String filePath = AppStartupConstants.PATH_WORK + "/SiteSaveTest.json";
        instance.loadData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");

    }

    /**
     * Test of saveData method, of class CSGFiles.
     */
    @Test
    public void testSaveData() throws Exception {
        System.out.println("saveData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFiles instance = null;
        instance.saveData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportData method, of class CSGFiles.
     */
    @Test
    public void testExportData() throws Exception {
        System.out.println("exportData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFiles instance = null;
        instance.exportData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

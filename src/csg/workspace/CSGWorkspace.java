/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import properties_manager.PropertiesManager;
import csg.CSGProp;
/**
 *
 * @author dsli
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CourseSiteGeneratorApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CSGController controller;

    //The window consists of 5 tabs
    TabPane t;
    Tab courseDataTab;
    Tab taDataTab;
    Tab recitationDataTab;
    Tab scheduleDataTab;
    Tab projectDataTab;

    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public CSGWorkspace(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        t = new TabPane();
        courseDataTab = new Tab();
        courseDataTab.setText(CSGProp.COURSE_DETAILS_TAB.toString()); //Problem, doesn't seem to recognize enum
        taDataTab = new Tab();
        taDataTab.setText(CSGProp.TA_DATA_TAB.toString());
        recitationDataTab = new Tab();
        recitationDataTab.setText(CSGProp.RECITATION_DATA_TAB.toString());
        scheduleDataTab = new Tab();
        scheduleDataTab.setText(CSGProp.SCHEDULE_DATA_TAB.toString());
        projectDataTab = new Tab();
        projectDataTab.setText(CSGProp.PROJECT_DATA_TAB.toString());
    }
    
    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
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

    @Override
    public void resetWorkspace() {
        
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        
    }
}

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    
    GridPane topCourseDataBox;
    ComboBox subjectComboBox;
    ComboBox numberComboBox;
    ComboBox semesterComboBox;
    
    Label courseInfoLabel;
    Label subjectLabel;
    Label numberLabel;
    Label semesterLabel;
    Label yearLabel;
    Label titleLabel;
    Label instructorNameLabel;
    Label instructorHomeLabel;
    Label exportDirLabel;
    TextField titleTextField;
    TextField instructorNameTextField;
    TextField instructorHomeTextField;
    Label exportDirTextView;
    Button changeExportDirButton;
    
    GridPane courseDataMiddleBox;
    Label siteTemplateLabel;
    Label siteTemplateDescriptionLabel;
    Label templateDir;
    Label sitePagesLabel;
    TableView sitePages;
    //checkBox declaration here?
    
    GridPane pageStyleDataBox;
    Label pageStyleLabel;
    Label bannerSchoolLabel;
    Label leftFooterLabel;
    Label rightFooterLabel;
    Label stylesheetLabel;
    ImageView bannerSchoolImage;
    ImageView leftFooterImage;
    ImageView rightFooterImage;
    Button changeBannerSchoolImageButton;
    Button changeLeftFooterImageButton;
    Button changeRightFooterImageButton;
    ComboBox stylesheetSelect;
    Label stylesheetNote;
    
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
        //Create the tabs for the TabPane
        courseDataTab = new Tab();
        courseDataTab.setText(CSGProp.COURSE_DETAILS_TAB.toString());

        VBox courseDataTabHBox = new VBox();
        
        //Stuff for the top VBox, Course Information
        topCourseDataBox = new GridPane();
        subjectComboBox = new ComboBox();
        numberComboBox = new ComboBox();
        semesterComboBox = new ComboBox();
        
        courseInfoLabel = new Label();
        subjectLabel = new Label();
        numberLabel = new Label();
        semesterLabel = new Label();
        yearLabel = new Label();
        titleLabel = new Label();
        instructorNameLabel = new Label();
        instructorHomeLabel = new Label();
        exportDirLabel = new Label();
        
        titleTextField = new TextField();
        instructorNameTextField = new TextField();
        instructorHomeTextField = new TextField();
        exportDirTextView = new Label();
        changeExportDirButton = new Button();
        
        //Middle box content, the Site Template
        siteTemplateLabel = new Label();
        
        courseDataTab.setContent(courseDataTabHBox);
        
        taDataTab = new Tab();
        taDataTab.setText(CSGProp.TA_DATA_TAB.toString());
        recitationDataTab = new Tab();
        recitationDataTab.setText(CSGProp.RECITATION_DATA_TAB.toString());
        scheduleDataTab = new Tab();
        scheduleDataTab.setText(CSGProp.SCHEDULE_DATA_TAB.toString());
        projectDataTab = new Tab();
        projectDataTab.setText(CSGProp.PROJECT_DATA_TAB.toString());
        
        //Now add the Tabs
        t.getTabs().addAll(courseDataTab, taDataTab, recitationDataTab, scheduleDataTab, projectDataTab);
        
        workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(t);
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

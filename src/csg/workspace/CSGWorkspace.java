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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    
    //Components for courseDataTab
    VBox courseDataTabVBox;
    GridPane topCourseDataBox;
    ComboBox subjectComboBox;
    ComboBox numberComboBox;
    ComboBox semesterComboBox;
    ComboBox yearComboBox;
    
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
    
    VBox courseDataMiddleBox;
    Label siteTemplateLabel;
    Label siteTemplateDescriptionLabel;
    Label templateDir;
    Button selectTemplateDirButton;
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
    
    //Components for taDataTab
    HBox taDataTabHBox;
    VBox taDataTATableViewVBox;
    Label teachingAssistantsLabel;
    Button deleteTAButton;
    HBox taDataTableViewTopPane;
    TableView taInformation;
    HBox taDataTextFieldPane;
    VBox taDataGridPaneVBox;
    HBox taOfficeHoursTopBox;
    Label officeHoursLabel;
    Label officeHoursStartLabel;
    ComboBox officeHoursStartBox;
    Label officeHoursEndLabel;
    ComboBox officeHoursEndBox;
    GridPane taDataOfficeHoursGridPane; //will need a few methods to construct this
    
    //Components for recitationDataTab
    VBox recitationDataVBox;
    HBox recitationTopBox;
    Label recitationsLabel;
    Button deleteRecitationButton;
    TableView recitationData;
    GridPane addEditGridPane;
    Label sectionLabel;
    TextField sectionTextField;
    Label instructorLabel;
    TextField instructorTextField;
    Label dayTimeLabel;
    TextField dayTimeTextField;
    Label locationLabel;
    TextField locationTextField;
    Label supervisingTa1Label;
    ComboBox supervisingTa1Box;
    Label supervisingTa2Label;
    ComboBox supervisingTa2Box;
    Button addUpdateButton;
    Button clearButton;
    
    //Components for scheduleDataTab
    VBox scheduleDataVBox;
    Label scheduleLabel;
    GridPane startEndGridPane;
    Label calendarBoundariesLabel;
    Label startingMondayLabel;
    DatePicker startingMondayPicker;
    Label endingFridayLabel;
    DatePicker endingFridayPicker;
    VBox scheduleItemsVBox;
    HBox topScheduleItemsBox;
    Label scheduleItemsLabel;
    Button deleteScheduleItemButton;
    TableView scheduleItems;
    GridPane addEditSchedulePane;
    Label typeLabel;
    ComboBox typeBox;
    Label dateLabel;
    DatePicker datePicker;
    Label timeLabel;
    TextField timeTextField;
    Label scheduleItemTitleLabel;
    TextField scheduleItemTimeTextField;
    Label linkLabel;
    TextField linkTextField;
    Label criteriaLabel;
    TextField criteriaTextField;
    Button addUpdateScheduleItemButton;
    Button clearScheduleItemButton;
    
    //Components for projectDataTab
    Label projectsLabel;
    VBox projectDataVBox;
    VBox projectTeamsVBox;
    HBox projectTeamsTopHBox;
    Label teamsLabel;
    Button deleteProjectButton;
    TableView projectTeams;
    GridPane addEditProjectGridPane;
    Label addEditProjectLabel;
    Label teamNameLabel;
    TextField teamNameTextField;
    Label teamColorLabel;
    ColorPicker teamColorPicker;
    Label teamTextColorLabel;
    ColorPicker teamTextColorPicker;
    Label teamLinkLabel;
    TextField teamLinkTextField;
    Button addEditTeamButton;
    Button clearTeamButton;
    VBox projectTeamStudentsVBox;
    HBox projectTeamStudentsTopBox;
    Label studentsLabel;
    Button deleteStudentsButton;
    TableView teamMembers;
    Label addEditStudentsLabel;
    GridPane addEditStudentsPane;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label teamLabel;
    ComboBox teamBox;
    Label roleLabel;
    Button addUpdateStudentsButton;
    Button clearStudentsButton;
    
    /**
     * The constructor initializes the user interface, except for
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
        taDataTab = new Tab();
        taDataTab.setText(CSGProp.TA_DATA_TAB.toString());
        recitationDataTab = new Tab();
        recitationDataTab.setText(CSGProp.RECITATION_DATA_TAB.toString());
        scheduleDataTab = new Tab();
        scheduleDataTab.setText(CSGProp.SCHEDULE_DATA_TAB.toString());
        projectDataTab = new Tab();
        projectDataTab.setText(CSGProp.PROJECT_DATA_TAB.toString());
        courseDataTabVBox = new VBox();
        
        //Stuff for the top VBox, Course Information
        topCourseDataBox = new GridPane();
        subjectComboBox = new ComboBox();
        numberComboBox = new ComboBox();
        semesterComboBox = new ComboBox();
        yearComboBox = new ComboBox();
        
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
        
        //Add components to topCourseDataBox
        topCourseDataBox.add(courseInfoLabel, 0, 0);
        topCourseDataBox.add(subjectLabel, 0, 1);
        topCourseDataBox.add(subjectComboBox, 1, 1);
        topCourseDataBox.add(numberLabel, 2, 1);
        topCourseDataBox.add(numberComboBox, 3, 1);
        topCourseDataBox.add(semesterLabel, 0, 2);
        topCourseDataBox.add(semesterComboBox, 1, 2);
        topCourseDataBox.add(yearLabel, 2, 2);
        topCourseDataBox.add(yearComboBox, 3, 2);
        topCourseDataBox.add(titleLabel, 0, 3);
        topCourseDataBox.add(titleTextField, 1, 3);
        topCourseDataBox.add(instructorNameLabel, 0, 4);
        topCourseDataBox.add(instructorNameTextField, 1, 4);
        topCourseDataBox.add(instructorHomeLabel, 0, 5);
        topCourseDataBox.add(instructorHomeTextField, 1, 5);
        topCourseDataBox.add(exportDirLabel, 0, 6);
        topCourseDataBox.add(exportDirTextView, 1, 6);
        topCourseDataBox.add(changeExportDirButton, 2, 6);
        
        
        //Middle box content, the Site Template
        courseDataMiddleBox = new VBox();
        siteTemplateLabel = new Label();
        siteTemplateDescriptionLabel = new Label();
        templateDir = new Label();
        selectTemplateDirButton = new Button();
        sitePagesLabel = new Label();
        sitePages = new TableView();
        //checkBox declaration here?
        
        //add everything to the courseDataMiddleBox
        courseDataMiddleBox.getChildren().addAll(siteTemplateLabel, siteTemplateDescriptionLabel, templateDir, selectTemplateDirButton, sitePagesLabel, sitePages);
        
        //pageStyleDataBox, the Page Style component of the Course Details Pane
        pageStyleDataBox = new GridPane();
        pageStyleLabel = new Label();
        bannerSchoolLabel = new Label();
        leftFooterLabel = new Label();
        rightFooterLabel = new Label();
        stylesheetLabel = new Label();
        bannerSchoolImage = new ImageView();
        leftFooterImage = new ImageView();
        rightFooterImage = new ImageView();
        changeBannerSchoolImageButton = new Button();
        changeLeftFooterImageButton = new Button();
        changeRightFooterImageButton = new Button();
        stylesheetSelect = new ComboBox();
        stylesheetNote = new Label();
        
        pageStyleDataBox.add(pageStyleLabel, 0, 0);
        pageStyleDataBox.add(bannerSchoolLabel, 0, 1);
        pageStyleDataBox.add(bannerSchoolImage, 1, 1);
        pageStyleDataBox.add(changeBannerSchoolImageButton, 2, 1);
        pageStyleDataBox.add(leftFooterLabel, 0, 2);
        pageStyleDataBox.add(leftFooterImage, 1, 2);
        pageStyleDataBox.add(changeLeftFooterImageButton, 2, 2);
        pageStyleDataBox.add(rightFooterLabel, 0, 3);
        pageStyleDataBox.add(rightFooterImage, 1, 3);
        pageStyleDataBox.add(changeRightFooterImageButton, 2, 3);
        pageStyleDataBox.add(stylesheetLabel, 0, 4);
        pageStyleDataBox.add(stylesheetSelect, 1, 4);
        pageStyleDataBox.add(stylesheetNote, 0, 5);
        
        //add everything to the VBox
        courseDataTabVBox.getChildren().add(topCourseDataBox);
        courseDataTabVBox.getChildren().add(courseDataMiddleBox);
        courseDataTabVBox.getChildren().add(pageStyleDataBox);
        courseDataTab.setContent(courseDataTabVBox);
        
        /*taDataTab = new Tab();
        taDataTab.setText(CSGProp.TA_DATA_TAB.toString());*/
        
        /*recitationDataTab = new Tab();
        recitationDataTab.setText(CSGProp.RECITATION_DATA_TAB.toString());*/
        
        /*scheduleDataTab = new Tab();
        scheduleDataTab.setText(CSGProp.SCHEDULE_DATA_TAB.toString());*/
        
        /*projectDataTab = new Tab();
        projectDataTab.setText(CSGProp.PROJECT_DATA_TAB.toString());*/
        
        
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

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
//import csg.data.CSGData;
//import csg.data.TAData;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TeachingAssistant;
import csg.style.CSGStyle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
//import tam.data.TAData;

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
    // For the site pages table, maybe use generics later on for your datatypes here
    TableView sitePages;
    TableColumn useColumn;
    TableColumn navbarTitleColumn;
    TableColumn fileNameColumn;
    TableColumn scriptColumn;
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
    boolean addTA;
    HBox taDataTabHBox;
    VBox taDataTATableViewVBox;
    Label teachingAssistantsLabel;
    Button deleteTAButton;
    HBox taDataTableViewTopPane;
    TableView taInformation;
    TableColumn<TeachingAssistant, CheckBox> taIsUndergradColumn;
    TableColumn<TeachingAssistant, String> taNameColumn;
    TableColumn<TeachingAssistant, String> taEmailColumn;
    HBox taDataTextFieldPane;
    TextField taNameTextField;
    TextField taEmailTextField;
    Button addTAButton;
    Button clearTAButton;
    ScrollPane taDataGridScrollPane;
    VBox taDataGridPaneVBox;
    HBox taOfficeHoursTopBox;
    Label officeHoursLabel;
    Label officeHoursStartLabel;
    ComboBox officeHoursStartBox;
    Label officeHoursEndLabel;
    ComboBox officeHoursEndBox;
    ObservableList<String> time_options;
    GridPane taDataOfficeHoursGridPane; //will need a few methods to construct this
    //Components for the above GridPane
    HashMap<String, Pane> taDataOfficeHoursGridTimeHeaderPanes;
    HashMap<String, Label> taDataOfficeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> taDataOfficeHoursGridDayHeaderPanes;
    HashMap<String, Label> taDataOfficeHoursGridDayHeaderLabels;
    HashMap<String, Pane> taDataOfficeHoursGridTimeCellPanes;
    HashMap<String, Label> taDataOfficeHoursGridTimeCellLabels;
    HashMap<String, Pane> taDataOfficeHoursGridTACellPanes;
    HashMap<String, Label> taDataOfficeHoursGridTACellLabels;

    //Components for recitationDataTab
    VBox recitationDataVBox;
    HBox recitationTopBox;
    Label recitationsLabel;
    Button deleteRecitationButton;
    TableView recitationData;
    TableColumn recSection;
    TableColumn recInstructor;
    TableColumn recDayTime;
    TableColumn recLocation;
    TableColumn recTA1;
    TableColumn recTA2;
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
    TableColumn scheduleItemType;
    TableColumn scheduleItemDate;
    TableColumn scheduleItemTitle;
    TableColumn scheduleItemTopic;
    GridPane addEditSchedulePane;
    Label typeLabel;
    ComboBox typeBox;
    Label dateLabel;
    DatePicker datePicker;
    Label timeLabel;
    TextField timeTextField;
    Label scheduleItemTitleLabel;
    TextField scheduleItemTitleTextField;
    Label topicLabel;
    TextField topicTextField;
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
    TableColumn projectTeamName;
    TableColumn projectTeamColor;
    TableColumn projectTeamTextColor;
    TableColumn projectTeamLink;
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
    TableColumn teamMemberFirstName;
    TableColumn teamMemberLastName;
    TableColumn teamMemberTeam;
    TableColumn teamMemberRole;
    Label addEditStudentsLabel;
    GridPane addEditStudentsPane;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label teamLabel;
    ComboBox teamBox;
    Label roleLabel;
    TextField roleTextField;
    Button addUpdateStudentsButton;
    Button clearStudentsButton;

    /**
     * The constructor initializes the user interface, except for the full
     * office hours grid, since it doesn't yet know what the hours will be until
     * a file is loaded or a new one is created.
     */
    public CSGWorkspace(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        t = new TabPane();
        //Create the tabs for the TabPane
        courseDataTab = new Tab();
        //courseDataTab.textProperty().set(CSGProp.COURSE_DETAILS_TAB.toString());
        courseDataTab.setText(props.getProperty(CSGProp.COURSE_DETAILS_TAB.toString()));
        taDataTab = new Tab();
        taDataTab.setText(props.getProperty(CSGProp.TA_DATA_TAB.toString()));
        recitationDataTab = new Tab();
        recitationDataTab.setText(props.getProperty(CSGProp.RECITATION_DATA_TAB.toString()));
        scheduleDataTab = new Tab();
        scheduleDataTab.setText(props.getProperty(CSGProp.SCHEDULE_DATA_TAB.toString()));
        projectDataTab = new Tab();
        projectDataTab.setText(props.getProperty(CSGProp.PROJECT_DATA_TAB.toString()));

        //Make sure we can't close the Tabs
        courseDataTab.setClosable(false);
        taDataTab.setClosable(false);
        recitationDataTab.setClosable(false);
        scheduleDataTab.setClosable(false);
        projectDataTab.setClosable(false);
        courseDataTabVBox = new VBox();

        //Stuff for the top VBox, Course Information
        topCourseDataBox = new GridPane();
        subjectComboBox = new ComboBox();
        numberComboBox = new ComboBox();
        semesterComboBox = new ComboBox();
        yearComboBox = new ComboBox();

        courseInfoLabel = new Label(props.getProperty(CSGProp.COURSE_INFO_LABEL).toString());
        subjectLabel = new Label(props.getProperty(CSGProp.COURSE_SUBJECT_LABEL).toString());
        numberLabel = new Label(props.getProperty(CSGProp.COURSE_NUMBER_LABEL).toString());
        semesterLabel = new Label(props.getProperty(CSGProp.COURSE_SEMESTER_LABEL).toString());
        yearLabel = new Label(props.getProperty(CSGProp.COURSE_YEAR_LABEL.toString()));
        titleLabel = new Label(props.getProperty(CSGProp.COURSE_TITLE_LABEL).toString());
        instructorNameLabel = new Label(props.getProperty(CSGProp.COURSE_INSTRUCTOR_NAME_LABEL).toString());
        instructorHomeLabel = new Label(props.getProperty(CSGProp.COURSE_INSTRUCTOR_HOME_LABEL).toString());
        exportDirLabel = new Label(props.getProperty(CSGProp.COURSE_EXPORT_DIR_LABEL).toString());

        titleTextField = new TextField();
        instructorNameTextField = new TextField();
        instructorNameTextField.setPromptText(props.getProperty(CSGProp.NAME_PROMPT_TEXT.toString()));
        instructorHomeTextField = new TextField();
        instructorHomeTextField.setPromptText(props.getProperty(CSGProp.HOME_PROMPT_TEXT.toString()));
        exportDirTextView = new Label();
        changeExportDirButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_LABEL).toString());

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
        
        topCourseDataBox.setStyle("-fx-background-color: bisque;-fx-border: 5px; -fx-border-color: black;");

        //Middle box content, the Site Template
        courseDataMiddleBox = new VBox();
        siteTemplateLabel = new Label(props.getProperty(CSGProp.COURSE_SITE_TEMPLATE_LABEL));
        siteTemplateDescriptionLabel = new Label(props.getProperty(CSGProp.COURSE_SITE_TEMPLATE_NOTE_LABEL).toString());
        templateDir = new Label();
        selectTemplateDirButton = new Button(props.getProperty(CSGProp.COURSE_SELECT_TEMPLATE_DIRECTORY_BUTTON_LABEL.toString()));
        sitePagesLabel = new Label(props.getProperty(CSGProp.COURSE_SITE_PAGES_LABEL).toString());
        sitePages = new TableView();
        //useColumn = new TableColumn();
        //navbarTitleColumn = new TableColumn();
        //fileNameColumn = new TableColumn();
        //scriptColumn = new TableColumn();
        sitePages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String useLabelText = props.getProperty(CSGProp.COURSE_SITE_PAGES_USE_LABEL.toString());
        String navbarTitleLabelText = props.getProperty(CSGProp.COURSE_SITE_PAGES_NAVBAR_TITLE_LABEL.toString());
        String filenameLabelText = props.getProperty(CSGProp.COURSE_SITE_PAGES_FILE_NAME_LABEL.toString());
        String scriptLabelText = props.getProperty(CSGProp.COURSE_SITE_PAGES_SCRIPT_LABEL.toString());
        useColumn = new TableColumn(useLabelText);

        navbarTitleColumn = new TableColumn(navbarTitleLabelText);
        fileNameColumn = new TableColumn(filenameLabelText);
        scriptColumn = new TableColumn(scriptLabelText);
        //insert setCellValueFactory methods here when you develop the data component
        sitePages.getColumns().add(useColumn);
        sitePages.getColumns().addAll(navbarTitleColumn, fileNameColumn, scriptColumn);

        //add everything to the courseDataMiddleBox
        courseDataMiddleBox.getChildren().addAll(siteTemplateLabel, siteTemplateDescriptionLabel, templateDir, selectTemplateDirButton, sitePagesLabel, sitePages);
        courseDataMiddleBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        //pageStyleDataBox, the Page Style component of the Course Details Pane
        pageStyleDataBox = new GridPane();
        pageStyleLabel = new Label(props.getProperty(CSGProp.COURSE_PAGE_STYLE_LABEL).toString());
        bannerSchoolLabel = new Label(props.getProperty(CSGProp.COURSE_BANNER_SCHOOL_IMAGE_LABEL).toString());
        leftFooterLabel = new Label(props.getProperty(CSGProp.COURSE_LEFT_FOOTER_LABEL).toString());
        rightFooterLabel = new Label(props.getProperty(CSGProp.COURSE_RIGHT_FOOTER_LABEL).toString());
        stylesheetLabel = new Label(props.getProperty(CSGProp.COURSE_STYLESHEET_LABEL).toString());
        bannerSchoolImage = new ImageView();
        leftFooterImage = new ImageView();
        rightFooterImage = new ImageView();
        changeBannerSchoolImageButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_LABEL).toString());
        changeLeftFooterImageButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_LABEL).toString());
        changeRightFooterImageButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_LABEL).toString());
        stylesheetSelect = new ComboBox();
        stylesheetNote = new Label(props.getProperty(CSGProp.COURSE_STYLESHEET_NOTE_LABEL).toString());

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
        
        pageStyleDataBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        //add everything to the VBox
        courseDataTabVBox.getChildren().add(topCourseDataBox);
        courseDataTabVBox.getChildren().add(courseDataMiddleBox);
        courseDataTabVBox.getChildren().add(pageStyleDataBox);
        courseDataTabVBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");
        courseDataTab.setContent(courseDataTabVBox);

        /*taDataTab = new Tab();
        taDataTab.setText(CSGProp.TA_DATA_TAB.toString());*/
        //First, declare the whole pane
        addTA = true;
        taDataTabHBox = new HBox();
        //Assembling the left VBox
        taDataTATableViewVBox = new VBox();
        taDataTableViewTopPane = new HBox();
        teachingAssistantsLabel = new Label(props.getProperty(CSGProp.TEACHING_ASSISTANTS_LABEL).toString());
        deleteTAButton = new Button(props.getProperty(CSGProp.DELETE_SYMBOL).toString());
        taDataTableViewTopPane.getChildren().addAll(teachingAssistantsLabel, deleteTAButton);
        
        taInformation = new TableView();
        taInformation.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<TeachingAssistant> taTableData = data.getTeachingAssistants();
        taInformation.setItems(taTableData);
        // Is undergrad column initialization here
        String isUndergradColumnText = props.getProperty(CSGProp.IS_UNDERGRAD_COLUMN_TEXT.toString());
        
        String nameColumnText = props.getProperty(CSGProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGProp.EMAIL_COLUMN_TEXT.toString());
        taIsUndergradColumn = new TableColumn(isUndergradColumnText);
        taNameColumn = new TableColumn(nameColumnText);
        taEmailColumn = new TableColumn(emailColumnText);
        taIsUndergradColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, CheckBox>("isUndergrad")
        );
        taIsUndergradColumn.setCellFactory(column -> new CheckBoxTableCell());
        taInformation.setEditable(true);
        taNameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        taEmailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taInformation.getColumns().add(taIsUndergradColumn);
        taInformation.getColumns().add(taNameColumn);
        taInformation.getColumns().add(taEmailColumn);
        taDataTextFieldPane = new HBox();
        taNameTextField = new TextField();
        taEmailTextField = new TextField();
        addTAButton = new Button(props.getProperty(CSGProp.ADD_BUTTON_LABEL).toString());
        clearTAButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_LABEL).toString());
        taDataTextFieldPane.getChildren().addAll(taNameTextField, taEmailTextField, addTAButton, clearTAButton);
        taDataTATableViewVBox.getChildren().addAll(taDataTableViewTopPane, taInformation, taDataTextFieldPane);
        
        taDataTATableViewVBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        //Assembling the right VBox
        time_options = FXCollections.observableArrayList(
        props.getProperty(CSGProp.TIME_12AM.toString()),
        props.getProperty(CSGProp.TIME_1AM.toString()),
        props.getProperty(CSGProp.TIME_2AM.toString()),
        props.getProperty(CSGProp.TIME_3AM.toString()),
        props.getProperty(CSGProp.TIME_4AM.toString()),
        props.getProperty(CSGProp.TIME_5AM.toString()),
        props.getProperty(CSGProp.TIME_6AM.toString()),
        props.getProperty(CSGProp.TIME_7AM.toString()),
        props.getProperty(CSGProp.TIME_8AM.toString()),
        props.getProperty(CSGProp.TIME_9AM.toString()),
        props.getProperty(CSGProp.TIME_10AM.toString()),
        props.getProperty(CSGProp.TIME_11AM.toString()),
        props.getProperty(CSGProp.TIME_12PM.toString()),
        props.getProperty(CSGProp.TIME_1PM.toString()),
        props.getProperty(CSGProp.TIME_2PM.toString()),
        props.getProperty(CSGProp.TIME_3PM.toString()),
        props.getProperty(CSGProp.TIME_4PM.toString()),
        props.getProperty(CSGProp.TIME_5PM.toString()),
        props.getProperty(CSGProp.TIME_6PM.toString()),
        props.getProperty(CSGProp.TIME_7PM.toString()),
        props.getProperty(CSGProp.TIME_8PM.toString()),
        props.getProperty(CSGProp.TIME_9PM.toString()),
        props.getProperty(CSGProp.TIME_10PM.toString()),
        props.getProperty(CSGProp.TIME_11PM.toString())
        );
        taDataGridScrollPane = new ScrollPane();
        taDataGridPaneVBox = new VBox();
        taOfficeHoursTopBox = new HBox();
        officeHoursLabel = new Label(props.getProperty(CSGProp.OFFICE_HOURS_LABEL.toString()));
        officeHoursStartLabel = new Label(props.getProperty(CSGProp.OFFICE_HOURS_START_LABEL.toString()));
        officeHoursStartBox = new ComboBox(time_options);
        officeHoursEndLabel = new Label(props.getProperty(CSGProp.OFFICE_HOURS_END_LABEL.toString()));
        officeHoursEndBox = new ComboBox(time_options);
        
        taOfficeHoursTopBox.getChildren().addAll(officeHoursLabel, officeHoursStartLabel, officeHoursStartBox, officeHoursEndLabel, officeHoursEndBox);
        taDataOfficeHoursGridPane = new GridPane();
        //Code here for actually assembling the GridPane
        taDataOfficeHoursGridTimeHeaderPanes = new HashMap<String, Pane>();
        taDataOfficeHoursGridTimeHeaderLabels = new HashMap<String, Label>();
        taDataOfficeHoursGridDayHeaderPanes = new HashMap<String, Pane>();
        taDataOfficeHoursGridDayHeaderLabels = new HashMap<String, Label>();
        taDataOfficeHoursGridTimeCellPanes = new HashMap<String, Pane>();
        taDataOfficeHoursGridTimeCellLabels = new HashMap<String, Label>();
        taDataOfficeHoursGridTACellPanes = new HashMap<String, Pane>();
        taDataOfficeHoursGridTACellLabels = new HashMap<String, Label>();
        taDataGridPaneVBox.getChildren().addAll(taOfficeHoursTopBox, taDataOfficeHoursGridPane);
        taDataGridScrollPane.setContent(taDataGridPaneVBox);
        
        // NOW LET'S SETUP THE EVENT HANDLING - for the TA DATA TAB ONLY
        controller = new CSGController(app);
        
        //ComboBox Events
        officeHoursStartBox.setPrefHeight(42);
        officeHoursStartBox.setPrefWidth(150);
        officeHoursStartBox.getSelectionModel().select(data.getStartHour());
        officeHoursStartBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(officeHoursStartBox.getSelectionModel().getSelectedIndex() != data.getStartHour())
                        controller.changeTime();
            }
        });
        
        officeHoursEndBox.setPrefHeight(42);
        officeHoursEndBox.setPrefWidth(150);
        officeHoursEndBox.getSelectionModel().select(data.getEndHour());
        officeHoursEndBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(officeHoursEndBox.getSelectionModel().getSelectedIndex() != data.getEndHour())
                        controller.changeTime();
            }    
        });

        // CONTROLS FOR ADDING TAs
        taNameTextField.setOnAction(e -> {
            if(!addTA)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        taEmailTextField.setOnAction(e -> {
            if(!addTA)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        addTAButton.setOnAction(e -> {
            if(!addTA)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        clearTAButton.setOnAction(e -> {
            addTAButton.setText(props.getProperty(CSGProp.ADD_BUTTON_LABEL.toString()));
            addTA = true;
            taNameTextField.clear();
            taEmailTextField.clear();
            taInformation.getSelectionModel().select(null);
        });

        taInformation.setFocusTraversable(true);
        taInformation.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        taInformation.setOnMouseClicked(e -> {
            addTAButton.setText(props.getProperty(CSGProp.ADD_EDIT_LABEL.toString()));
            addTA = false;
            controller.loadTAtotext();
        });
        
        // Workspace handler goes at the end, handler organized by tab

        //Assembling the whole taDataTab
        taDataTabHBox.getChildren().addAll(taDataTATableViewVBox, taDataGridScrollPane);
        taDataTab.setContent(taDataTabHBox);

        /*recitationDataTab = new Tab();
        recitationDataTab.setText(CSGProp.RECITATION_DATA_TAB.toString());*/
        recitationDataVBox = new VBox();
        recitationTopBox = new HBox();
        recitationsLabel = new Label(props.getProperty(CSGProp.RECITATIONS_LABEL.toString()));
        deleteRecitationButton = new Button(props.getProperty(CSGProp.DELETE_SYMBOL.toString()));
        recitationTopBox.getChildren().addAll(recitationsLabel, deleteRecitationButton);
        recitationData = new TableView();
        ObservableList<Recitation> recitationTableData = data.getRecitations();
        recitationData.setItems(recitationTableData);
        recSection = new TableColumn(props.getProperty(CSGProp.REC_SECTION_LABEL.toString()));
        recInstructor = new TableColumn(props.getProperty(CSGProp.REC_INSTRUCTOR_LABEL.toString()));
        recDayTime = new TableColumn(props.getProperty(CSGProp.REC_DAY_TIME_LABEL.toString()));
        recLocation = new TableColumn(props.getProperty(CSGProp.REC_LOCATION_LABEL.toString()));
        recTA1 = new TableColumn(props.getProperty(CSGProp.REC_TA2_LABEL.toString()));
        recTA2 = new TableColumn(props.getProperty(CSGProp.REC_TA2_LABEL.toString()));
        recSection.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("section")
        );
        recInstructor.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("instructor")
        );
        recDayTime.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("dayTime")
        );
        recLocation.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("location")
        );
        // How to get their names in to the TableView?
        recTA1.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("supervisingTA1")
        );
        recTA2.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("supervisingTA2")
        );
        
        recitationData.getColumns().addAll(recSection, recInstructor, recDayTime, recLocation, recTA1, recTA2);
        
        addEditGridPane = new GridPane();
        sectionLabel = new Label(props.getProperty(CSGProp.REC_SECTION_LABEL.toString()));
        sectionTextField = new TextField();
        instructorLabel = new Label(props.getProperty(CSGProp.REC_INSTRUCTOR_LABEL.toString()));
        instructorTextField = new TextField();
        dayTimeLabel = new Label(props.getProperty(CSGProp.REC_DAY_TIME_LABEL.toString()));
        dayTimeTextField = new TextField();
        locationLabel = new Label(props.getProperty(CSGProp.REC_LOCATION_LABEL.toString()));
        locationTextField = new TextField();
        supervisingTa1Label = new Label(props.getProperty(CSGProp.REC_TA1_LABEL.toString()));
        supervisingTa1Box = new ComboBox();
        supervisingTa2Label = new Label(props.getProperty(CSGProp.REC_TA1_LABEL.toString()));
        supervisingTa2Box = new ComboBox();
        addUpdateButton = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_LABEL.toString()));
        clearButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_LABEL.toString()));
        addEditGridPane.add(sectionLabel, 0, 0);
        addEditGridPane.add(sectionTextField, 1, 0);
        addEditGridPane.add(instructorLabel, 0, 1);
        addEditGridPane.add(instructorTextField, 1, 1);
        addEditGridPane.add(dayTimeLabel, 0, 2);
        addEditGridPane.add(dayTimeTextField, 1, 2);
        addEditGridPane.add(locationLabel, 0, 3);
        addEditGridPane.add(locationTextField, 1, 3);
        addEditGridPane.add(supervisingTa1Label, 0, 4);
        addEditGridPane.add(supervisingTa1Box, 1, 4);
        addEditGridPane.add(supervisingTa2Label, 0, 5);
        addEditGridPane.add(supervisingTa2Box, 1, 5);
        addEditGridPane.add(addUpdateButton, 0, 6);
        addEditGridPane.add(clearButton, 1, 6);

        recitationDataVBox.getChildren().addAll(recitationTopBox, recitationData, addEditGridPane);
        recitationDataVBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        recitationDataTab.setContent(recitationDataVBox);
        /*scheduleDataTab = new Tab();
        scheduleDataTab.setText(CSGProp.SCHEDULE_DATA_TAB.toString());*/

        //Assembling the VBox to be displayed in the Tab
        scheduleDataVBox = new VBox();
        scheduleLabel = new Label();
        startEndGridPane = new GridPane();
        calendarBoundariesLabel = new Label(props.getProperty(CSGProp.CALENDAR_BOUNDARIES_LABEL.toString()));
        startingMondayLabel = new Label(props.getProperty(CSGProp.STARTING_MONDAY_LABEL.toString()));
        startingMondayPicker = new DatePicker();
        endingFridayLabel = new Label(props.getProperty(CSGProp.ENDING_FRIDAY_LABEL.toString()));
        endingFridayPicker = new DatePicker();
        //Constructing the top box ("Calendar Boundaries")
        startEndGridPane.add(calendarBoundariesLabel, 0, 0);
        startEndGridPane.add(startingMondayLabel, 0, 1);
        startEndGridPane.add(startingMondayPicker, 1, 1);
        startEndGridPane.add(endingFridayLabel, 2, 1);
        startEndGridPane.add(endingFridayPicker, 3, 1);
        //The bottom box ("Schedule Items")
        scheduleItemsVBox = new VBox();
        topScheduleItemsBox = new HBox();
        scheduleItemsLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEMS_LABEL.toString()));
        deleteScheduleItemButton = new Button();
        topScheduleItemsBox.getChildren().addAll(scheduleItemsLabel, deleteScheduleItemButton);
        scheduleItems = new TableView();
        scheduleItemType = new TableColumn(props.getProperty(CSGProp.SCHEDULE_ITEM_TYPE_LABEL.toString()));
        scheduleItemDate = new TableColumn(props.getProperty(CSGProp.SCHEDULE_ITEM_DATE_LABEL.toString()));
        scheduleItemTitle = new TableColumn(props.getProperty(CSGProp.SCHEDULE_ITEM_TITLE_LABEL.toString()));
        scheduleItemTopic = new TableColumn(props.getProperty(CSGProp.SCHEDULE_ITEM_TOPIC_LABEL.toString()));
        ObservableList<ScheduleItem> scheduleItemsData = data.getScheduleItems();
        scheduleItems.setItems(scheduleItemsData);
        scheduleItemType.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("type")
        );
        scheduleItemDate.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("date")
        );
        scheduleItemTitle.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("title")
        );
        scheduleItemTopic.setCellValueFactory(
                new PropertyValueFactory<ScheduleItem, String>("topic")
        );
        scheduleItems.getColumns().addAll(scheduleItemType, scheduleItemDate, scheduleItemTitle, scheduleItemTopic);
        addEditSchedulePane = new GridPane();
        typeLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_TYPE_LABEL.toString()));
        typeBox = new ComboBox();
        dateLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_DATE_LABEL.toString()));
        datePicker = new DatePicker();
        timeLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_TIME_LABEL.toString()));
        timeTextField = new TextField();
        scheduleItemTitleLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_TITLE_LABEL.toString()));
        scheduleItemTitleTextField = new TextField();
        topicLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_TOPIC_LABEL.toString()));
        topicTextField = new TextField();
        linkLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_LINK_LABEL.toString()));
        linkTextField = new TextField();
        criteriaLabel = new Label(props.getProperty(CSGProp.SCHEDULE_ITEM_CRITERIA_LABEL.toString()));
        criteriaTextField = new TextField();
        addUpdateScheduleItemButton = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_LABEL.toString()));
        clearScheduleItemButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_LABEL.toString()));
        //add the components to addEditSchedulePane
        addEditSchedulePane.add(typeLabel, 0, 1);
        addEditSchedulePane.add(typeBox, 1, 1);
        addEditSchedulePane.add(dateLabel, 0, 2);
        addEditSchedulePane.add(datePicker, 1, 2);
        addEditSchedulePane.add(timeLabel, 0, 3);
        addEditSchedulePane.add(timeTextField, 1, 3);
        addEditSchedulePane.add(scheduleItemTitleLabel, 0, 4);
        addEditSchedulePane.add(scheduleItemTitleTextField, 1, 4);
        addEditSchedulePane.add(topicLabel, 0, 5); //
        addEditSchedulePane.add(topicTextField, 1, 5);
        addEditSchedulePane.add(linkLabel, 0, 6);
        addEditSchedulePane.add(linkTextField, 1, 6);
        addEditSchedulePane.add(criteriaLabel, 0, 7);
        addEditSchedulePane.add(criteriaTextField, 1, 7);
        addEditSchedulePane.add(addUpdateScheduleItemButton, 0, 8);
        addEditSchedulePane.add(clearScheduleItemButton, 1, 8);
        //Assemble the bottom box...
        scheduleItemsVBox.getChildren().addAll(topScheduleItemsBox, scheduleItems, addEditSchedulePane);
        scheduleItemsVBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        //Now assemble the entire scheduleDataVBox
        scheduleDataVBox.getChildren().addAll(startEndGridPane, scheduleItemsVBox);

        scheduleDataTab.setContent(scheduleDataVBox);

        /*projectDataTab = new Tab();
        projectDataTab.setText(CSGProp.PROJECT_DATA_TAB.toString());*/
        projectsLabel = new Label();
        projectDataVBox = new VBox();
        projectTeamsVBox = new VBox();
        projectTeamsTopHBox = new HBox();
        teamsLabel = new Label(props.getProperty(CSGProp.TEAMS_LABEL.toString()));
        deleteProjectButton = new Button(props.getProperty(CSGProp.DELETE_SYMBOL.toString()));
        projectTeamsTopHBox.getChildren().addAll(teamsLabel, deleteProjectButton);
        projectTeams = new TableView();
        ObservableList<ProjectTeam> projectTeamData = data.getProjectTeams();
        projectTeams.setItems(projectTeamData);
        projectTeamName = new TableColumn(props.getProperty(CSGProp.TEAM_NAME_LABEL.toString()));
        projectTeamColor = new TableColumn(props.getProperty(CSGProp.TEAM_COLOR_LABEL.toString()));
        projectTeamTextColor = new TableColumn(props.getProperty(CSGProp.TEXT_COLOR_LABEL.toString()));
        projectTeamLink = new TableColumn(props.getProperty(CSGProp.TEAM_LINK_LABEL.toString()));
        projectTeamName.setCellValueFactory(
                new PropertyValueFactory<ProjectTeam, String>("name")
        );
        projectTeamColor.setCellValueFactory(
                new PropertyValueFactory<ProjectTeam, String>("color")
        );
        projectTeamTextColor.setCellValueFactory(
                new PropertyValueFactory<ProjectTeam, String>("textColor")
        );
        projectTeamLink.setCellValueFactory(
                new PropertyValueFactory<ProjectTeam, String>("link")
        );
        projectTeams.getColumns().addAll(projectTeamName, projectTeamColor, projectTeamTextColor, projectTeamLink);
        addEditProjectGridPane = new GridPane();
        addEditProjectLabel = new Label(props.getProperty(CSGProp.ADD_EDIT_LABEL.toString()));
        teamNameLabel = new Label(props.getProperty(CSGProp.TEAM_NAME_LABEL.toString()));
        teamNameTextField = new TextField();
        teamColorLabel = new Label(props.getProperty(CSGProp.TEAM_COLOR_LABEL.toString()));
        teamColorPicker = new ColorPicker();
        teamColorPicker.setPrefHeight(85);
        teamTextColorLabel = new Label(props.getProperty(CSGProp.TEXT_COLOR_LABEL.toString()));
        teamTextColorPicker = new ColorPicker();
        teamTextColorPicker.setPrefHeight(85);
        teamLinkLabel = new Label(props.getProperty(CSGProp.TEAM_LINK_LABEL.toString()));
        teamLinkTextField = new TextField();
        addEditTeamButton = new Button(props.getProperty(CSGProp.ADD_EDIT_LABEL.toString()));
        clearTeamButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_LABEL.toString()));
        addEditProjectGridPane.add(addEditProjectLabel, 0, 0);
        addEditProjectGridPane.add(teamNameLabel, 0, 1);
        addEditProjectGridPane.add(teamNameTextField, 1, 1);
        addEditProjectGridPane.add(teamColorLabel, 0, 2);
        addEditProjectGridPane.add(teamColorPicker, 1, 2);
        addEditProjectGridPane.add(teamTextColorLabel, 2, 2);
        addEditProjectGridPane.add(teamTextColorPicker, 3, 2);
        addEditProjectGridPane.add(teamLinkLabel, 2, 1);
        addEditProjectGridPane.add(teamLinkTextField, 3, 1);
        addEditProjectGridPane.add(addEditTeamButton, 4, 1);
        addEditProjectGridPane.add(clearTeamButton, 5, 1);
        projectTeamsVBox.getChildren().addAll(projectTeamsTopHBox, projectTeams, addEditProjectGridPane);
        projectTeamsVBox.setStyle("-fx-background-color: bisque;-fx-border: 5px;-fx-border-color: black;");

        projectTeamStudentsVBox = new VBox();
        projectTeamStudentsTopBox = new HBox();
        studentsLabel = new Label(props.getProperty(CSGProp.TEAM_STUDENTS_LABEL.toString()));
        deleteStudentsButton = new Button(props.getProperty(CSGProp.DELETE_SYMBOL.toString()));
        projectTeamStudentsTopBox.getChildren().addAll(studentsLabel, deleteStudentsButton);

        teamMembers = new TableView();
        // How to construct the TableView in accordance with the Team Members?
        teamMemberFirstName = new TableColumn(props.getProperty(CSGProp.STUDENT_FIRST_NAME_LABEL.toString()));
        teamMemberLastName = new TableColumn(props.getProperty(CSGProp.STUDENT_LAST_NAME_LABEL.toString()));
        teamMemberTeam = new TableColumn(props.getProperty(CSGProp.STUDENT_TEAM_LABEL.toString()));
        teamMemberRole = new TableColumn(props.getProperty(CSGProp.STUDENT_ROLE_LABEL.toString()));
        teamMembers.getColumns().addAll(teamMemberFirstName, teamMemberLastName, teamMemberTeam, teamMemberRole);

        addEditStudentsLabel = new Label(props.getProperty(CSGProp.ADD_EDIT_LABEL.toString()));

        addEditStudentsPane = new GridPane();
        firstNameLabel = new Label(props.getProperty(CSGProp.STUDENT_FIRST_NAME_LABEL.toString()));
        firstNameTextField = new TextField();
        lastNameLabel = new Label(props.getProperty(CSGProp.STUDENT_LAST_NAME_LABEL.toString()));
        lastNameTextField = new TextField();
        teamLabel = new Label(props.getProperty(CSGProp.STUDENT_TEAM_LABEL.toString()));
        teamBox = new ComboBox();
        roleLabel = new Label(props.getProperty(CSGProp.STUDENT_ROLE_LABEL.toString()));
        roleTextField = new TextField();
        addUpdateStudentsButton = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_LABEL.toString()));
        clearStudentsButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_LABEL.toString()));
        addEditStudentsPane.add(firstNameLabel, 0, 0);
        addEditStudentsPane.add(firstNameTextField, 1, 0);
        addEditStudentsPane.add(lastNameLabel, 2, 0);
        addEditStudentsPane.add(lastNameTextField, 3, 0);
        addEditStudentsPane.add(teamLabel, 0, 1);
        addEditStudentsPane.add(teamBox, 1, 2);
        addEditStudentsPane.add(roleLabel, 2, 2);
        addEditStudentsPane.add(roleTextField, 3, 2);
        addEditStudentsPane.add(addUpdateStudentsButton, 4, 2);
        addEditStudentsPane.add(clearStudentsButton, 5, 2);
        projectTeamStudentsVBox.getChildren().addAll(projectTeamStudentsTopBox, teamMembers, addEditStudentsLabel, addEditStudentsPane);
        projectDataVBox.getChildren().addAll(projectTeamsVBox, projectTeamStudentsVBox);
        projectDataTab.setContent(projectDataVBox);
        //Now add the Tabs
        t.getTabs().addAll(courseDataTab, taDataTab, recitationDataTab, scheduleDataTab, projectDataTab);
        t.getStyleClass().add("tab_background_color");

        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(t);
        
        //Workspace handler here
        workspace.setOnKeyPressed(e ->{
            if(e.isControlDown())
                if(e.getCode() == KeyCode.Z)
                    controller.Undo();
                else if(e.getCode() == KeyCode.Y)
                    controller.Redo();
        });
        
        workspace.setStyle("-fx-background-color: bisque;");
    }
    
    public TabPane getT() {
        return t;
    }
    
    public Tab getCourseDataTab() {
        return courseDataTab;
    }
    
    public Tab getTADataTab() {
        return taDataTab;
    }
    
    public Tab getRecitationDataTab() {
        return recitationDataTab;
    }
    
    public Tab getScheduleDataTab() {
        return scheduleDataTab;
    }
    
    public Tab getProjectDataTab() {
        return projectDataTab;
    }
    
    public VBox getCourseDataTabVBox() {
        return courseDataTabVBox;
    }
    
    public HBox getTADataTabHBox() {
        return taDataTabHBox;
    }
    
    public VBox getRecitationDataVBox() {
        return recitationDataVBox;
    }
    
    public VBox getScheduleDataVBox() {
        return scheduleDataVBox;
    }
    
    public VBox getProjectDataVBox() {
        return projectDataVBox;
    }
    
    public HBox getTADataTextFieldPane() {
        return taDataTextFieldPane;
    }

    public GridPane getTADataOfficeHoursGridPane() {
        return taDataOfficeHoursGridPane;
    }
    
    public HashMap<String, Pane> getTADataOfficeHoursGridTimeHeaderPanes() {
        return taDataOfficeHoursGridTimeHeaderPanes;
    }
    
    public HashMap<String, Label> getTADataOfficeHoursGridTimeHeaderLabels() {
        return taDataOfficeHoursGridTimeHeaderLabels;
    }
    
    public HashMap<String, Pane> getTADataOfficeHoursGridDayHeaderPanes() {
        return taDataOfficeHoursGridDayHeaderPanes;
    }
    
    public HashMap<String, Label> getTADataOfficeHoursGridDayHeaderLabels() {
        return taDataOfficeHoursGridDayHeaderLabels;
    }
    
    public HashMap<String, Pane> getTADataOfficeHoursGridTimeCellPanes() {
        return taDataOfficeHoursGridTimeCellPanes;
    }
    
    public HashMap<String, Label> getTADataOfficeHoursGridTimeCellLabels() {
        return taDataOfficeHoursGridTimeCellLabels;
    }
    
    public HashMap<String, Pane> getTADataOfficeHoursGridTACellPanes() {
        return taDataOfficeHoursGridTACellPanes;
    }
    
    public HashMap<String, Label> getTADataOfficeHoursGridTACellLabels() {
        return taDataOfficeHoursGridTACellLabels;
    }
    
    public TextField getTANameTextField() {
        return taNameTextField;
    }

    public TextField getTAEmailTextField() {
        return taEmailTextField;
    }
    
    public Button getAddTAButton() {
        return addTAButton;
    }
    
    public Button getClearTAButton() {
        return clearTAButton;
    }
    
    public TableView getTAInformationTable() {
        return taInformation;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : taDataOfficeHoursGridTACellLabels.keySet()) {
            if (taDataOfficeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return taDataOfficeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return taDataOfficeHoursGridTACellPanes.get(cellPane);
    }

    public ComboBox getOfficeHour(boolean start) {
        if (start) {
            return officeHoursStartBox;
        }
        return officeHoursEndBox;
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
        // CLEAR OUT THE GRID PANE
        taDataOfficeHoursGridPane.getChildren().clear();

        // AND THEN ALL THE GRID PANES AND LABELS
        taDataOfficeHoursGridTimeHeaderPanes.clear();
        taDataOfficeHoursGridTimeHeaderLabels.clear();
        taDataOfficeHoursGridDayHeaderPanes.clear();
        taDataOfficeHoursGridDayHeaderLabels.clear();
        taDataOfficeHoursGridTimeCellPanes.clear();
        taDataOfficeHoursGridTimeCellLabels.clear();
        taDataOfficeHoursGridTACellPanes.clear();
        taDataOfficeHoursGridTACellLabels.clear();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData csgData = (CSGData) dataComponent;
        reloadOfficeHoursGrid(csgData);
    }

    public void reloadOfficeHoursGrid(CSGData dataComponent) {
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, taDataOfficeHoursGridTimeHeaderPanes, taDataOfficeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, taDataOfficeHoursGridDayHeaderPanes, taDataOfficeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, taDataOfficeHoursGridTimeCellPanes, taDataOfficeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, taDataOfficeHoursGridTimeCellPanes, taDataOfficeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, taDataOfficeHoursGridTimeCellPanes, taDataOfficeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, taDataOfficeHoursGridTimeCellPanes, taDataOfficeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, taDataOfficeHoursGridTACellPanes, taDataOfficeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, taDataOfficeHoursGridTACellPanes, taDataOfficeHoursGridTACellLabels, col, row + 1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : taDataOfficeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }

        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CSGStyle csgStyle = (CSGStyle) app.getStyleComponent();
        csgStyle.initOfficeHoursGridStyle();
    }

    public void addCellToGrid(CSGData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);

        // NOW PUT THE CELL IN THE WORKSPACE GRID
        taDataOfficeHoursGridPane.add(cellPane, col, row);

        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);

        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());
    }
    
    
}

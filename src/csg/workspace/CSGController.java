/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import csg.data.TeachingAssistant;
import csg.jtps.TAAdderUR;
import csg.jtps.TAtoggleUR;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.util.regex.Pattern;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
//import csg.data.TAData;
import csg.CSGProp;
import static csg.CSGProp.BAD_START_END_DATES_MESSAGE;
import static csg.CSGProp.BAD_START_END_DATES_TITLE;
import static csg.CSGProp.MISSING_RECITATION_DAY_TIME_MESSAGE;
import static csg.CSGProp.MISSING_RECITATION_DAY_TIME_TITLE;
import static csg.CSGProp.MISSING_RECITATION_INSTRUCTOR_MESSAGE;
import static csg.CSGProp.MISSING_RECITATION_INSTRUCTOR_TITLE;
import static csg.CSGProp.MISSING_RECITATION_LOCATION_MESSAGE;
import static csg.CSGProp.MISSING_RECITATION_LOCATION_TITLE;
import static csg.CSGProp.MISSING_RECITATION_SECTION_MESSAGE;
import static csg.CSGProp.MISSING_RECITATION_SECTION_TITLE;
import static csg.CSGProp.MISSING_RECITATION_TA_MESSAGE;
import static csg.CSGProp.MISSING_RECITATION_TA_TITLE;
import static csg.CSGProp.MISSING_TA_EMAIL_MESSAGE;
import static csg.CSGProp.MISSING_TA_EMAIL_TITLE;
import static csg.CSGProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGProp.MISSING_TA_NAME_TITLE;
import static csg.CSGProp.SAME_TA_MESSAGE;
import static csg.CSGProp.SAME_TA_TITLE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import csg.jtps.TAReplaceUR;
import csg.jtps.TAdeletUR;
import csg.jtps.TAhourschangeUR;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.file.TimeSlot;
import csg.jtps.CourseSiteEditUR;
import csg.jtps.DatePickerChangeUR;
import csg.jtps.ProjectTeamAdderUR;
import csg.jtps.ProjectTeamDeleteUR;
import csg.jtps.ProjectTeamReplaceUR;
import csg.jtps.RecitationAdderUR;
import csg.jtps.RecitationDeleteUR;
import csg.jtps.RecitationReplaceUR;
import csg.jtps.ScheduleItemAdderUR;
import csg.jtps.ScheduleItemDeleteUR;
import csg.jtps.ScheduleItemEditUR;
import csg.jtps.StudentAdderUR;
import csg.jtps.StudentDeleteUR;
import csg.jtps.StudentEditUR;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.CSGStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;

/**
 *
 * @author dsli
 */
public class CSGController {

    static jTPS jTPS = new jTPS();
    CourseSiteGeneratorApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGController(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTANameTextField();
        TextField emailTextField = workspace.getTAEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } else if (!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_EMAIL_INVALID_TITLE.toString()), props.getProperty(CSGProp.TA_EMAIL_INVALID_MESSAGE.toString()));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {

            // ADD THE NEW TA TO THE DATA
            jTPS_Transaction addTAUR = new TAAdderUR(app);
            jTPS.addTransaction(addTAUR);

            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    public void handleAddRecitation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getSectionTextField();
        TextField instructorTextField = workspace.getInstructorTextField();
        TextField dayTimeTextField = workspace.getDayTimeTextField();
        TextField locationTextField = workspace.getLocationTextField();
        ComboBox supervisingTAComboBox1 = workspace.getSupervisingTa1Box();
        ComboBox supervisingTAComboBox2 = workspace.getSupervisingTa2Box();

        // For checking the existing data
        CSGData dataComponent = (CSGData) app.getDataComponent();

        // For in case we need to alert the user to an error
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // Getting our String variables
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        // How to represent your TA Objects?
        TeachingAssistant ta1 = (TeachingAssistant) supervisingTAComboBox1.getSelectionModel().getSelectedItem();
        TeachingAssistant ta2 = (TeachingAssistant) supervisingTAComboBox2.getSelectionModel().getSelectedItem();
        String ta1Str = "";
        String ta2Str = "";
        if (ta1 != null) {
            ta1Str = ta1.toString();
        }
        if (ta2 != null) {
            ta2Str = ta2.toString();
        }

        if (section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_SECTION_TITLE), props.getProperty(MISSING_RECITATION_SECTION_MESSAGE));
        } else if (instructor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_INSTRUCTOR_TITLE), props.getProperty(MISSING_RECITATION_INSTRUCTOR_MESSAGE));
        } else if (dayTime.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_DAY_TIME_TITLE), props.getProperty(MISSING_RECITATION_DAY_TIME_MESSAGE));
        } else if (location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_LOCATION_TITLE), props.getProperty(MISSING_RECITATION_LOCATION_MESSAGE));
        } else if (ta1Str.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_TA_TITLE), props.getProperty(MISSING_RECITATION_TA_MESSAGE));
        } else if (ta2Str.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_RECITATION_TA_TITLE), props.getProperty(MISSING_RECITATION_TA_MESSAGE));
        } else if (ta1Str.equals(ta2Str)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(SAME_TA_TITLE), props.getProperty(SAME_TA_MESSAGE));
        } else {
            jTPS_Transaction addRecitationUR = new RecitationAdderUR(app);
            jTPS.addTransaction(addRecitationUR);

            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");
            supervisingTAComboBox1.getSelectionModel().selectFirst();
            supervisingTAComboBox2.getSelectionModel().selectFirst();

            // We've changed stuff
            markWorkAsEdited();
        }
    }

    public void handleAddProjectTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        TextField teamNameTextField = workspace.getTeamNameTextField();
        ColorPicker teamColorPicker = workspace.getTeamColorPicker();
        ColorPicker teamTextColorPicker = workspace.getTeamTextColorPicker();
        TextField teamLinkTextField = workspace.getTeamLinkTextField();

        String teamName = teamNameTextField.getText();
        Color teamColor = teamColorPicker.getValue();
        Color teamTextColor = teamTextColorPicker.getValue();
        String teamLink = teamLinkTextField.getText();

        if (teamName.isEmpty()) {

        } else if (teamLink.isEmpty()) {

        } else {
            jTPS_Transaction addTeamUR = new ProjectTeamAdderUR(app);
            jTPS.addTransaction(addTeamUR);
            markWorkAsEdited();
        }
    }

    public void handleEditProjectTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();

        TableView projectTeams = workspace.getProjectTeams();
        TextField teamNameTextField = workspace.getTeamNameTextField();
        ColorPicker teamColorPicker = workspace.getTeamColorPicker();
        ColorPicker teamTextColorPicker = workspace.getTeamTextColorPicker();
        TextField teamLinkTextField = workspace.getTeamLinkTextField();

        String teamName = teamNameTextField.getText();
        Color teamColor = teamColorPicker.getValue();
        Color teamTextColor = teamTextColorPicker.getValue();
        String teamLink = teamLinkTextField.getText();

        jTPS_Transaction replaceTeamUR = new ProjectTeamReplaceUR(app);
        jTPS.addTransaction(replaceTeamUR);
        markWorkAsEdited();
        projectTeams.refresh();
    }

    public void handleAddStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        TableView projectTeams = workspace.getProjectTeams();
        Object selectProjectTeam = projectTeams.getSelectionModel().getSelectedItem();
        ProjectTeam projectTeam = (ProjectTeam) selectProjectTeam;

        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamBox = workspace.getTeamBox();
        TextField roleTextField = workspace.getRoleTextField();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        ProjectTeam team = (ProjectTeam) teamBox.getSelectionModel().getSelectedItem();
        String role = roleTextField.getText();

        jTPS_Transaction addStudentUR = new StudentAdderUR(app);
        jTPS.addTransaction(addStudentUR);
        markWorkAsEdited();
    }

    public void handleEditStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        TableView teamMembers = workspace.getTeamMembers();
        Object selectStudent = teamMembers.getSelectionModel().getSelectedItem();
        Student projectTeam = (Student) selectStudent;

        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamBox = workspace.getTeamBox();
        TextField roleTextField = workspace.getRoleTextField();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        ProjectTeam team = (ProjectTeam) teamBox.getSelectionModel().getSelectedItem();
        String role = roleTextField.getText();

        jTPS_Transaction addStudentUR = new StudentEditUR(app);
        jTPS.addTransaction(addStudentUR);
        teamMembers.refresh();
        markWorkAsEdited();

    }

    public void handleAddScheduleItem() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        ComboBox typeBox = workspace.getTypeBox();
        DatePicker scheduleItemDatePicker = workspace.getDatePicker();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();

        String type = typeBox.getSelectionModel().getSelectedItem().toString();
        LocalDate scheduleItemDate = scheduleItemDatePicker.getValue();
        Calendar c = Calendar.getInstance();
        c.set(scheduleItemDate.getYear(), scheduleItemDate.getMonthValue() - 1, scheduleItemDate.getDayOfMonth());
        Date date = c.getTime();
        String time = timeTextField.getText();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();

        jTPS_Transaction addScheduleItemUR = new ScheduleItemAdderUR(app);
        jTPS.addTransaction(addScheduleItemUR);
        markWorkAsEdited();
    }

    public void handleEditScheduleItem() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        ComboBox typeBox = workspace.getTypeBox();
        DatePicker scheduleItemDatePicker = workspace.getDatePicker();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();

        String type = typeBox.getSelectionModel().getSelectedItem().toString();
        LocalDate scheduleItemDate = scheduleItemDatePicker.getValue();
        Calendar c = Calendar.getInstance();
        c.set(scheduleItemDate.getYear(), scheduleItemDate.getMonthValue() - 1, scheduleItemDate.getDayOfMonth());
        Date date = c.getTime();
        String time = timeTextField.getText();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();

        jTPS_Transaction editScheduleItemUR = new ScheduleItemEditUR(app);
        jTPS.addTransaction(editScheduleItemUR);
        workspace.getScheduleItems().refresh();
        markWorkAsEdited();
    }

    public void handleRemoveScheduleItem() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();

        TableView scheduleItems = workspace.getScheduleItems();
        Object selectedItem = scheduleItems.getSelectionModel().getSelectedItem();
        ScheduleItem scheduleItemToRemove = (ScheduleItem) selectedItem;

        jTPS_Transaction deleteScheduleItemUR = new ScheduleItemDeleteUR(app, scheduleItemToRemove);
        jTPS.addTransaction(deleteScheduleItemUR);
        markWorkAsEdited();
    }

    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }

    /**
     * This function provides a response for when the user presses a keyboard
     * key. Note that we're only responding to Delete, to remove a TA.
     *
     * @param code The keyboard code pressed.
     */
    public void handleKeyPressTAInformation(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            deleteTAFromTAInformation();
        }
    }

    // How to handle the DELETE button being pressed
    public void deleteTAFromTAInformation() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction deletUR = new TAdeletUR(app, taName);
            jTPS.addTransaction(deletUR);

            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    // Handling DELETE for RecitationData TableView
    public void handleKeyPressRecitationData(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            deleteRecitationFromRecitationData();
        }
    }

    public void deleteRecitationFromRecitationData() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recitationData = workspace.getRecitationData();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = recitationData.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            Recitation r = (Recitation) selectedItem;
            String recitationSection = r.getSection();
            String recitationInstructor = r.getInstructor();
            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction deletUR = new RecitationDeleteUR(app, recitationSection, recitationInstructor);
            jTPS.addTransaction(deletUR);

            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    // Handling DELETE for ScheduleItem TableView - NOT COMPLETE, NEED TO DO THE JTPS FOR SCHEDULEITEMS
    public void handleKeyPressScheduleItems(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            deleteScheduleItemFromScheduleItems();
        }
    }

    public void deleteScheduleItemFromScheduleItems() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleItems = workspace.getScheduleItems();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = scheduleItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            /*ScheduleItem s = (ScheduleItem)selectedItem;
                String recitationSection = r.getSection();
                String recitationInstructor = r.getInstructor();
                CSGData data = (CSGData)app.getDataComponent();
                
                jTPS_Transaction deletUR = new RecitationDeleteUR(app, recitationSection, recitationInstructor);
                jTPS.addTransaction(deletUR);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                // WE'VE CHANGED STUFF
                markWorkAsEdited();*/
        }
    }

    // Handling DELETE for ScheduleItem TableView - NOT COMPLETE, NEED TO DO THE JTPS FOR SCHEDULEITEMS
    public void handleKeyPressProjectTeams(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            deleteProjectTeamFromProjectTeams();
        }
    }

    public void deleteProjectTeamFromProjectTeams() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView projectTeams = workspace.getProjectTeams();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = projectTeams.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            ProjectTeam pt = (ProjectTeam) selectedItem;
            String teamName = pt.getName();

            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction deletUR = new ProjectTeamDeleteUR(app, teamName);
            jTPS.addTransaction(deletUR);

            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
        projectTeams.refresh();
    }

    // Handling DELETE for ScheduleItem TableView - NOT COMPLETE, NEED TO DO THE JTPS FOR SCHEDULEITEMS
    public void handleKeyPressStudents(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            deleteStudentFromTeamMembers();
        }
    }

    public void deleteStudentFromTeamMembers() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView teamMembers = workspace.getTeamMembers();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = teamMembers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            Student s = (Student) selectedItem;
            String firstName = s.getFirstName();
            String lastName = s.getLastName();
            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction deletUR = new StudentDeleteUR(app, s);
            jTPS.addTransaction(deletUR);

            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTADataOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTADataOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTADataOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTADataOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTADataOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTADataOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            CSGData data = (CSGData) app.getDataComponent();
            String cellKey = pane.getId();

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            jTPS_Transaction toggleUR = new TAtoggleUR(taName, cellKey, data);
            jTPS.addTransaction(toggleUR);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    public void changeExistTA() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        CSGData data = (CSGData) app.getDataComponent();
        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        String name = ta.getName();
        String newName = workspace.getTANameTextField().getText();
        String newEmail = workspace.getTAEmailTextField().getText();
        jTPS_Transaction replaceTAUR = new TAReplaceUR(app);
        jTPS.addTransaction(replaceTAUR);
        markWorkAsEdited();
    }

    public void loadTAtotext() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAInformationTable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String name = ta.getName();
            String email = ta.getEmail();
            workspace.getTANameTextField().setText(name);
            workspace.getTAEmailTextField().setText(email);
        }
    }

    public void loadScheduleItemToText() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleItems = workspace.getScheduleItems();
        Object selectedItem = scheduleItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ScheduleItem s = (ScheduleItem) selectedItem;
            String type = s.getType();
            Date date = s.getDate();
            LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
            String time = s.getTime();
            String title = s.getTitle();
            String topic = s.getTopic();
            String link = s.getLink();
            String criteria = s.getCriteria();

            workspace.getTypeBox().getSelectionModel().select(type);
            workspace.getDatePicker().setValue(ld);
            workspace.getTimeTextField().setText(time);
            workspace.getScheduleItemTitleTextField().setText(title);
            workspace.getTopicTextField().setText(topic);
            workspace.getLinkTextField().setText(link);
            workspace.getCriteriaTextField().setText(criteria);
        }
    }

    public void handleEditRecitation() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        TableView recitationTable = workspace.getRecitationData();
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        Recitation recitation = (Recitation) selectedItem;
        String section = recitation.getSection();
        String instructor = recitation.getInstructor();
        String dayTime = recitation.getDayTime();
        String location = recitation.getLocation();
        TeachingAssistant ta1 = recitation.getSupervisingTA1();
        TeachingAssistant ta2 = recitation.getSupervisingTA2();
        
        String newTA1 = workspace.getSupervisingTa1Box().getSelectionModel().getSelectedItem().toString();
        String newTA2 = workspace.getSupervisingTa2Box().getSelectionModel().getSelectedItem().toString();
        if (!(newTA1.equals(newTA2))) {
        jTPS_Transaction editRecitationUR = new RecitationReplaceUR(app);
        jTPS.addTransaction(editRecitationUR);
        markWorkAsEdited();
        //recitationTable.refresh();
        }
        else {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.SAME_TA_TITLE.toString()), props.getProperty(CSGProp.SAME_TA_MESSAGE.toString()));
        }
    }

    public void loadRecitationtotext() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recitationData = workspace.getRecitationData();
        Object selectedItem = recitationData.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Recitation r = (Recitation) selectedItem;
            String section = r.getSection();
            String instructor = r.getInstructor();
            String dayTime = r.getDayTime();
            String location = r.getLocation();
            TeachingAssistant ta1 = r.getSupervisingTA1();
            TeachingAssistant ta2 = r.getSupervisingTA2();
            workspace.getSectionTextField().setText(section);
            workspace.getInstructorTextField().setText(instructor);
            workspace.getDayTimeTextField().setText(dayTime);
            workspace.getLocationTextField().setText(location);
            workspace.getSupervisingTa1Box().getSelectionModel().select(ta1);
            workspace.getSupervisingTa2Box().getSelectionModel().select(ta2);
        }
    }

    public void handleRemoveRecitationRequest() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recitationData = workspace.getRecitationData();
        Object selectedItem = recitationData.getSelectionModel().getSelectedItem();
        Recitation r = (Recitation) selectedItem;
        String section = r.getSection();
        String instructor = r.getInstructor();
        jTPS_Transaction deleteRecitationUR = new RecitationDeleteUR(app, section, instructor);
        jTPS.addTransaction(deleteRecitationUR);
        markWorkAsEdited();
    }

    public void loadProjectTeamToText() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView projectTeams = workspace.getProjectTeams();
        Object selectedItem = projectTeams.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ProjectTeam p = (ProjectTeam) selectedItem;
            String teamName = p.getName();
            Color teamColor = p.getColor();
            Color teamTextColor = p.getTextColor();
            String teamLink = p.getLink();

            TextField teamNameTextField = workspace.getTeamNameTextField();
            ColorPicker teamColorPicker = workspace.getTeamColorPicker();
            ColorPicker teamTextColorPicker = workspace.getTeamTextColorPicker();
            TextField teamLinkTextField = workspace.getTeamLinkTextField();

            teamNameTextField.setText(teamName);
            teamColorPicker.setValue(teamColor);
            teamTextColorPicker.setValue(teamTextColor);
            teamLinkTextField.setText(teamLink);
        }

    }

    public void loadStudentToText() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView teamMembers = workspace.getTeamMembers();
        Object selectedItem = teamMembers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Student s = (Student) selectedItem;
            String firstName = s.getFirstName();
            String lastName = s.getLastName();
            String teamName = s.getTeamName();
            String role = s.getRole();

            TextField firstNameTextField = workspace.getFirstNameTextField();
            TextField lastNameTextField = workspace.getLastNameTextField();
            ComboBox teamBox = workspace.getTeamBox();
            TextField roleTextField = workspace.getRoleTextField();

            firstNameTextField.setText(firstName);
            lastNameTextField.setText(lastName);
            teamBox.getSelectionModel().select(teamName);
            roleTextField.setText(role);
        }
    }

    public void Undo() {
        jTPS.undoTransaction();
        markWorkAsEdited();
    }

    public void Redo() {
        jTPS.doTransaction();
        markWorkAsEdited();
    }

    public void changeTime() {
        CSGData data = (CSGData) app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        int startTime = data.getStartHour();
        int endTime = data.getEndHour();
        int newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        int newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        if (newStartTime > endTime || newEndTime < startTime) {
            comboBox1.getSelectionModel().select(startTime);
            comboBox2.getSelectionModel().select(endTime);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.START_OVER_END_TITLE.toString()), props.getProperty(CSGProp.START_OVER_END_MESSAGE.toString()));
            return;
        }
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
        if (officeHours.isEmpty()) {
            workspace.getTADataOfficeHoursGridPane().getChildren().clear();
            data.initHours("" + newStartTime, "" + newEndTime);
        }
        String firsttime = officeHours.get(0).getTime();
        int firsthour = Integer.parseInt(firsttime.substring(0, firsttime.indexOf('_')));
        if (firsttime.contains("pm")) {
            firsthour += 12;
        }
        if (firsttime.contains("12")) {
            firsthour -= 12;
        }
        String lasttime = officeHours.get(officeHours.size() - 1).getTime();
        int lasthour = Integer.parseInt(lasttime.substring(0, lasttime.indexOf('_')));
        if (lasttime.contains("pm")) {
            lasthour += 12;
        }
        if (lasttime.contains("12")) {
            lasthour -= 12;
        }
        if (firsthour < newStartTime || lasthour + 1 > newEndTime) {
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(CSGProp.OFFICE_HOURS_REMOVED_TITLE.toString()), props.getProperty(CSGProp.OFFICE_HOURS_REMOVED_MESSAGE).toString());
            String selection = yesNoDialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                comboBox1.getSelectionModel().select(startTime);
                comboBox2.getSelectionModel().select(endTime);
                return;
            }
        }

        jTPS_Transaction changeTimeUR = new TAhourschangeUR(app);
        jTPS.addTransaction(changeTimeUR);

        markWorkAsEdited();
    }

    public void handleStartEndDatePickerChange(boolean triggered) {
        if (triggered) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            CSGData data = (CSGData) app.getDataComponent();
            TableView scheduleItems = workspace.getScheduleItems();

            DatePicker startMondayPicker = workspace.getStartingMondayPicker();
            DatePicker endFridayPicker = workspace.getEndingFridayPicker();

            LocalDate ldMonday = startMondayPicker.getValue();
            LocalDate ldFriday = endFridayPicker.getValue();

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.set(ldMonday.getYear(), ldMonday.getMonthValue() - 1, ldMonday.getDayOfMonth());
            c2.set(ldFriday.getYear(), ldFriday.getMonthValue() - 1, ldFriday.getDayOfMonth());

            Date startMondayDate = c1.getTime();
            Date endFridayDate = c2.getTime();
            if (validateDatePickerChange(startMondayDate, endFridayDate)) {
                jTPS_Transaction datePickerEditUR = new DatePickerChangeUR(app);
                jTPS.addTransaction(datePickerEditUR);

                scheduleItems.refresh();
                markWorkAsEdited();
            } else {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(BAD_START_END_DATES_TITLE), props.getProperty(BAD_START_END_DATES_MESSAGE));
                Date oldStart = data.getStartDate();
                Date oldEnd = data.getEndDate();

                Instant instant1 = oldStart.toInstant();
                ZonedDateTime zdt1 = instant1.atZone(ZoneId.systemDefault());
                LocalDate oldld1 = zdt1.toLocalDate();
                //oldld1 = oldld1.minusMonths(1);

                Instant instant2 = oldEnd.toInstant();
                ZonedDateTime zdt2 = instant2.atZone(ZoneId.systemDefault());
                LocalDate oldld2 = zdt2.toLocalDate();
                //oldld2 = oldld2.minusMonths(1);

                startMondayPicker.setValue(oldld1);
                endFridayPicker.setValue(oldld2);
            }
        }
        else {
            
        }
    }

    public boolean validateDatePickerChange(Date newStartDate, Date newEndDate) {
        if (newStartDate.compareTo(newEndDate) >= 0) {
            return false;
        }
        return true;
    }

    public void handleCheckBoxChange() {
        markWorkAsEdited();
    }

    public void handleUpdateCourseSiteInformation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        jTPS_Transaction courseEditUR = new CourseSiteEditUR(app);
        jTPS.addTransaction(courseEditUR);

        markWorkAsEdited();
    }

    public void handleDirectoryChange() {
        markWorkAsEdited();
    }
}

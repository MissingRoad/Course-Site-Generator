/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGProp;
import csg.CourseSiteGeneratorApp;
import csg.file.TimeSlot;
import csg.workspace.CSGWorkspace;
import djf.components.AppDataComponent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

/**
 *
 * @author dsli
 */
public class CSGData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CourseSiteGeneratorApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    
    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;
    
    // THIS DATA STRUCTURE STORES THE COURSE INFORMATION
    CourseSite courseSiteInfo;
    
    // THIS DATA STRUCTURE WILL STORE THE DATA FOR THE RECITATIONS
    ObservableList<Recitation> recitations;
    
    // THE SCHEDULE ITEMS
    ObservableList<ScheduleItem> scheduleItems;
    
    // THE PROJECT TEAMS
    ObservableList<ProjectTeam> projectTeams;
    
    // AND THE STUDENTS
    ObservableList<Student> students;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 0;
    public static final int MAX_END_HOUR = 23;
    
    // THESE ARE THE VALUES FOR THE CALENDAR BOUNDARY DATEPICKERS
    Date startDate;
    Date endDate;

    /**
     * This constructor will setup the required data structures for
     * use, but will have to wait on the office hours grid, since
     * it receives the StringProperty objects from the Workspace.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public CSGData(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        courseSiteInfo = new CourseSite();
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE, and the other observableLists
        teachingAssistants = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        scheduleItems = FXCollections.observableArrayList();
        projectTeams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
    
    
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
        
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(null);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
    
    // ACCESSOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public CourseSite getCourseSiteInfo() {
        return courseSiteInfo;
    }
    
    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }
    
    public ObservableList getRecitations() {
        return recitations;
    }
    
    public ObservableList getScheduleItems() {
        return scheduleItems;
    }
    
    public ObservableList getProjectTeams() {
        return projectTeams;
    }
    
    public ObservableList getStudents() {
        return students;
    }
    
    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }
    
    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
//        if (hour < startHour)
        if(time.contains("pm"))
            milHour += 12;
        if(time.contains("12"))
            milHour -= 12;
        row += (milHour - startHour) * 2;
        if (time.contains("_30"))
            row += 1;
        return getCellKey(col, row);
    }
    
    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    /**
     * This method is for giving this data manager the string property
     * for a given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }    
    
    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
                                int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    

    
    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;
        
        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();
            
        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        workspaceComponent.reloadOfficeHoursGrid(this);
        
        workspaceComponent.getOfficeHour(true).getSelectionModel().select(startHour);
        workspaceComponent.getOfficeHour(false).getSelectionModel().select(endHour);
    }
    
    
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }
    
    //This method is for initializing the boolean value of isUndergrad for each TA
    public boolean initIsUndergradTA(String isUndergradString) {
        
        return (isUndergradString.equalsIgnoreCase("true") ? true : false);
    }


    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }
    
    public Recitation findRecitation(String recNum, String instructor) {
        for (Recitation r: recitations) {
            if (r.getInstructor().equals(instructor) && r.getSection().equals(recNum))
                return r;
        }
        return null;
    }

    public boolean containsRecitation(String recNum, String instructor) {
        for (Recitation r: recitations) {
            if (r.getInstructor().equals(instructor) && r.getSection().equals(recNum))
                return true;
        }
        return false;
    }
    
    public boolean containsScheduleItem(String title, Date d) {
        for (ScheduleItem s: scheduleItems) {
            if (s.getTitle().equals(title) &&  s.getDate().equals(d))
                return true;
        }
        return false;
    }
    
    public boolean containsProjectTeam(String name) {
        for (ProjectTeam p: projectTeams) {
            if (p.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addTA(String initName, String initEmail, boolean isUndergrad) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, isUndergrad);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                return;
            }
        }
    }
    
    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }
    
    public void addRecitation(String section, String instructor, String dayTime, String location, TeachingAssistant ta1, TeachingAssistant ta2) {
        Recitation recitation = new Recitation(section, instructor, dayTime, location, ta1, ta2);
        
        // ADD THE RECITATION
        if (!containsRecitation(section, instructor)) {
            recitations.add(recitation);
        }
        
        // Provision for sorting here...
    }
    
    public void removeRecitation(String section, String instructor) {
        
        if (containsRecitation(section, instructor)) {
            recitations.remove(findRecitation(section, instructor));
        }
    }
    
    // Overloading the removeRecitaion method?
    
    public void addScheduleItem(String type, Date date, String title, String topic) {
        ScheduleItem s = new ScheduleItem(type, date, title, topic);
        
        if (!containsScheduleItem(title, date)) {
            scheduleItems.add(s);
        }
    }
    
    // Add remove for ScheduleItem here
    
    public void addProjectTeam(String name, Color c, Color t, String link) {
        ProjectTeam p = new ProjectTeam(name, c, t, link);
        
        if (!containsProjectTeam(name))
            projectTeams.add(p);
    }
    
    public void removeProjectTeam(String name) {
        for (ProjectTeam p: projectTeams) {
            if (!containsProjectTeam(name))
                projectTeams.remove(p);
        }
    }

    /**
     * This function toggles the taName in the cell represented
     * by cellKey. Toggle means if it's there it removes it, if
     * it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }
    
    /**
     * This method removes taName from the office grid cell
     * represented by cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        }
        // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        }
        // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }
    
    public void replaceTAName(String name, String newName){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        for(Pane p : workspace.getTADataOfficeHoursGridTACellPanes().values()){
            String cellKey = p.getId();
            StringProperty cellProp = officeHours.get(cellKey);
            String cellText = cellProp.getValue();
            if (cellText.contains(name)) {
                toggleTAOfficeHours(cellKey, name);
                toggleTAOfficeHours(cellKey, newName);
            }
        }
    }
    
    public void changeTime(int startTime, int endTime, ArrayList<TimeSlot> officeHours){
        initHours("" + startTime, "" + endTime);
        for(TimeSlot ts : officeHours){
            String temp = ts.getTime();
            int tempint = Integer.parseInt(temp.substring(0, temp.indexOf('_')));
            if(temp.contains("pm"))
                tempint += 12;
            if(temp.contains("12"))
                tempint -= 12;
            if(tempint >= startTime && tempint <= endTime)
                addOfficeHoursReservation(ts.getDay(), ts.getTime(), ts.getName());
        }
    }
    
    public void changeDate(Date startDate, Date endDate) {
        
    }
    
    public TeachingAssistant findTeachingAssistant(String name) {
        for (TeachingAssistant ta: teachingAssistants) {
            if (ta.getName().equals(name)) {
                return ta;
            }
            if (ta.getName().contains(name))
                return ta;
        }
        return null;
    }
    
    public ProjectTeam findProjectTeam(String teamName) {
        for (ProjectTeam p: projectTeams) {
            if (p.getName().equals(teamName))
                return p;
        }
        return null;
    }
}
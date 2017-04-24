/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectTeam;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import csg.data.TeachingAssistant;
import static djf.settings.AppStartupConstants.PATH_PUBLIC_HTML;
import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author dsli
 */
public class CSGFiles implements AppFileComponent {
    CourseSiteGeneratorApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_IS_UNDERGRAD_TA = "is_undergrad_ta";
    static final String JSON_EMAIL = "email";
    static final String JSON_RECITATION_SECTION = "recitation_section";
    static final String JSON_RECITATION_INSTRUCTOR = "recitation_instructor";
    static final String JSON_RECITATION_DAY_TIME = "recitation_day_time";
    static final String JSON_RECITATION_LOCATION = "recitation_location";
    static final String JSON_RECITATION_TA1 = "recitation_ta1";
    static final String JSON_RECITATION_TA2 = "recitation_ta2";
    static final String JSON_START_DATE = "startDate";
    static final String JSON_END_DATE = "endDate";
    static final String JSON_SCHEDULE_TYPE = "type";
    static final String JSON_SCHEDULE_ITEM_DATE = "schedule_item_date";
    static final String JSON_SCHEDULE_ITEM_TITLE = "schedule_item_title";
    static final String JSON_SCHEDULE_ITEM_TOPIC = "schedule_item_topic";
    static final String JSON_PROJECT_TEAM_NAME = "project_team_name";
    static final String JSON_PROJECT_TEAM_RED_COLOR = "project_team_red_color";
    static final String JSON_PROJECT_TEAM_GREEN_COLOR = "project_team_green_color";
    static final String JSON_PROJECT_TEAM_BLUE_COLOR = "project_team_blue_color";
    static final String JSON_PROJECT_TEAM_COLOR_OPACITY = "project_team_color_opacity";
    static final String JSON_PROJECT_TEAM_TEXT_RED_COLOR = "project_team_text_red_color";
    static final String JSON_PROJECT_TEAM_TEXT_GREEN_COLOR = "project_team_text_green_color";
    static final String JSON_PROJECT_TEAM_TEXT_BLUE_COLOR = "project_team_text_blue_color";
    static final String JSON_PROJECT_TEAM_TEXT_COLOR_OPACITY = "project_team_text_color_opacity";
    static final String JSON_PROJECT_TEAM_LINK = "project_team_link";
    static final String JSON_STUDENT_FIRST_NAME = "first_name";
    static final String JSON_STUDENT_LAST_NAME = "last_name";
    static final String JSON_STUDENT_TEAM = "team";
    static final String JSON_STUDENT_ROLE = "role";
    // Data types regarding various types for the various JSON arrays
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    static final String JSON_PROJECT_TEAMS = "project_teams";
    static final String JSON_STUDENTS = "students";
    
    public CSGFiles(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException, ParseException {
	// CLEAR THE OLD DATA OUT
	CSGData dataManager = (CSGData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            String isUndergradString = jsonTA.getString(JSON_IS_UNDERGRAD_TA);
            boolean isUndergrad = dataManager.initIsUndergradTA(isUndergradString);
            dataManager.addTA(name, email, isUndergrad);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        //Now the additional components for CSG...
        //Starting with Recitations
        JsonArray jsonRecitationsArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecitationsArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationsArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_RECITATION_SECTION);
            String instructor = jsonRecitation.getString(JSON_RECITATION_INSTRUCTOR);
            String recDayTime = jsonRecitation.getString(JSON_RECITATION_DAY_TIME);
            String recitationLocation = jsonRecitation.getString(JSON_RECITATION_LOCATION);
            String ta1Name = jsonRecitation.getString(JSON_RECITATION_TA1);
            String ta2Name = jsonRecitation.getString(JSON_RECITATION_TA2);
            TeachingAssistant ta1 = dataManager.findTeachingAssistant(ta1Name);
            TeachingAssistant ta2 = dataManager.findTeachingAssistant(ta2Name);
            dataManager.addRecitation(section, instructor, recDayTime, section, ta1, ta2);
        }
        
        //Schedule Items
        JsonArray jsonScheduleItemArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleItemArray.size(); i++) {
            JsonObject jsonScheduleItem = jsonScheduleItemArray.getJsonObject(i);
            String type = jsonScheduleItem.getString(JSON_SCHEDULE_TYPE);
            String date = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_DATE);
            String title = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TOPIC);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz YYYY");
            Date scheduleItemDate = sdf.parse(date);
            dataManager.addScheduleItem(type, scheduleItemDate, title, topic);
        }
        
        //Project Teams
        JsonArray projectTeamArray = json.getJsonArray(JSON_PROJECT_TEAMS);
        for (int i = 0; i < projectTeamArray.size(); i++) {
            JsonObject jsonProjectTeamItem = projectTeamArray.getJsonObject(i);
            String name = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_NAME);
            String teamColorRed = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_RED_COLOR);
            String teamColorGreen = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_GREEN_COLOR);
            String teamColorBlue = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_BLUE_COLOR);
            String textColorRed = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_RED_COLOR);
            String textColorGreen = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_GREEN_COLOR);
            String textColorBlue = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_BLUE_COLOR);
            String link = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_LINK);
            int teamColorRedVal = Integer.parseInt(teamColorRed);
            int teamColorGreenVal = Integer.parseInt(teamColorGreen);
            int teamColorBlueVal = Integer.parseInt(teamColorBlue);
            int textColorRedVal = Integer.parseInt(textColorRed);
            int textColorGreenVal = Integer.parseInt(textColorGreen);
            int textColorBlueVal = Integer.parseInt(textColorBlue); // 255.0, why?
            Color teamColor = new Color(teamColorRedVal, teamColorGreenVal, teamColorBlueVal);
            Color textColor = new Color(textColorRedVal, textColorGreenVal, textColorBlueVal);
            dataManager.addProjectTeam(name, textColor, textColor, link);
        }
        
        // Students - should add each student to their appropriate ProjectTeam
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentsArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_STUDENT_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_STUDENT_LAST_NAME);
            String team = jsonStudent.getString(JSON_STUDENT_TEAM);
            String role = jsonStudent.getString(JSON_STUDENT_ROLE);
            ProjectTeam projectTeam = dataManager.findProjectTeam(team);
            Student s = new Student(firstName, lastName, projectTeam, role);
            projectTeam.addStudent(s);
        }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	CSGData dataManager = (CSGData)data;
        
        // Build a JsonObject - The CourseSite Information Object
        

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_IS_UNDERGRAD_TA, ta.isUndergrad() + "").build(); // What will this line print for a String?
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        // Now build the Recitation JSON Objects to SAVE
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for (Recitation r: recitations) {
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, r.getSection())
                    .add(JSON_RECITATION_INSTRUCTOR, r.getInstructor())
                    .add(JSON_RECITATION_DAY_TIME, r.getDayTime())
                    .add(JSON_RECITATION_LOCATION, r.getLocation())
                    .add(JSON_RECITATION_TA1, r.getSupervisingTA1().getName())
                    .add(JSON_RECITATION_TA2, r.getSupervisingTA2().getName()).build();
            recitationArrayBuilder.add(recitationJson);
        }
        JsonArray recitationsArray = recitationArrayBuilder.build();
        
        // Now the Schedule Item Objects
        JsonArrayBuilder scheduleItemArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItems();
        for (ScheduleItem s: scheduleItems) {
            JsonObject scheduleItem = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_TYPE, s.getType())
                    .add(JSON_SCHEDULE_ITEM_DATE, s.getDate().toString())
                    .add(JSON_SCHEDULE_ITEM_TOPIC, s.getTopic())
                    .add(JSON_SCHEDULE_ITEM_TITLE, s.getTitle())
                    .build();
            scheduleItemArrayBuilder.add(scheduleItem);
        }
        JsonArray scheduleItemsArray = scheduleItemArrayBuilder.build();
        
        // And now the ProjectTeam JSON Objects
        JsonArrayBuilder projectTeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeam> projectTeams = dataManager.getProjectTeams();
        for (ProjectTeam p: projectTeams) {
            JsonObject projectTeamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME, p.getName())
                    .add(JSON_PROJECT_TEAM_RED_COLOR, p.getColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_GREEN_COLOR, p.getColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_BLUE_COLOR, p.getColor().getBlue() + "")
                    //.add(JSON_PROJECT_TEAM_COLOR_OPACITY, p.getColor().getTransparency())
                    .add(JSON_PROJECT_TEAM_TEXT_RED_COLOR, p.getTextColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_GREEN_COLOR, p.getTextColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_BLUE_COLOR, p.getTextColor().getBlue() + "")
                    .add(JSON_PROJECT_TEAM_LINK, p.getLink()).build();
            projectTeamArrayBuilder.add(projectTeamJson);
        }
        JsonArray projectTeamsArray = projectTeamArrayBuilder.build();
        
        // And finally the Student JSON Objects (pertaining to the teams)
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for (Student s: students) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_FIRST_NAME, s.getFirstName())
                    .add(JSON_STUDENT_LAST_NAME, s.getLastName())
                    .add(JSON_STUDENT_TEAM, s.getTeamName())
                    .add(JSON_STUDENT_ROLE, s.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentsArray = studentArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsArray)
                .add(JSON_PROJECT_TEAMS, projectTeamsArray)
                .add(JSON_STUDENTS, studentsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        CSGData dataManager = (CSGData)data;
        File fileToImport = new File(PATH_PUBLIC_HTML);
         
        File fileToExport = new File(filePath + "/newExport.html");
        
        //What would be the method for copying the file?
        //fileToImport.renameTo(fileToExport);
        //FileUtils.copyFile(fileToImport, fileToExport);
        FileUtils.copyDirectory(fileToImport, fileToExport);
        
        String officeHoursGridDataDirectory = filePath + "/newExport.html/" + "js/OfficeHoursGridData.json";
        String recitationsBuilderDirectory = filePath + "/newExport.html/" + "js/RecitationsData.json";
        String scheduleBuilderDirectory = filePath + "/newExport.html/" + "js/ScheduleData.json";
        String projectTeamsBuilderDirectory = filePath + "/newExport.html/" + "js/TeamsAndStudents.json";
        
        // Saving the file
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_IS_UNDERGRAD_TA, ta.isUndergrad() + "").build(); // What will this line print for a String?
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
        
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject taDataJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(taDataJSO);
	jsonWriter.close();

	// INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
	OutputStream os = new FileOutputStream(officeHoursGridDataDirectory);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(taDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(officeHoursGridDataDirectory);
	pw.write(prettyPrinted);
	pw.close();
        
        // RECITATIONS JSON
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for (Recitation r: recitations) {
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, r.getSection())
                    .add(JSON_RECITATION_INSTRUCTOR, r.getInstructor())
                    .add(JSON_RECITATION_DAY_TIME, r.getDayTime())
                    .add(JSON_RECITATION_LOCATION, r.getLocation())
                    .add(JSON_RECITATION_TA1, r.getSupervisingTA1().getName())
                    .add(JSON_RECITATION_TA2, r.getSupervisingTA2().getName()).build();
            recitationArrayBuilder.add(recitationJson);
        }
        JsonArray recitationsArray = recitationArrayBuilder.build();
        
        // Saving the Recitation JSON
        JsonObject recitationJSO = Json.createObjectBuilder()
                .add(JSON_RECITATIONS, recitationsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> propertiesR = new HashMap<>(1);
	propertiesR.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactoryR = Json.createWriterFactory(propertiesR);
	StringWriter swR = new StringWriter();
	JsonWriter jsonWriterR = writerFactoryR.createWriter(swR);
	jsonWriterR.writeObject(recitationJSO);
	jsonWriterR.close();

	// INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
	OutputStream osR = new FileOutputStream(recitationsBuilderDirectory);
	JsonWriter jsonFileWriterR = Json.createWriter(osR);
	jsonFileWriterR.writeObject(recitationJSO);
	String prettyPrintedR = sw.toString();
	PrintWriter pwR = new PrintWriter(recitationsBuilderDirectory);
	pwR.write(prettyPrintedR);
	pwR.close();
        
        // Now the Schedule Item Objects
        JsonArrayBuilder scheduleItemArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItems();
        for (ScheduleItem s: scheduleItems) {
            JsonObject scheduleItem = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_TYPE, s.getType())
                    .add(JSON_SCHEDULE_ITEM_DATE, s.getDate().toString())
                    .add(JSON_SCHEDULE_ITEM_TOPIC, s.getTopic())
                    .add(JSON_SCHEDULE_ITEM_TITLE, s.getTitle())
                    .build();
            scheduleItemArrayBuilder.add(scheduleItem);
        }
        JsonArray scheduleItemsArray = scheduleItemArrayBuilder.build();
        
        // Saving the ScheduleItem JSON
        JsonObject scheduleItemJSO = Json.createObjectBuilder()
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> propertiesS = new HashMap<>(1);
	propertiesS.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactoryS = Json.createWriterFactory(properties);
	StringWriter swS = new StringWriter();
	JsonWriter jsonWriterS = writerFactoryS.createWriter(swS);
	jsonWriterS.writeObject(scheduleItemJSO);
	jsonWriterS.close();

	// INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
	OutputStream osS = new FileOutputStream(scheduleBuilderDirectory);
	JsonWriter jsonFileWriterS = Json.createWriter(osS);
	jsonFileWriterS.writeObject(scheduleItemJSO);
	String prettyPrintedS = sw.toString();
	PrintWriter pwS = new PrintWriter(scheduleBuilderDirectory);
	pwS.write(prettyPrintedS);
	pwS.close();
        
        // ProjectTeams JSON
        // And now the ProjectTeam JSON Objects
        JsonArrayBuilder projectTeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeam> projectTeams = dataManager.getProjectTeams();
        for (ProjectTeam p: projectTeams) {
            JsonObject projectTeamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME, p.getName())
                    .add(JSON_PROJECT_TEAM_RED_COLOR, p.getColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_GREEN_COLOR, p.getColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_BLUE_COLOR, p.getColor().getBlue() + "")
                    //.add(JSON_PROJECT_TEAM_COLOR_OPACITY, p.getColor().getTransparency())
                    .add(JSON_PROJECT_TEAM_TEXT_RED_COLOR, p.getTextColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_GREEN_COLOR, p.getTextColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_BLUE_COLOR, p.getTextColor().getBlue() + "")
                    .add(JSON_PROJECT_TEAM_LINK, p.getLink()).build();
            projectTeamArrayBuilder.add(projectTeamJson);
        }
        JsonArray projectTeamsArray = projectTeamArrayBuilder.build();
        
        // Saving the ProjectTeam JSON
        JsonObject projectTeamJSO = Json.createObjectBuilder()
                .add(JSON_PROJECT_TEAMS, projectTeamsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> propertiesPT = new HashMap<>(1);
	propertiesPT.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactoryPT = Json.createWriterFactory(propertiesPT);
	StringWriter swPT = new StringWriter();
	JsonWriter jsonWriterPT = writerFactoryPT.createWriter(swPT);
	jsonWriterPT.writeObject(projectTeamJSO);
	jsonWriterPT.close();

	// INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
	OutputStream osPT = new FileOutputStream(projectTeamsBuilderDirectory);
	JsonWriter jsonFileWriterPT = Json.createWriter(osPT);
	jsonFileWriterPT.writeObject(projectTeamJSO);
	String prettyPrintedPT = swPT.toString();
	PrintWriter pwPT = new PrintWriter(projectTeamsBuilderDirectory);
	pwPT.write(prettyPrintedPT);
	pwPT.close();
        
        // Students JSON Array - Constructing
        // And finally the Student JSON Objects (pertaining to the teams)
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for (Student s: students) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_FIRST_NAME, s.getFirstName())
                    .add(JSON_STUDENT_LAST_NAME, s.getLastName())
                    .add(JSON_STUDENT_TEAM, s.getTeamName())
                    .add(JSON_STUDENT_ROLE, s.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentsArray = studentArrayBuilder.build();
        
        // Saving the Students JSON
        JsonObject studentsJSO = Json.createObjectBuilder()
                .add(JSON_STUDENTS, studentsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> propertiesST = new HashMap<>(1);
	propertiesST.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactoryST = Json.createWriterFactory(propertiesST);
	StringWriter swST = new StringWriter();
	JsonWriter jsonWriterST = writerFactoryST.createWriter(swST);
	jsonWriterST.writeObject(studentsJSO);
	jsonWriterST.close();

	// INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
	OutputStream osST = new FileOutputStream(filePath);
	JsonWriter jsonFileWriterST = Json.createWriter(osST);
	jsonFileWriterST.writeObject(projectTeamJSO);
	String prettyPrintedST = swST.toString();
	PrintWriter pwST = new PrintWriter(filePath);
	pwST.write(prettyPrintedST);
	pwST.close();
    }
}

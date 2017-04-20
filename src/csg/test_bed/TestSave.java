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
import djf.settings.AppStartupConstants;
import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
//import static javafx.application.Application.launch;
//import javafx.stage.Stage;

/**
 *
 * @author dsli
 */
public class TestSave {
    
    CSGData dataComponent;
    CSGFiles fileComponent;

    public TestSave() {
        CourseSiteGeneratorApp app = new CourseSiteGeneratorApp();
        dataComponent = (CSGData)app.getDataComponent();
        fileComponent = (CSGFiles)app.getFileComponent();
    }
    
    public void testSaveData() {
        TeachingAssistant jacobEvans = new TeachingAssistant("Jacob", "Evans", true);
        TeachingAssistant jamesHoffman = new TeachingAssistant("James", "Hoffman", true);
        dataComponent.getRecitations().add(new Recitation("R01", "McKenna", "Monday 5:30 - 6:23 PM", "Old CS 2120", jacobEvans, jamesHoffman));
        GregorianCalendar g = new GregorianCalendar(2017, 2, 17);
        Date testDate = g.getTime();
        dataComponent.getScheduleItems().add(new ScheduleItem("Holiday", testDate, "Snow Day", ""));
        dataComponent.getScheduleItems().add(new ScheduleItem("Lecture", testDate, "Lecture 3", "Event Programming"));
        Color c1 = Color.BLACK;
        Color c2 = Color.BLUE;
        ProjectTeam sampleProjectTeam = new ProjectTeam("Team 1", c1, c2, "");
        dataComponent.getProjectTeams().add(sampleProjectTeam);
        dataComponent.getStudents().add(new Student("David", "Li", sampleProjectTeam, "Lead Developer"));
        
        // Save the hard coded data from above
        try {
        fileComponent.saveData(dataComponent, AppStartupConstants.PATH_WORK); // What is filePath?
        }
        catch (IOException e) {
            System.out.println("Bad file saving.");
        }
    }

    /*static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_IS_UNDERGRAD_TA = "is_undergrad_ta";
    static final String JSON_EMAIL = "email";
    // Below, NEW STRING CONSTANTS TO BE ADDED TO THE REAL SAVEDATA() METHOD
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
    static final String JSON_PROJECT_TEAM_COLOR = "project_team_color";
    static final String JSON_PROJECT_TEAM_TEXT_COLOR = "project_team_text_color";
    static final String JSON_PROJECT_TEAM_LINK = "project_team_link";
    static final String JSON_STUDENT_FIRST_NAME = "first_name";
    static final String JSON_STUDENT_LAST_NAME = "last_name";
    static final String JSON_STUDENT_TEAM = "team";
    static final String JSON_STUDENT_ROLE = "role";
    

    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	CSGData dataManager = (CSGData)data;
        
        // Under construction: Building Course Object to Save
        //The Course Information
        
        //The Site Pages
        
        //The Images and Styling

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_IS_UNDERGRAD_TA, ta.isUndergrad()).build();
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
        
        //Now build the Recitation JSON Objects to save
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
        
        // And now the ProjectTeam JSON Objects
        JsonArrayBuilder projectTeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeam> projectTeams = dataManager.getProjectTeams();
        for (ProjectTeam p: projectTeams) {
            JsonObject projectTeamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME, p.getName())
                    .add(JSON_PROJECT_TEAM_COLOR, p.getColor())
                    .add(JSON_PROJECT_TEAM_TEXT_COLOR, p.getTextColor())
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
    }*/
}

class TestSaveDriver {
    public static void main(String[] args) {
        TestSave t = new TestSave();
        
        t.testSaveData();
    }
}

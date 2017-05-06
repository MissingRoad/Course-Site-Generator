/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGProp;
import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.CourseSite;
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
import javafx.scene.paint.Color;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 *
 * @author dsli
 */
public class CSGFiles implements AppFileComponent {

    CourseSiteGeneratorApp app;

    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_COURSE_SUBJECT = "course_subject";
    static final String JSON_COURSE_NUMBER = "course_number";
    static final String JSON_COURSE_SEMESTER = "course_semester";
    static final String JSON_COURSE_YEAR = "course_year";
    static final String JSON_COURSE_TITLE = "course_title";
    static final String JSON_INSTRUCTOR_NAME = "course_instructor_name";
    static final String JSON_INSTRUCTOR_HOME = "course_instructor_home";
    static final String JSON_EXPORT_DIR = "course_export_dir";
    static final String JSON_HAS_HOME = "course_has_home";
    static final String JSON_HAS_SYLLABUS = "course_has_syllabus";
    static final String JSON_HAS_SCHEDULE = "course_has_schedule";
    static final String JSON_HAS_HW = "course_has_hw";
    static final String JSON_HAS_PROJECT = "course_has_project";
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
    static final String JSON_SCHEDULE_ITEM_TIME = "schedule_item_time";
    static final String JSON_SCHEDULE_ITEM_TITLE = "schedule_item_title";
    static final String JSON_SCHEDULE_ITEM_TOPIC = "schedule_item_topic";
    static final String JSON_SCHEDULE_ITEM_LINK = "schedule_item_link";
    static final String JSON_SCHEDULE_ITEM_CRITERIA = "schedule_item_criteria";
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
    static final String JSON_COURSE_INFO = "course_info";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    static final String JSON_PROJECT_TEAMS = "project_teams";
    static final String JSON_STUDENTS = "students";

    // CONSTANTS FOR EXPORTING
    //Start and end hour
    static final String JSON_START_HOUR_EXPORT = "startHour";
    static final String JSON_END_HOUR_EXPORT = "endHour";
    // Course Details - FIX
    static final String JSON_COURSE_SUBJECT_EXPORT = "course_subject";
    static final String JSON_COURSE_NUMBER_EXPORT = "course_number";
    static final String JSON_COURSE_SEMESTER_EXPORT = "course_semester";
    static final String JSON_COURSE_YEAR_EXPORT = "course_year";
    static final String JSON_COURSE_TITLE_EXPORT = "course_title";
    static final String JSON_INSTRUCTOR_NAME_EXPORT = "course_instructor_name";
    static final String JSON_INSTRUCTOR_HOME_EXPORT = "course_instructor_home";
    static final String JSON_EXPORT_DIR_EXPORT = "course_export_dir";
    static final String JSON_HAS_HOME_EXPORT = "course_has_home";
    static final String JSON_HAS_SYLLABUS_EXPORT = "course_has_syllabus";
    static final String JSON_HAS_SCHEDULE_EXPORT = "course_has_schedule";
    static final String JSON_HAS_HW_EXPORT = "course_has_hw";
    static final String JSON_HAS_PROJECT_EXPORT = "course_has_project";
    // ScheduleBuilder.json
    static final String JSON_OFFICE_HOURS_EXPORT = "officeHours";
    static final String JSON_DAY_EXPORT = "day";
    static final String JSON_MONTH_EXPORT = "month";
    static final String JSON_LINK_EXPORT = "link";
    static final String JSON_TIME_EXPORT = "time";
    static final String JSON_NAME_EXPORT = "name";
    static final String JSON_TITLE_EXPORT = "title";
    static final String JSON_TOPIC_EXPORT = "topic";
    // OfficeHoursGridData.json
    static final String JSON_UNDERGRAD_TAS_EXPORT = "undergrad_tas"; // FOR ALL TA's, NOT JUST UNDERGRAD
    static final String JSON_IS_UNDERGRAD_TA_EXPORT = "is_undergrad_ta";
    static final String JSON_EMAIL_EXPORT = "email";
    static final String JSON_RECITATION_SECTION_EXPORT = "section";
    static final String JSON_RECITATION_INSTRUCTOR_EXPORT = "instructor";
    static final String JSON_RECITATION_DAY_TIME_EXPORT = "day_time";
    static final String JSON_RECITATION_LOCATION_EXPORT = "location";
    static final String JSON_RECITATION_TA1_EXPORT = "ta_1";
    static final String JSON_RECITATION_TA2_EXPORT = "ta_2";
    static final String JSON_START_DATE_EXPORT = "startDate";
    static final String JSON_END_DATE_EXPORT = "endDate";
    static final String JSON_SCHEDULE_TYPE_EXPORT = "type";
    static final String JSON_SCHEDULE_ITEM_DATE_EXPORT = "schedule_item_date";
    static final String JSON_STARTING_MONDAY_MONTH = "startingMondayMonth";
    static final String JSON_STARTING_MONDAY_DAY = "startingMondayDay";
    static final String JSON_ENDING_FRIDAY_MONTH = "endingFridayMonth";
    static final String JSON_ENDING_FRIDAY_DAY = "endingFridayDay";
    static final String JSON_SCHEDULE_ITEM_MONTH_EXPORT = "month";
    static final String JSON_SCHEDULE_ITEM_DAY_EXPORT = "day";
    static final String JSON_SCHEDULE_ITEM_TIME_EXPORT = "time";
    static final String JSON_SCHEDULE_ITEM_TITLE_EXPORT = "title";
    static final String JSON_SCHEDULE_ITEM_LINK_EXPORT = "link";
    static final String JSON_SCHEDULE_ITEM_TOPIC_EXPORT = "schedule_item_topic";
    static final String JSON_PROJECT_TEAM_NAME_EXPORT = "name";
    static final String JSON_PROJECT_TEAM_RED_COLOR_EXPORT = "red";
    static final String JSON_PROJECT_TEAM_GREEN_COLOR_EXPORT = "green";
    static final String JSON_PROJECT_TEAM_BLUE_COLOR_EXPORT = "blue";
    static final String JSON_PROJECT_TEAM_COLOR_OPACITY_EXPORT = "project_team_color_opacity";
    static final String JSON_PROJECT_TEAM_TEXT_COLOR_EXPORT = "text_color";
    static final String JSON_PROJECT_TEAM_TEXT_RED_COLOR_EXPORT = "project_team_text_red_color";
    static final String JSON_PROJECT_TEAM_TEXT_GREEN_COLOR_EXPORT = "project_team_text_green_color";
    static final String JSON_PROJECT_TEAM_TEXT_BLUE_COLOR_EXPORT = "project_team_text_blue_color";
    static final String JSON_PROJECT_TEAM_TEXT_COLOR_OPACITY_EXPORT = "project_team_text_color_opacity";
    static final String JSON_PROJECT_TEAM_LINK_EXPORT = "project_team_link";
    static final String JSON_STUDENT_LAST_NAME_EXPORT = "lastName";
    static final String JSON_STUDENT_FIRST_NAME_EXPORT = "firstName";
    static final String JSON_STUDENT_TEAM_EXPORT = "team";
    static final String JSON_STUDENT_ROLE_EXPORT = "role";
    static final String JSON_BANNER_IMAGE_EXPORT = "banner_image";
    static final String JSON_LEFT_FOOTER_IMAGE_EXPORT = "left_footer";
    static final String JSON_RIGHT_FOOTER_IMAGE_EXPORT = "right_footer";
    // Data types regarding various types for the various JSON arrays
    static final String JSON_COURSE_INFO_EXPORT = "course_info";
    static final String JSON_RECITATIONS_EXPORT = "recitations";
    static final String JSON_HOLIDAYS_EXPORT = "holidays";
    static final String JSON_LECTURES_EXPORT = "lectures";
    static final String JSON_REFERENCES_EXPORT = "references";
    static final String JSON_HOMEWORKS_EXPORT = "hws";
    static final String JSON_PROJECT_TEAMS_EXPORT = "project_teams";
    static final String JSON_STUDENTS_EXPORT = "students";

    public CSGFiles(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException, ParseException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;

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
            String time = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TIME);
            String title = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_LINK);
            String criteria = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_CRITERIA);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz YYYY");
            Date scheduleItemDate = sdf.parse(date);
            dataManager.addScheduleItem(type, scheduleItemDate, time, title, topic, link, criteria);
        }

        //Project Teams
        JsonArray projectTeamArray = json.getJsonArray(JSON_PROJECT_TEAMS);
        for (int i = 0; i < projectTeamArray.size(); i++) {
            JsonObject jsonProjectTeamItem = projectTeamArray.getJsonObject(i);
            String name = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_NAME);
            String teamColorRed = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_RED_COLOR);
            String teamColorGreen = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_GREEN_COLOR);
            String teamColorBlue = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_BLUE_COLOR);
            String teamColorOpacity = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_COLOR_OPACITY);
            String textColorRed = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_RED_COLOR);
            String textColorGreen = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_GREEN_COLOR);
            String textColorBlue = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_BLUE_COLOR);
            String textColorOpacity = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_TEXT_COLOR_OPACITY);
            String link = jsonProjectTeamItem.getString(JSON_PROJECT_TEAM_LINK);
            double teamColorRedVal = Double.parseDouble(teamColorRed);
            double teamColorGreenVal = Double.parseDouble(teamColorGreen);
            double teamColorBlueVal = Double.parseDouble(teamColorBlue);
            double teamColorOpacityVal = Double.parseDouble(teamColorOpacity);
            double textColorRedVal = Double.parseDouble(textColorRed);
            double textColorGreenVal = Double.parseDouble(textColorGreen);
            double textColorBlueVal = Double.parseDouble(textColorBlue);
            double textColorOpacityVal = Double.parseDouble(textColorOpacity);
            Color teamColor = new Color(teamColorRedVal, teamColorGreenVal, teamColorBlueVal, teamColorOpacityVal);
            Color textColor = new Color(textColorRedVal, textColorGreenVal, textColorBlueVal, textColorOpacityVal);
            dataManager.addProjectTeam(name, teamColor, textColor, link);
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
            dataManager.addStudent(s);
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
        CSGData dataManager = (CSGData) data;

        // Build a JsonObject - The CourseSite Information Object
        CourseSite cs = dataManager.getCourseSiteInfo();
        JsonObject csInfo = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, cs.getCourseSubject())
                .add(JSON_COURSE_NUMBER, "" + cs.getCourseNumber())
                .add(JSON_COURSE_SEMESTER, cs.getCourseSemester())
                .add(JSON_COURSE_YEAR, cs.getCourseYear() + "")
                .add(JSON_COURSE_TITLE, cs.getCourseTitle())
                .add(JSON_INSTRUCTOR_NAME, cs.getInstName())
                .add(JSON_INSTRUCTOR_HOME, cs.getInstHome())
                .add(JSON_EXPORT_DIR, cs.getExportDir())
                // FIX THESE LINES
                .add(JSON_HAS_HOME, cs.getHomePage().getExists() + "")
                .add(JSON_HAS_SYLLABUS, cs.getSyllabusPage().getExists() + "")
                .add(JSON_HAS_SCHEDULE, cs.getSchedulePage().getExists() + "")
                .add(JSON_HAS_HW, cs.getHwPage().getExists() + "")
                .add(JSON_HAS_PROJECT, cs.getProjectsPage().getExists() + "").build();

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
        for (Recitation r : recitations) {
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
        for (ScheduleItem s : scheduleItems) {
            JsonObject scheduleItem = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_TYPE, s.getType())
                    .add(JSON_SCHEDULE_ITEM_DATE, s.getDate().toString())
                    .add(JSON_SCHEDULE_ITEM_TIME, s.getTime())
                    .add(JSON_SCHEDULE_ITEM_TITLE, s.getTitle())
                    .add(JSON_SCHEDULE_ITEM_TOPIC, s.getTopic())
                    .add(JSON_SCHEDULE_ITEM_LINK, s.getLink())
                    .add(JSON_SCHEDULE_ITEM_CRITERIA, s.getCriteria())
                    .build();
            scheduleItemArrayBuilder.add(scheduleItem);
        }
        JsonArray scheduleItemsArray = scheduleItemArrayBuilder.build();

        // And now the ProjectTeam JSON Objects
        JsonArrayBuilder projectTeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeam> projectTeams = dataManager.getProjectTeams();
        for (ProjectTeam p : projectTeams) {
            JsonObject projectTeamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME, p.getName())
                    .add(JSON_PROJECT_TEAM_RED_COLOR, p.getColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_GREEN_COLOR, p.getColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_BLUE_COLOR, p.getColor().getBlue() + "")
                    .add(JSON_PROJECT_TEAM_COLOR_OPACITY, p.getColor().getOpacity() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_RED_COLOR, p.getTextColor().getRed() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_GREEN_COLOR, p.getTextColor().getGreen() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_BLUE_COLOR, p.getTextColor().getBlue() + "")
                    .add(JSON_PROJECT_TEAM_TEXT_COLOR_OPACITY, p.getTextColor().getOpacity() + "")
                    .add(JSON_PROJECT_TEAM_LINK, p.getLink()).build();
            projectTeamArrayBuilder.add(projectTeamJson);
        }
        JsonArray projectTeamsArray = projectTeamArrayBuilder.build();

        // And finally the Student JSON Objects (pertaining to the teams)
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for (Student s : students) {
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
                .add(JSON_COURSE_INFO, csInfo)
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
        CSGData dataManager = (CSGData) data;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        File fileToImport = new File(PATH_PUBLIC_HTML);

        File fileToExport = new File(filePath + "/newExport.html");

        //What would be the method for copying the file?
        //fileToImport.renameTo(fileToExport);
        //FileUtils.copyFile(fileToImport, fileToExport);
        FileUtils.copyDirectory(fileToImport, fileToExport);

        String courseInfoDirectory = filePath + "/newExport.html/" + "js/CourseInformationData.json";
        String officeHoursGridDataDirectory = filePath + "/newExport.html/" + "js/OfficeHoursGridData.json";
        String recitationsBuilderDirectory = filePath + "/newExport.html/" + "js/RecitationsData.json";
        String scheduleBuilderDirectory = filePath + "/newExport.html/" + "js/ScheduleData.json";
        String projectTeamsBuilderDirectory = filePath + "/newExport.html/" + "js/TeamsAndStudents.json";

        // Building Course Information to Save
        CourseSite cs = dataManager.getCourseSiteInfo();
        JsonObject csInfo = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, cs.getCourseSubject())
                .add(JSON_COURSE_NUMBER, "" + cs.getCourseNumber())
                .add(JSON_COURSE_SEMESTER, cs.getCourseSemester())
                .add(JSON_COURSE_YEAR, cs.getCourseYear() + "")
                .add(JSON_COURSE_TITLE, cs.getCourseTitle())
                .add(JSON_INSTRUCTOR_NAME, cs.getInstName())
                .add(JSON_INSTRUCTOR_HOME, cs.getInstHome())
                .add(JSON_EXPORT_DIR, cs.getExportDir())
                
                .build();
        
        Map<String, Object> propertiesCS = new HashMap<>(1);
        propertiesCS.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactoryCS = Json.createWriterFactory(propertiesCS);
        StringWriter swCS = new StringWriter();
        JsonWriter jsonWriterCS = writerFactoryCS.createWriter(swCS);
        jsonWriterCS.writeObject(csInfo);
        jsonWriterCS.close();

        // INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
        OutputStream osCS = new FileOutputStream(courseInfoDirectory);
        JsonWriter jsonFileWriterCS = Json.createWriter(osCS);
        jsonFileWriterCS.writeObject(csInfo);
        String prettyPrintedCS = swCS.toString();
        PrintWriter pwCS = new PrintWriter(courseInfoDirectory);
        pwCS.write(prettyPrintedCS);
        pwCS.close();
        
        // Building TA Array to Save
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            if (ta.isUndergrad()) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME_EXPORT, ta.getName())
                    .add(JSON_EMAIL_EXPORT, ta.getEmail())
                    .build(); // What will this line print for a String?
            taArrayBuilder.add(taJson);
            }
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY_EXPORT, ts.getDay())
                    .add(JSON_TIME_EXPORT, ts.getTime())
                    .add(JSON_NAME_EXPORT, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }

        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        JsonObject taDataJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR_EXPORT, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR_EXPORT, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS_EXPORT, undergradTAsArray)
                .add(JSON_OFFICE_HOURS_EXPORT, timeSlotsArray).build();

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
        for (Recitation r : recitations) {
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION_EXPORT, r.getSection())
                    .add(JSON_RECITATION_DAY_TIME_EXPORT, r.getDayTime())
                    .add(JSON_RECITATION_LOCATION_EXPORT, r.getLocation())
                    .add(JSON_RECITATION_TA1_EXPORT, r.getSupervisingTA1().getName())
                    .add(JSON_RECITATION_TA2_EXPORT, r.getSupervisingTA2().getName()).build();
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
        String prettyPrintedR = swR.toString();
        PrintWriter pwR = new PrintWriter(recitationsBuilderDirectory);
        pwR.write(prettyPrintedR);
        pwR.close();

        // Get the start and ending Dates for Schedule
        Date startMondayDate = dataManager.getStartDate();
        Date endFridayDate = dataManager.getEndDate();
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startMondayDate);
        cal2.setTime(endFridayDate);
        
        int startMonth = cal1.get(Calendar.MONTH);
        int startDay = cal1.get(Calendar.DAY_OF_MONTH);
        int endMonth = cal2.get(Calendar.MONTH);
        int endDay = cal2.get(Calendar.DAY_OF_MONTH);
        // Now the Schedule Item Objects
        
        // Starting with the Holidays
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItems();
        for (ScheduleItem s : scheduleItems) {
            if (s.getType().equalsIgnoreCase(props.getProperty(CSGProp.HOLIDAY_EVENT.toString()))) {
                Date scheduleItemDate = s.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(scheduleItemDate);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                JsonObject scheduleItem = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_ITEM_MONTH_EXPORT, month + "")
                        .add(JSON_SCHEDULE_ITEM_DAY_EXPORT, day + "")
                        .add(JSON_SCHEDULE_ITEM_TITLE_EXPORT, s.getTitle())
                        .add(JSON_SCHEDULE_ITEM_LINK_EXPORT, s.getLink())
                        .build();
                holidayArrayBuilder.add(scheduleItem);
            }
        }
        JsonArray holidayItemsArray = holidayArrayBuilder.build();
        
        // Now the lectures
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        
        for (ScheduleItem s : scheduleItems) {
            if (s.getType().equalsIgnoreCase(CSGProp.LECTURE_EVENT.toString())) {
                Date scheduleItemDate = s.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(scheduleItemDate);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                JsonObject scheduleItem = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_ITEM_MONTH_EXPORT, month + "")
                        .add(JSON_SCHEDULE_ITEM_DAY_EXPORT, day + "")
                        .add(JSON_SCHEDULE_ITEM_TITLE_EXPORT, s.getTitle())
                        .add(JSON_SCHEDULE_ITEM_TOPIC_EXPORT, s.getTopic())
                        .add(JSON_SCHEDULE_ITEM_LINK_EXPORT, s.getLink())
                        .build();
                lectureArrayBuilder.add(scheduleItem);
            }
        }
        JsonArray lectureItemsArray = lectureArrayBuilder.build();
        
        // Now the REFERENCE ITEMS
        JsonArrayBuilder referenceArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem s : scheduleItems) {
            if (s.getType().equalsIgnoreCase(CSGProp.REFERENCE_EVENT.toString())) {
                Date scheduleItemDate = s.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(scheduleItemDate);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                JsonObject scheduleItem = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_ITEM_MONTH_EXPORT, month + "")
                        .add(JSON_SCHEDULE_ITEM_DAY_EXPORT, day + "")
                        .add(JSON_SCHEDULE_ITEM_TITLE_EXPORT, s.getTitle())
                        .add(JSON_SCHEDULE_ITEM_TOPIC_EXPORT, s.getTopic())
                        .add(JSON_SCHEDULE_ITEM_LINK_EXPORT, s.getLink())
                        .build();
                referenceArrayBuilder.add(scheduleItem);
            }
        }
        JsonArray referenceItemsArray = referenceArrayBuilder.build();
        
        // Now the RECITATION Items
        
        JsonArrayBuilder recitationItemsArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem s : scheduleItems) {
            if (s.getType().equalsIgnoreCase(CSGProp.RECITATION_EVENT.toString())) {
                Date scheduleItemDate = s.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(scheduleItemDate);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                JsonObject scheduleItem = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_ITEM_MONTH_EXPORT, month + "")
                        .add(JSON_SCHEDULE_ITEM_DAY_EXPORT, day + "")
                        .add(JSON_SCHEDULE_ITEM_TITLE_EXPORT, s.getTitle())
                        .add(JSON_SCHEDULE_ITEM_TOPIC_EXPORT, s.getTopic())
                        
                        .build();
                recitationItemsArrayBuilder.add(scheduleItem);
            }
        }
        JsonArray recitationItemsArray = recitationItemsArrayBuilder.build();
        
        // Now the HOMEWORK items
        JsonArrayBuilder homeworkArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem s : scheduleItems) {
            if (s.getType().equalsIgnoreCase(CSGProp.HOMEWORK_EVENT.toString())) {
                Date scheduleItemDate = s.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(scheduleItemDate);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                JsonObject scheduleItem = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_ITEM_MONTH_EXPORT, month + "")
                        .add(JSON_SCHEDULE_ITEM_DAY_EXPORT, day + "")
                        .add(JSON_SCHEDULE_ITEM_TITLE_EXPORT, s.getTitle())
                        .add(JSON_SCHEDULE_ITEM_TOPIC_EXPORT, s.getTopic())
                        .add(JSON_SCHEDULE_ITEM_TIME_EXPORT, s.getTime())
                        .add(JSON_SCHEDULE_ITEM_LINK_EXPORT, s.getLink())
                        .build();
                homeworkArrayBuilder.add(scheduleItem);
            }
        }
        JsonArray homeworkItemsArray = referenceArrayBuilder.build();
        
        // Saving the ScheduleItem JSON
        JsonObject scheduleItemJSO = Json.createObjectBuilder()
                .add(JSON_STARTING_MONDAY_MONTH, startMonth + "")
                .add(JSON_STARTING_MONDAY_DAY, startDay + "")
                .add(JSON_ENDING_FRIDAY_MONTH, endMonth + "")
                .add(JSON_ENDING_FRIDAY_DAY, endDay + "")
                .add(JSON_HOLIDAYS_EXPORT, holidayItemsArray)
                .add(JSON_LECTURES_EXPORT, lectureItemsArray)
                .add(JSON_REFERENCES_EXPORT, referenceItemsArray)
                .add(JSON_RECITATIONS_EXPORT, recitationItemsArray)
                .add(JSON_HOMEWORKS_EXPORT, homeworkItemsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> propertiesS = new HashMap<>(1);
        propertiesS.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactoryS = Json.createWriterFactory(propertiesS);
        StringWriter swS = new StringWriter();
        JsonWriter jsonWriterS = writerFactoryS.createWriter(swS);
        jsonWriterS.writeObject(scheduleItemJSO);
        jsonWriterS.close();

        // INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
        OutputStream osS = new FileOutputStream(scheduleBuilderDirectory);
        JsonWriter jsonFileWriterS = Json.createWriter(osS);
        jsonFileWriterS.writeObject(scheduleItemJSO);
        String prettyPrintedS = swS.toString();
        PrintWriter pwS = new PrintWriter(scheduleBuilderDirectory);
        pwS.write(prettyPrintedS);
        pwS.close();

        // ProjectTeams JSON
        // And now the ProjectTeam JSON Objects
        JsonArrayBuilder projectTeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeam> projectTeams = dataManager.getProjectTeams();
        for (ProjectTeam p : projectTeams) {
            int newRed = (int)(p.getColor().getRed() * 256);
            int newGreen = (int)(p.getColor().getGreen() * 256);
            int newBlue = (int)(p.getColor().getBlue() * 256);
            String textColor = p.getTextColor().toString();
            JsonObject projectTeamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME_EXPORT, p.getName())
                    .add(JSON_PROJECT_TEAM_RED_COLOR_EXPORT, newRed + "")
                    .add(JSON_PROJECT_TEAM_GREEN_COLOR_EXPORT, newGreen + "")
                    .add(JSON_PROJECT_TEAM_BLUE_COLOR_EXPORT, newBlue + "")
                    //.add(JSON_PROJECT_TEAM_COLOR_OPACITY, p.getColor().getTransparency())
                    .add(JSON_PROJECT_TEAM_TEXT_COLOR_EXPORT, textColor)
                    .build();
            projectTeamArrayBuilder.add(projectTeamJson);
        }
        JsonArray projectTeamsArray = projectTeamArrayBuilder.build();
        
        // Students Array
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for (Student s: students) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_LAST_NAME_EXPORT, s.getLastName())
                    .add(JSON_STUDENT_FIRST_NAME_EXPORT, s.getFirstName())
                    .add(JSON_STUDENT_TEAM_EXPORT, s.getTeamName())
                    .add(JSON_STUDENT_ROLE_EXPORT, s.getRole())
                    .build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentArray = studentArrayBuilder.build();
        
        // Saving the ProjectTeam JSON
        JsonObject projectTeamJSO = Json.createObjectBuilder()
                .add(JSON_PROJECT_TEAMS, projectTeamsArray)
                .add(JSON_STUDENTS, studentArray).build();

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
        /*JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
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
	pwST.close();*/
    }
}

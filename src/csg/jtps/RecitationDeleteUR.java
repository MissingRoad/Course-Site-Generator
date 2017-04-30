/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.TeachingAssistant;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class RecitationDeleteUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private TeachingAssistant supervisingta1;
    private TeachingAssistant supervisingta2;
    
    public RecitationDeleteUR(CourseSiteGeneratorApp app, String section, String instructor) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        Recitation r = data.findRecitation(section, instructor);
        this.section = section;
        this.instructor = instructor;
        this.dayTime = r.getDayTime();
        this.location = r.getLocation();
        this.supervisingta1 = r.getSupervisingTA1();
        this.supervisingta2 = r.getSupervisingTA2();
    }
    
    @Override
    public void doTransaction() {
        data.removeRecitation(section, instructor);
    }
    
    @Override
    public void undoTransaction() {
        data.addRecitation(section, instructor, dayTime, location, supervisingta1, supervisingta2);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.data.ScheduleItem;
import java.util.Date;
import jtps.jTPS_Transaction;

/**
 *
 * @author dsli
 */
public class ScheduleItemDeleteUR implements jTPS_Transaction {
    private CourseSiteGeneratorApp app;
    private CSGData data;
    
    private String type;
    private Date date;
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    
    public ScheduleItemDeleteUR(CourseSiteGeneratorApp app, ScheduleItem s) {
        this.app = app;
        this.data = (CSGData)app.getDataComponent();
        
        this.type = s.getType();
        this.date = s.getDate();
        this.time = s.getTime();
        this.title = s.getTitle();
        this.topic = s.getTopic();
        this.link = s.getLink();
        this.criteria = s.getCriteria();
    }
    
    @Override
    public void doTransaction() {
        data.removeScheduleItem(title, date);
    }
    
    @Override
    public void undoTransaction() {
        data.addScheduleItem(type, date, time, title, topic, link, criteria);
    }
}

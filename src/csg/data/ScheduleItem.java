/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dsli
 */
public class ScheduleItem implements Comparable {
    private final StringProperty type;
    private Date date;
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    private final StringProperty criteria;
    
    public ScheduleItem(String type, Date date, String time, String title, String topic, String link, String criteria) {
        this.type = new SimpleStringProperty(type);
        this.date = date;
        this.time = new SimpleStringProperty(time);
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = new SimpleStringProperty(link);
        this.criteria = new SimpleStringProperty(criteria);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getType() {
        return type.get();
    }
    
    public void setType(String newType) {
        this.type.set(newType);
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public void setTitle(String newTitle) {
        this.title.set(newTitle);
    }
    
    public String getTopic() {
        return topic.get();
    }
    
    public void setTopic(String newTopic) {
        this.topic.set(newTopic);
    }
    
    public String getTime() {
        return time.get();
    }
    
    public void setTime(String newTimeString) {
        this.time.set(newTimeString);
    }
    
    public String getLink() {
        return link.get();
    }
    
    public void setLink(String newLink) {
        this.link.set(newLink);
    }
    
    public String getCriteria() {
        return criteria.get();
    }
    
    public void setCriteria(String newCriteria) {
        this.criteria.set(newCriteria);
    }
    
    //Implement compare(Object o) and toString() eventually
    @Override
    public int compareTo(Object o) {
        // o should be a DATE parameter, to compare with DatePicker "values" (converted to Date Objects)
        Date datePickerDate = (Date)o;
        int i = this.date.compareTo(date);
        return i;
    }
}

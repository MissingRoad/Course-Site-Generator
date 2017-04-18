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
public class ScheduleItem {
    private final StringProperty type;
    private Date date;
    private final StringProperty title;
    private final StringProperty topic;
    
    public ScheduleItem(String type, Date date, String title, String topic) {
        this.type = new SimpleStringProperty(type);
        this.date = date;
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
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
    
    //Implement compare(Object o) and toString() eventually
}

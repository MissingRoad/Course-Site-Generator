/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.workspace.CSGWorkspace;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.TeachingAssistant;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author zhaotingyi
 */

// MAKE SURE TO ADD THE EXTRA FUNCTIONALITY FOR THE OTHER TABS
public class TAdeletUR implements jTPS_Transaction{
    
    private CourseSiteGeneratorApp app;
    private CSGData data;
    private ArrayList<StringProperty> cellProps = new ArrayList<StringProperty>();
    private String TAname;
    private String TAemail;
    private Recitation recitation;
    private TeachingAssistant emptyTA;
    
    public TAdeletUR(CourseSiteGeneratorApp app, String TAname){
        this.app = app;
        data = (CSGData)app.getDataComponent();
        this.TAname = TAname;
        TAemail = data.getTA(TAname).getEmail();
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        HashMap<String, Label> labels = workspace.getTADataOfficeHoursGridTACellLabels();
        this.emptyTA = new TeachingAssistant("", "", false);
        for (Label label : labels.values()) {
            if (label.getText().equals(TAname)
            || (label.getText().contains(TAname + "\n"))
            || (label.getText().contains("\n" + TAname))) {
                cellProps.add(label.textProperty());
            }
        }
        ObservableList<Recitation> recitations = data.getRecitations();
        this.recitation = null;
        for (Recitation r: recitations) {
            if (r.getSupervisingTA1().getName().equals(TAname)) {
                recitation = r;
            }
            if (r.getSupervisingTA2().getName().equals(TAname)) {
                recitation = r;
            }
        }
    }

    @Override
    public void doTransaction() {
        data.removeTA(TAname);
        for(StringProperty cellProp : cellProps){
            data.removeTAFromCell(cellProp, TAname);
        }
        if (recitation != null) {
            if (recitation.getSupervisingTA1().getName().equals(TAname)) {
                recitation.setSupervisingTA1(emptyTA);
            }
            else if (recitation.getSupervisingTA2().getName().equals(TAname)) {
                recitation.setSupervisingTA2(emptyTA);
            }
        }
        TableView recitationData = ((CSGWorkspace)app.getWorkspaceComponent()).getRecitationData();
        recitationData.refresh();
    }

    @Override
    public void undoTransaction() {
        data.addTA(TAname, TAemail, false);
        for(StringProperty cellProp : cellProps){
            String cellText = cellProp.getValue();
            if (cellText.length() == 0){
                cellProp.setValue(TAname);
            } else {
                cellProp.setValue(cellText + "\n" + TAname);}
        }
        TeachingAssistant ta = data.getTA(TAname);
        if (recitation != null) {
            if (recitation.getSupervisingTA1().getName().equals("")) {
                recitation.setSupervisingTA1(ta);
            }
            else if (recitation.getSupervisingTA2().getName().equals("")) {
                recitation.setSupervisingTA2(ta);
            }
        }
        TableView recitationData = ((CSGWorkspace)app.getWorkspaceComponent()).getRecitationData();
        recitationData.refresh();
    }
    
}

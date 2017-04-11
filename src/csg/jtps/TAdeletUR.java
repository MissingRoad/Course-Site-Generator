/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAdeletUR implements jTPS_Transaction{
    
    private TAManagerApp app;
    private TAData data;
    private ArrayList<StringProperty> cellProps = new ArrayList<StringProperty>();
    private String TAname;
    private String TAemail;
    
    public TAdeletUR(TAManagerApp app, String TAname){
        this.app = app;
        data = (TAData)app.getDataComponent();
        this.TAname = TAname;
        TAemail = data.getTA(TAname).getEmail();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
        for (Label label : labels.values()) {
            if (label.getText().equals(TAname)
            || (label.getText().contains(TAname + "\n"))
            || (label.getText().contains("\n" + TAname))) {
                cellProps.add(label.textProperty());
            }
        }
    }

    @Override
    public void doTransaction() {
        data.removeTA(TAname);
        for(StringProperty cellProp : cellProps){
            data.removeTAFromCell(cellProp, TAname);
        }
    }

    @Override
    public void undoTransaction() {
        data.addTA(TAname, TAemail);
        for(StringProperty cellProp : cellProps){
            String cellText = cellProp.getValue();
            if (cellText.length() == 0){
                cellProp.setValue(TAname);
            } else {
                cellProp.setValue(cellText + "\n" + TAname);}
        }
    }
    
}

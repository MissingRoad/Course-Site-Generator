/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CourseSiteGeneratorApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author zhaotingyi
 */
public class TAAdderUR implements jTPS_Transaction{
    
    private String TAName;
    private String TAEmail;
    private CourseSiteGeneratorApp app;
    private CSGWorkspace workspace;
    
    public TAAdderUR(CourseSiteGeneratorApp app){
        this.app = app;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
        TAName = workspace.getTANameTextField().getText();
        TAEmail = workspace.getTAEmailTextField().getText();
        
    }

    @Override
    public void doTransaction() {
        ((CSGData)app.getDataComponent()).addTA(TAName, TAEmail, true);
    }

    @Override
    public void undoTransaction() {
        ((CSGData)app.getDataComponent()).removeTA(TAName);
    }
    
}

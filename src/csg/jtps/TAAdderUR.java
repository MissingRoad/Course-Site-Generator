/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import java.util.regex.Pattern;
import jtps.jTPS_Transaction;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAAdderUR implements jTPS_Transaction{
    
    private String TAName;
    private String TAEmail;
    private TAManagerApp app;
    private TAWorkspace workspace;
    
    public TAAdderUR(TAManagerApp app){
        this.app = app;
        workspace = (TAWorkspace)app.getWorkspaceComponent();
        TAName = workspace.getNameTextField().getText();
        TAEmail = workspace.getEmailTextField().getText();
    }

    @Override
    public void doTransaction() {
        ((TAData)app.getDataComponent()).addTA(TAName, TAEmail);
    }

    @Override
    public void undoTransaction() {
        ((TAData)app.getDataComponent()).removeTA(TAName);
    }
    
}

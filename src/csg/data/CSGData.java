/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CourseSiteGeneratorApp;
import djf.components.AppDataComponent;

/**
 *
 * @author dsli
 */
public class CSGData implements AppDataComponent {
    
    CourseSiteGeneratorApp app;
    
    @Override
    public void resetData() {
        
    }
    
    public CSGData(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;
import csg.data.CSGData;
import csg.file.CSGFiles;
import csg.style.CSGStyle;
import csg.workspace.CSGWorkspace;
import java.util.Locale;
import djf.AppTemplate;
import static javafx.application.Application.launch;
/**
 *
 * @author dsli
 */
public class CourseSiteGeneratorApp extends AppTemplate {
    @Override
    public void buildAppComponentsHook() {
        dataComponent = new CSGData(this);
        workspaceComponent = new CSGWorkspace(this);
        fileComponent = new CSGFiles(this);
        styleComponent = new CSGStyle(this);
    }
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
}

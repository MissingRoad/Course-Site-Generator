/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgsandbox;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author dsli
 */
public class TestBooleanString {
    /*static boolean bool = false;
    
    public static void printBool() {
        String s = bool + "";
        System.out.println(s);
    }
    
    public static void main(String[] args) {
        printBool();
    }*/
    
    public static void main(String[] args) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        json.add("Yes");
        json.add("No");
        
        JsonArray arr = json.build();
        
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("Array", arr).build();
        
        
        System.out.println();
    }
    
}
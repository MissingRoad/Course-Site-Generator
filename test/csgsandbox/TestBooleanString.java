/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgsandbox;

/**
 *
 * @author dsli
 */
public class TestBooleanString {
    static boolean bool = false;
    
    public static void printBool() {
        String s = bool + "";
        System.out.println(s);
    }
    
    public static void main(String[] args) {
        printBool();
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgsandbox;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author dsli
 */
public class DateTestClass {
    public static void main(String[] args) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        
        c1.set(2016, 0, 5);
        c2.set(2016, 0, 10);
        
        Date startMondayDate = c2.getTime();
        Date endFridayDate = c1.getTime();
        
        int i = startMondayDate.compareTo(endFridayDate);
        System.out.println(i);
    }
}
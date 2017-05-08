/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgsandbox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author dsli
 */
public class DateTestClass {
    public static void main(String[] args) {
        /*Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        
        c1.set(2016, 0, 5);
        c2.set(2016, 0, 10);
        
        Date startMondayDate = c2.getTime();
        Date endFridayDate = c1.getTime();
        
        int i = startMondayDate.compareTo(endFridayDate);
        System.out.println(i);*/
        
        /*String dateString = "Wed Jan 18 23:22:18 EST 2017";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        try {
            Date scheduleItemDate = sdf.parse(dateString);
            System.out.println(scheduleItemDate.toString());
        }
        catch (ParseException e) {
            System.out.println("Exception");
        }*/
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("02-01-2017", formatter);
        System.out.println(localDate.toString());
    }
}

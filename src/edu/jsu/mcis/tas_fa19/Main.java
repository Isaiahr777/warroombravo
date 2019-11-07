package edu.jsu.mcis.tas_fa19;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        
        TASDatabase db = new TASDatabase();
        
        Badge b = new Badge("021890C0", "");
        
        Shift s = db.getShift(b);
        
        System.out.println("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)");
        System.out.println(s);
        
        /*GregorianCalendar ts = new GregorianCalendar();
        
        ts.set(Calendar.DAY_OF_MONTH, 17);
        ts.set(Calendar.YEAR, 2018);
        ts.set(Calendar.MONTH, 8);
        
        ts.set(Calendar.HOUR, 0);
        ts.set(Calendar.MINUTE, 0);
        ts.set(Calendar.SECOND, 0);
        
        Badge b = db.getBadge("67637925");
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts.getTimeInMillis());
        
        for (Punch p : p1) {
            System.out.println(p);
        }*/
        
    }
    
}
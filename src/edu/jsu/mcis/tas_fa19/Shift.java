
package edu.jsu.mcis.tas_fa19;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


public class Shift {
    
    private int id;
    private String description;
    private LocalTime start;
    private LocalTime stop;
    private int interval;
    private int graceperiod;
    private int dock;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private int lunchdeduct;
    
    public Shift (HashMap<String, String> parameters) {
        
        this.id = Integer.parseInt( parameters.get("id") );
        this.description = parameters.get("description");
        
        int starthour = Integer.parseInt( parameters.get("starthour") );
        int startminute = Integer.parseInt( parameters.get("startminute") );
        int stophour = Integer.parseInt( parameters.get("stophour") );
        int stopminute = Integer.parseInt( parameters.get("stopminute") );
        
        int lstarthour = Integer.parseInt( parameters.get("lstarthour") );
        int lstartminute = Integer.parseInt( parameters.get("lstartminute") );
        int lstophour = Integer.parseInt( parameters.get("lstophour") );
        int lstopminute = Integer.parseInt( parameters.get("lstopminute") );
        
        this.start = LocalTime.of( starthour, startminute );
        this.stop = LocalTime.of( stophour, stopminute );
        this.lunchstart = LocalTime.of( lstarthour, lstartminute );
        this.lunchstop = LocalTime.of( lstophour, lstopminute );
        
        this.interval = Integer.parseInt( parameters.get("interval") );
        this.graceperiod = Integer.parseInt( parameters.get("graceperiod") );
        this.dock = Integer.parseInt( parameters.get("dock") );
        
        this.lunchdeduct = Integer.parseInt( parameters.get("lunchdeduct") );
        
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public int getInterval() {
        return interval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public int getDock() {
        return dock;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getLunchdeduct() {
        return lunchdeduct;
    }
    
    public int getShiftDuration() {
        
        return ( (int)ChronoUnit.MINUTES.between(start, stop) );
        
    }
    
    public int getLunchDuration() {
        
        return ( (int)ChronoUnit.MINUTES.between(lunchstart, lunchstop) );
        
    }
    
    @Override
    public String toString() {
        
        // "Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)"
        
        StringBuilder s = new StringBuilder();
        
        s.append(description).append(": ");
        
        s.append( String.format("%02d", start.getHour()) ).append(":");
        s.append( String.format("%02d", start.getMinute()) ).append(" - ");
        
        s.append( String.format("%02d", stop.getHour()) ).append(":");
        s.append( String.format("%02d", stop.getMinute()) ).append(" ");
        
        s.append("(").append( getShiftDuration() ).append(" minutes); ");
        
        s.append("Lunch: ");
        
        s.append( String.format("%02d", lunchstart.getHour()) ).append(":");
        s.append( String.format("%02d", lunchstart.getMinute()) ).append(" - ");
        
        s.append( String.format("%02d", lunchstop.getHour()) ).append(":");
        s.append( String.format("%02d", lunchstop.getMinute()) ).append(" ");
        
        s.append("(").append( getLunchDuration() ).append(" minutes)");
        
        return s.toString();
        
    }
    
    
}

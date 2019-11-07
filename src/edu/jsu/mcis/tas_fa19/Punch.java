
package edu.jsu.mcis.tas_fa19;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class Punch {
    
    private int id;
    private int terminalid;
    private String badgeid;
    private long originalTimeStamp;
    private long adjustedTimeSamp;
    private int punchtypeid;
    
    private String adjustmenttype;
    private Badge badge;

    public Punch(Badge badge, int terminalid, int punchtypeid) {
        
        /* Constructor for new Punch objects */
        
        this.badge = badge;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        
        this.id = 0;
        this.adjustmenttype = null;
        
        GregorianCalendar now = new GregorianCalendar();
        this.originalTimeStamp = now.getTimeInMillis();
        this.adjustedTimeSamp = now.getTimeInMillis();
        
    }
    
    public Punch(int id, Badge badge, int terminalid, int punchtypeid, long originaltimestamp) {
        
        /* Constructor for already-existing Punch objects */
        
        this.badge = badge;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        
        this.id = id;
        this.adjustmenttype = null;
        
        this.originalTimeStamp = originaltimestamp;
        this.adjustedTimeSamp = originaltimestamp;
        
    }

    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public long getOriginaltimestamp() {
        return originalTimeStamp;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }
    
    public String printOriginalTimestamp(){
        StringBuilder s = new StringBuilder();
        
        GregorianCalendar ots = new GregorianCalendar();
        ots.setTimeInMillis(originalTimeStamp);
        Date d = ots.getTime();
        
        // "#D2C39273 CLOCKED IN: WED 09/05/2018 07:00:07" (use SimpleDateFormat)
        // Add Badge ID and punch type to StringBuilder
        
        
        s.append("#").append(badge.getId()).append(" CLOCKED IN: ");
        
        // INSERT YOUR CODE HERE
        
        // Add OriginalTimeStamp to StringBuilder        
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        s.append(sdf.format(d).toUpperCase() );
        
        // Compose output string and return
        
        return ( s.toString() );
    }
    
    @Override
    public String toString() {
        
        return ( printOriginalTimestamp() );
        
    }
    
}

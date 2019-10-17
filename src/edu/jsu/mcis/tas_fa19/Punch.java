
package edu.jsu.mcis.tas_fa19;


public class Punch {
    private int id;
    private int terminalid;
    private String badgeid;
    private long originalTimeStamp;
    private int punchid;
    private String adjustmenttype;
    private Badge badge;

    public Punch(Badge badge, int id, int terminalid, String badgeid, long originalTimeStamp, int punchid, String adjustmenttype) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originalTimeStamp = originalTimeStamp;
        this.punchid = punchid;
        this.adjustmenttype = adjustmenttype;
        this.badge = badge;
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

    public long getOriginalTimeStamp() {
        return originalTimeStamp;
    }

    public int getPunchid() {
        return punchid;
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }
    
    
    
    
    
}

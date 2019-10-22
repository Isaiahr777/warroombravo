package edu.jsu.mcis.tas_fa19;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalTime;

public class TASDatabase {
    
    private static Connection conn = null;
    private String myUrl = null;
    private String qBadges = null;
    private String qPunched = null;
    private String qShifted= null;
    private Statement STATE;
    
    public TASDatabase() throws ClassNotFoundException{
        
        try{
            myUrl = "jdbc:mysql://localhost/tas";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl,"Tasuser", "tasuser");
               
        } 
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    
     public Badge getBadge(String badge) throws SQLException{

        STATE = conn.createStatement();
        qBadges = "select from badge where id = '"+ badge +"'";
        ResultSet RS = STATE.executeQuery(qBadges);
         
        String badgeId = null;
        String badgeDescription = null;
        
        while(RS.next()){
            badgeId = RS.getString("Id");
            badgeDescription = RS.getString("Description");
     }
        close(STATE);
        
        Badge B = new Badge(badgeId, badgeDescription);
        
        return B;
    }
     public Shift getShift(String shift)throws SQLException{
         STATE = conn.createStatement();
         qShifted = "select from shift where id = '"+ shift +"'";
         ResultSet RS = STATE.executeQuery(qShifted);
         
         int shiftID = 0;
         int lunchDuration = 0;
         String shiftDescription = null;
         LocalTime start= null;
         LocalTime stop = null;
         int interval=0;
         int gracePeriod=0;
         int dock=0;
         LocalTime lunchStart = null;
         LocalTime lunchStop = null;
         int lunchDeduct=0;
         
         while (RS.next()){
             shiftID = RS.getInt("id");
             shiftDescription = RS.getString("description");
             start = (LocalTime) RS.getObject("start");
             stop = (LocalTime) RS.getObject("stop");
             interval = RS.getInt("interval");
             gracePeriod = RS.getInt("graceperiod");
             dock = RS.getInt("dock");
             lunchStart = (LocalTime) RS.getObject("lunchstart");
             lunchStop = (LocalTime) RS.getObject("lunchstop");
             lunchDeduct = RS.getInt("lunchdeduct");
             
         }
         close(STATE);
         
         Shift shifts = new Shift(lunchDuration, shiftID, shiftDescription, start, stop,
         interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
         
         return shifts;
     }
     public Shift getShift(Badge badge) throws SQLException{
         STATE = conn.createStatement();
         qShifted = "select from shift where id = '"+ badge +"'";
         ResultSet RS = STATE.executeQuery(qShifted);
         
         int shiftID = 0;
         int lunchDuration = 0;
         String shiftDescription = null;
         LocalTime start= null;
         LocalTime stop = null;
         int interval=0;
         int gracePeriod=0;
         int dock=0;
         LocalTime lunchStart = null;
         LocalTime lunchStop = null;
         int lunchDeduct=0;
         
         while (RS.next()){
             shiftID = RS.getInt("id");
             shiftDescription = RS.getString("description");
             start = (LocalTime) RS.getObject("start");
             stop = (LocalTime) RS.getObject("stop");
             interval = RS.getInt("interval");
             gracePeriod = RS.getInt("graceperiod");
             dock = RS.getInt("dock");
             lunchStart = (LocalTime) RS.getObject("lunchstart");
             lunchStop = (LocalTime) RS.getObject("lunchstop");
             lunchDeduct = RS.getInt("lunchdeduct");
             
         }
         close(STATE);
         
         Shift shifts = new Shift(lunchDuration, shiftID, shiftDescription, start, stop,
         interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
         
         return shifts;
     }
     public Punch getPunch(String punch) throws SQLException{
          STATE = conn.createStatement();
         qPunched = "select from shift where id = '"+ punch +"'";
         ResultSet RS = STATE.executeQuery(qPunched);
         
         Badge badge = null;
         int punchID = 0;
         int terminalId = 0;
         String badgeID = null;
         long originalTimeStamp = 0;
         int punchTypeID = 0;
         String adjustmentType = null;
         
         while (RS.next()){
             punchID = RS.getInt("id");
             terminalId = RS.getInt("terminalid");
             badgeID = RS.getString("badgeid");
             originalTimeStamp = RS.getLong("originaltimestamp");
             punchTypeID = RS.getInt("punchtypeid");
             
         }
         close(STATE);
         
         Punch P = new Punch (badge, punchID, terminalId, badgeID, originalTimeStamp,
         punchTypeID, adjustmentType);
         
         return P;
         
     }
     
    
    public void close(Statement STATE){
        try {
            STATE.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

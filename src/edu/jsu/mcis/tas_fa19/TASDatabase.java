package edu.jsu.mcis.tas_fa19;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TASDatabase {

    private Connection conn;

    public TASDatabase(){


        try {
            
            String myUrl = "jdbc:mysql://localhost/tas";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl,"War_Room_Bravo", "CS488");

        } 
        catch (Exception e) { e.printStackTrace(); }
    }


     public Badge getBadge(String id){
         
        Badge b = null;
        String query = "SELECT * FROM badge WHERE id = ?";

        try {
             
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, id);

            boolean hasresults = state.execute();

            if ( hasresults ) {
                
                ResultSet resultset = state.getResultSet();
                
                if (resultset.next()) {
                    
                    String ID = resultset.getString("id");
                    String desc = resultset.getString("description");
                    
                    b = new Badge(ID, desc);
                    
                }
                
                resultset.close();                

            }
            
            
            state.close();

        }
        catch (Exception ex) { ex.printStackTrace(); }

        return b;

    }
     public Shift getShift(int shift){
         Shift shifts = null;
         
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
         
         String qShifted = "SELECT * FROM shift WHERE shift = ?";
        
         try{
             PreparedStatement state = conn.prepareStatement(qShifted);
             state.setInt(1, shift);
             
             boolean hasresults = state.execute();
             ResultSet result = null;
             
             if (hasresults){
                 ResultSet resultset = state.getResultSet();
                 resultset.next();
                 shiftID = result.getInt("id");
                 shiftDescription = result.getString("description");
                 start = (LocalTime) result.getObject("start");
                 stop = (LocalTime) result.getObject("stop");
                 interval = result.getInt("interval");
                 gracePeriod = result.getInt("graceperiod");
                 dock = result.getInt("dock");
                 lunchStart = (LocalTime) result.getObject("lunchstart");
                 lunchStop = (LocalTime) result.getObject("lunchstop");
                 lunchDeduct = result.getInt("lunchdeduct");
                 
                 shifts = new Shift(shiftID, lunchDuration, shiftDescription, start, stop, interval,
                            gracePeriod, dock, lunchStart,lunchStop, lunchDeduct);
             }
             result.close();
             state.close();
         }
         catch (Exception ex){ ex.printStackTrace();}

         return shifts;
     }
    
     public Shift getShift(Badge badge){
         Shift shifts = null;
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
         
         String qShifted = "select from shift where id = ?";
         try{
             PreparedStatement state = conn.prepareStatement(qShifted);
             state.setObject(1, badge);
             
             boolean hasresults = state.execute();
             ResultSet result = null;
             
             if (hasresults){
                 ResultSet resultset = state.getResultSet();
                 resultset.next();
                 shiftID = result.getInt("id");
                 shiftDescription = result.getString("description");
                 start = (LocalTime) result.getObject("start");
                 stop = (LocalTime) result.getObject("stop");
                 interval = result.getInt("interval");
                 gracePeriod = result.getInt("graceperiod");
                 dock = result.getInt("dock");
                 lunchStart = (LocalTime) result.getObject("lunchstart");
                 lunchStop = (LocalTime) result.getObject("lunchstop");
                 lunchDeduct = result.getInt("lunchdeduct");
                 
                 shifts = new Shift(shiftID, lunchDuration, shiftDescription, start, stop, interval,
                            gracePeriod, dock, lunchStart,lunchStop, lunchDeduct);
             }
             result.close();
             state.close();
         }
         catch (Exception ex){ ex.printStackTrace();}

         return shifts;
     }
     public Punch getPunch(int punch){
         Punch P = null;
         String qPunched = "SELECT * FROM punch WHERE punch = ?";
         
         Badge badge = null;
         int punchID = 0;
         int terminalId = 0;
         String badgeID = null;
         long originalTimeStamp = 0;
         int punchTypeID = 0;
         String adjustmentType = null;
         
         try{
             PreparedStatement state = conn.prepareStatement(qPunched);
             state.setInt(1, punch);
             
             boolean hasresults = state.execute();
             ResultSet result = null;
             
             if (hasresults){
                 ResultSet resultset = state.getResultSet();
                 resultset.next();
                 punchID = result.getInt("id");
                 terminalId = result.getInt("terminalid");
                 badgeID = result.getString("badgeid");
                 originalTimeStamp = result.getLong("originaltimestamp");
                 punchTypeID = result.getInt("punchtypeid");
                 
                 P = new Punch(badge, terminalId, punchTypeID);
             }
             result.close();
             state.close();
         }
         catch (Exception ex){
             
         }
       
         return P;
         
     }
     public int insertPunch(Punch p){
         
         //getter methods as variables
         
         long stamp = p.getOriginaltimestamp();
         int tId = p.getTerminalid();
         int pId = p.getPunchtypeid();
         String badgeid = p.getBadgeid();
         String aType = p.getAdjustmenttype();
         
         String iPunch = "INSERT INTO punch (badgeid, terminalid, punchtypeid" 
                 + "VALUES (badgeid, tId, pId)";
         
         try{
             Statement state = conn.createStatement();
             state.executeUpdate(iPunch);
         }
         catch(Exception e){
             
         }
         
         return p.getId();
     }
        public ArrayList<Punch> getDailyPunchList(Badge badge, long ts){
            
            ArrayList<Punch> punchlist = new ArrayList<>();
                        
            GregorianCalendar gc1 = new GregorianCalendar();
            GregorianCalendar gc2 = new GregorianCalendar();
            
            gc1.setTimeInMillis(ts);
            gc2.setTimeInMillis(ts);
            
            gc1.set(Calendar.HOUR_OF_DAY, 0);
            gc1.set(Calendar.MINUTE, 0);
            gc1.set(Calendar.SECOND, 0);
            
            gc2.set(Calendar.HOUR_OF_DAY, 23);
            gc2.set(Calendar.MINUTE, 59);
            gc2.set(Calendar.SECOND, 59);
            
            long dayStart = gc1.getTimeInMillis();
            long dayEnd = gc2.getTimeInMillis();
            
            SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
            
            System.err.println("dayStart: " + sdf.format(gc1.getTime()) + ": " + dayStart);
            System.err.println("dayEnd: " + sdf.format(gc2.getTime()) + ": " + dayEnd);
            
            String query1 = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 as ts FROM punch "
                          + "WHERE badgeid = ? "
                          + "HAVING ts >= ? and ts <= ? "
                          + "ORDER BY originaltimestamp";
            
            String query2 = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 as ts FROM punch "
                          + "WHERE badgeid = ? "
                          + "HAVING ts > ? "
                          + "ORDER BY originaltimestamp "
                          + "LIMIT 1";
            
            
            try {
                
                
                
                PreparedStatement state = conn.prepareStatement(query1);
                
                state.setString(1, badge.getId());
                state.setLong(2, dayStart);
                state.setLong(3, dayEnd);
                
                boolean hasresults = state.execute();

                if ( hasresults ) {
                
                    ResultSet resultset = state.getResultSet();
                    
                    while( resultset.next() ) {
                        
                        int id = resultset.getInt("id");
                        String badgeid = resultset.getString("badgeid");                        
                        int terminalid = resultset.getInt("terminalid");
                        int punchtypeid = resultset.getInt("punchtypeid");
                        long ots = resultset.getLong("ts");
                        
                        Badge b = getBadge(badgeid);

                        Punch p = new Punch(id, b, terminalid, punchtypeid, ots);
                        punchlist.add(p);

                    }
                    
                }
                
            }
            
            catch(Exception e) { e.printStackTrace(); }
            
            return punchlist;
        
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

package edu.jsu.mcis.tas_fa19;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
                    
                    resultset.close();
                    
                }               

            }
            
            
            state.close();

        }
        catch (Exception ex) { ex.printStackTrace(); }

        return b;

    }
     public Shift getShift(int shiftid){
         
        Shift s = null;

        String query = "SELECT *,\n" +
        "HOUR(`start`) AS starthour, MINUTE(`start`) AS startminute,\n" +
        "HOUR(`stop`) AS stophour, MINUTE(`stop`) AS stopminute,\n" +
        "HOUR(`lunchstart`) AS lstarthour, MINUTE(`lunchstart`) AS lstartminute,\n" +
        "HOUR(`lunchstop`) AS lstophour, MINUTE(`lunchstop`) AS lstopminute\n" +
        "FROM shift s WHERE id = ?";

        try {
            
             PreparedStatement state = conn.prepareStatement(query);
             state.setInt(1, shiftid);
             
             boolean hasresults = state.execute();
             
             if (hasresults) {
                 
                ResultSet resultset = state.getResultSet();

                if (resultset.next()) {
                    
                    HashMap<String, String> parameters = new HashMap<>();
                    
                    parameters.put("id", String.valueOf(resultset.getInt("id")));

                    parameters.put("description", resultset.getString("description"));
                    
                    parameters.put("starthour", String.valueOf(resultset.getInt("starthour")));
                    parameters.put("startminute", String.valueOf(resultset.getInt("startminute")));
                    parameters.put("stophour", String.valueOf(resultset.getInt("stophour")));
                    parameters.put("stopminute", String.valueOf(resultset.getInt("stopminute")));
                    
                    parameters.put("lstarthour", String.valueOf(resultset.getInt("lstarthour")));
                    parameters.put("lstartminute", String.valueOf(resultset.getInt("lstartminute")));
                    parameters.put("lstophour", String.valueOf(resultset.getInt("lstophour")));
                    parameters.put("lstopminute", String.valueOf(resultset.getInt("lstopminute")));
                    
                    parameters.put("interval", String.valueOf(resultset.getInt("interval")));
                    parameters.put("graceperiod", String.valueOf(resultset.getInt("graceperiod")));
                    parameters.put("dock", String.valueOf(resultset.getInt("dock")));
                    parameters.put("lunchdeduct", String.valueOf(resultset.getInt("lunchdeduct")));
                    
                    s = new Shift(parameters);
                    
                    resultset.close();

                }

             }
             
             state.close();
         }
         catch (Exception ex){ ex.printStackTrace();}

         return s;
         
     }
    
    public Shift getShift(Badge badge) {
         
        String badgeid = badge.getId();
        Shift s = null;
        
        String query = "SELECT shiftid FROM employee WHERE badgeid = ?";

        try {
            
             PreparedStatement state = conn.prepareStatement(query);
             state.setString(1, badgeid);
             
             boolean hasresults = state.execute();
             
             if (hasresults) {
                 
                ResultSet resultset = state.getResultSet();

                if (resultset.next()) {
                    
                    int shiftid = resultset.getInt(1);                    
                    s = getShift(shiftid);
                    
                    resultset.close();

                }

            }

            state.close();
            
        }
        catch (Exception ex){ ex.printStackTrace();}

        return s;
        
     }
     public Punch getPunch(int punch){
         Punch P = null;
         String qPunched = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS ts FROM punch WHERE id = ?";
         
         try{
             PreparedStatement state = conn.prepareStatement(qPunched);
             state.setInt(1, punch);
             
             boolean hasresults = state.execute();
             
             if (hasresults){
                 ResultSet resultset = state.getResultSet();
                 resultset.next();
                 int punchID = resultset.getInt("id");
                 int terminalId = resultset.getInt("terminalid");
                 String badgeID = resultset.getString("badgeid");
                 long originalTimeStamp = resultset.getLong("ts");
                 int punchTypeID = resultset.getInt("punchtypeid");
                 
                 Badge badge = getBadge(badgeID);
                 
                 P = new Punch(punchID, badge, terminalId, punchTypeID, originalTimeStamp);
                 
                 resultset.close();
             }
             
             state.close();
         }
         catch (Exception ex){ ex.printStackTrace(); }
       
         return P;
         
     }
     public int insertPunch(Punch p) {
         
         int key = 0;
         
         //getter methods as variables
         
         long stamp = p.getOriginaltimestamp();
         int tId = p.getTerminalid();
         int pId = p.getPunchtypeid();
         String badgeid = p.getBadgeid();
         String aType = p.getAdjustmenttype();
         
         GregorianCalendar ots = new GregorianCalendar();
         ots.setTimeInMillis(stamp);
         
         
         /*String iPunch = "INSERT INTO punch (badgeid, terminalid, punchtypeid)" 
                 + "VALUES (badgeid, tId, pId)";
         */
         
         try {
         
            String iPunch = "INSERT INTO punch (terminalid, badgeid, punchtypeid, "
                    + "originaltimestamp) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(iPunch, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, tId);
            pstmt.setString(2, badgeid);
            pstmt.setInt(3, pId);
            pstmt.setString(4, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ots.getTime()));
                        
            int result = pstmt.executeUpdate();
            
            if (result == 1) {
                
                ResultSet keys = pstmt.getGeneratedKeys();
                
                if (keys.next()) {
                    key = keys.getInt(1);
                }
                
            }
            
         }
         catch(Exception e) { e.printStackTrace(); }
         
         return key;
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
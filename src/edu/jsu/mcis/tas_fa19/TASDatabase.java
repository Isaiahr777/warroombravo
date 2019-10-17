package edu.jsu.mcis.tas_fa19;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     
    
     public void close(Statement STATE){
        try {
            STATE.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
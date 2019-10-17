package edu.jsu.mcis.tas_fa19;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TASDatabase {
    
    private Connection conn;
    
    public TASDatabase(){
        
        
        try{
            String myUrl = "jdbc:mysql://localhost/tas";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl,"Tasuser", "tasuser");
               
        } 
        catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());

        }
    }
    
    
     public Badge getBadge(String id){
         Badge b = null;
         String query = "SELECT * FROM badge WHERE id = ?";
         
         
         try{
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, id);
            
            boolean hasresults = state.execute();
            ResultSet result = null;
            
            if ( hasresults ){
                ResultSet resultset = state.getResultSet();
                resultset.next();
                String ID = result.getString("id");
                String desc = result.getString("description");
                
            }
            result.close();
            state.close();
            
         
         }
         
         catch (Exception ex){
                 
                 }
         return b;
         
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
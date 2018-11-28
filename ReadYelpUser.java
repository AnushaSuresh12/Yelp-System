package Populate;
 
import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


// The class to populate yelpUser
public class ReadYelpUser 
{
    public void run_yelpuserfile() throws SQLException
    {
        // TODO Auto-generated method stub
        Connection dbConnection = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
 
        String query = "INSERT INTO YELP_USER"
        + "(yelping_since, funny_votes, useful_votes, cool_votes, review_count, user_name, user_id, fans, average_stars, u_type, hot_compliment, more_compliment, profile_compliment, cute_compliment, list_compliment, note_compliment, plain_compliment, cool_compliment, funny_compliment, writer_compliment, photos_compliment ) VALUES"
                + "(to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO FRIENDS" + "(user_id, friend_id) VALUES" + "(?,?)";
        String query3 = "INSERT INTO ELITE_YEARS" + "(user_id, elite) VALUES" + "(?,?)";
         
        JSONParser parser = new JSONParser();
         
        try
        {
            dbConnection = getDBConnection();
            ps = dbConnection.prepareStatement(query);
            ps2 = dbConnection.prepareStatement(query2);
            ps3 = dbConnection.prepareStatement(query3);
            FileReader file = new FileReader("C:\\Users\\Anusha\\eclipse-workspace\\DataBase\\Json Files\\yelp_user.json");
            
            System.out.println(file);
            BufferedReader bufferedReader = new BufferedReader(file);
            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
            	System.out.println(line);
            	Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                
                String date_string = (String) jsonObject.get("yelping_since");
                System.out.println(date_string);
    			Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.MONTH, Integer.parseInt(date_string.split("-")[1])-1);
                calendar.set(Calendar.YEAR,Integer.parseInt(date_string.split("-")[0]));
                Date date = (Date) calendar.getTime();
                System.out.println(date);
                
                java.sql.Date d = new java.sql.Date(date.getTime());
                System.out.println("____________");
                System.out.println(d.toString());
                ps.setString(1, d.toString());
             
                int Review_count = ((Long) jsonObject.get("review_count")).intValue();
                ps.setInt(5, Review_count);
             
                String Name = (String) jsonObject.get("name");
                ps.setString(6, Name);
             
                String User_id = (String) jsonObject.get("user_id");
                ps.setString(7, User_id);
             
                int fan = ((Long) jsonObject.get("fans")).intValue();
                ps.setInt(8, fan);
             
                float Avg_star = ((Double) jsonObject.get("average_stars")).floatValue();
                ps.setFloat(9, Avg_star);
             
                String Type = (String) jsonObject.get("type");
                ps.setString(10, Type);
                 
                JSONObject votes = (JSONObject) jsonObject.get("votes");
                int  funny_votes = ((Long) votes.get("funny")).intValue();
                int  useful_votes = ((Long) votes.get("useful")).intValue();
                int  cool_votes = ((Long) votes.get("cool")).intValue();
                 
                ps.setInt(2, funny_votes);
                ps.setInt(3, useful_votes);
                ps.setInt(4, cool_votes);
                 
                 
                JSONObject compliments = (JSONObject) jsonObject.get("compliments");
                
                System.out.println("compliments:" + compliments);
                
                int hot_compliment;
                int more_compliment;
                int profile_compliment;
                int cute_compliment;
                int list_compliment;
                int note_compliment;
                int plain_compliment;
                int cool_compliment;
                int funny_compliment;
                int writer_compliment;
                int photos_compliment;
                if(compliments.get("hot")!=null)
                {
                    hot_compliment = ((Long) compliments.get("hot")).intValue();
                }
                
                else
                {
                    hot_compliment = 0;         
                }
                 
                if(compliments.get("more")!=null)
                {
                    more_compliment = ((Long) compliments.get("more")).intValue();
                }
                else
                {
                    more_compliment = 0;            
                }
                 
                if(compliments.get("profile")!=null)
                {
                    profile_compliment = ((Long) compliments.get("profile")).intValue();
                }
                else
                {
                    profile_compliment = 0;         
                }
                 
                if(compliments.get("cute")!=null)
                {
                    cute_compliment = ((Long) compliments.get("cute")).intValue();
                }
                else
                {
                    cute_compliment = 0;            
                }
                 
                if(compliments.get("list")!=null)
                {
                    list_compliment = ((Long) compliments.get("list")).intValue();
                }
                else
                {
                    list_compliment = 0;            
                }
                 
                if(compliments.get("note")!=null)
                {
                    note_compliment = ((Long) compliments.get("note")).intValue();
                }
                else
                {
                    note_compliment = 0;            
                }
                 
                if(compliments.get("plain")!=null)
                {
                    plain_compliment = ((Long) compliments.get("plain")).intValue();
                }
                else
                {
                    plain_compliment = 0;           
                }
                 
                if(compliments.get("cool")!=null)
                {
                    cool_compliment = ((Long) compliments.get("cool")).intValue();
                }
                else
                {
                    cool_compliment = 0;
                }
                 
                if(compliments.get("funny")!=null)
                {
                    funny_compliment = ((Long) compliments.get("funny")).intValue();
                }
                else
                {
                    funny_compliment = 0;           
                }
                 
                if(compliments.get("writer")!=null)
                {
                    writer_compliment = ((Long) compliments.get("writer")).intValue();
                }
                else
                {
                    writer_compliment = 0;          
                }
                 
                if(compliments.get("photos")!=null)
                {
                    photos_compliment = ((Long) compliments.get("photos")).intValue();
                }
                else
                {
                    photos_compliment = 0;          
                }
                 
                ps.setInt(11, hot_compliment);
                ps.setInt(12, more_compliment);
                ps.setInt(13, profile_compliment);
                ps.setInt(14, cute_compliment);
                ps.setInt(15, list_compliment);
                ps.setInt(16, note_compliment);
                ps.setInt(17, plain_compliment);
                ps.setInt(18, cool_compliment);
                ps.setInt(19, funny_compliment);
                ps.setInt(20, writer_compliment);
                ps.setInt(21, photos_compliment);
                 
                ps.executeUpdate();
 
                if(jsonObject.get("friends")!=null)
                {
                    JSONArray friend = (JSONArray) jsonObject.get("friends");
                 
					@SuppressWarnings("unchecked")
					Iterator<String> it = friend.iterator();
                    String friend_id;
             
                    while(it.hasNext())
                    {
                        friend_id = it.next();
                        ps2.setString(1, User_id);
                        ps2.setString(2, friend_id);
                        ps2.executeUpdate();
                    }
                }
 
                if(jsonObject.get("elite")!=null)
                {
                    JSONArray elite = (JSONArray) jsonObject.get("elite");
                   
                    
					@SuppressWarnings("unchecked")
					Iterator<Long> it2 = elite.iterator();
                    int elite_year;
                 
                    while(it2.hasNext())
                    {
                        elite_year = (it2.next()).intValue();
                        ps3.setString(1, User_id);
                        ps3.setInt(2, elite_year);
                        ps3.executeUpdate();
                    }
                }
            }
            file.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (ps != null) 
            {
                ps.close();
            }
            if (ps2 != null) 
            {
                ps2.close();
            }
            if (ps3 != null) 
            {
                ps3.close();
            }
 
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
 
    }
     
    public static Connection getDBConnection() 
    {
 
    	System.out.println("inside connection class");
        Connection dbConnection = null;
 
        try {
 
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        try {
 
            dbConnection = DriverManager.getConnection(
            		"jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
            return dbConnection;
         } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        return dbConnection;
 
    }       
 
}
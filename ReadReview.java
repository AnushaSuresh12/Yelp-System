package Populate;
 
import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// This class is used to populate reviewJSON file
public class ReadReview
{
 
    public void run_reviewfile() throws SQLException
    {
        // TODO Auto-generated method stub
        Connection dbConnection = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO REVIEWS"
        + "(review_id, user_id, bid, funny_vote, useful_vote, cool_vote, stars, r_date, r_text, r_type) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?)";
         
        JSONParser parser = new JSONParser();
		try {
			
			dbConnection = getDBConnection();
			ps = dbConnection.prepareStatement(query);
			
			FileReader fReader = new FileReader("C:\\Users\\Anusha\\eclipse-workspace\\DataBase\\Json Files\\yelp_review.json");
			BufferedReader bfReader = new BufferedReader(fReader);
			String next;
			
			while ((next = bfReader.readLine()) != null) 
			{
				Object objParse = parser.parse(next);
				JSONObject objJson = (JSONObject) objParse;
				
				ps.setString(1, (String) objJson.get("review_id"));
				ps.setString(2, (String) objJson.get("user_id"));
				ps.setString(3, (String) objJson.get("business_id"));
				
				JSONObject objVotes = (JSONObject) objJson.get("votes");

				ps.setInt(4, ((Long) objVotes.get("funny")).intValue());
				ps.setInt(5, ((Long) objVotes.get("useful")).intValue());
				ps.setInt(6, ((Long) objVotes.get("cool")).intValue());

				ps.setInt(7, ((Long) objJson.get("stars")).intValue());
				ps.setString(8, (String) objJson.get("date"));
				ps.setString(9, (String) objJson.get("text"));
				ps.setString(10, (String) objJson.get("type"));

				ps.executeUpdate();	
			}
			
			fReader.close();
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
            if (ps!= null) 
            {
                ps.close();
            }
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
 
    }
    public static Connection getDBConnection() 
    {
 
        Connection dbConnection = null;
 
        try {
 
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        try {
 
        	 dbConnection = DriverManager.getConnection(
              		"jdbc:oracle:thin:@localhost:1521:ORCL", "scott", "tiger");
            return dbConnection;
 
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        return dbConnection;
 
    }       
 
}

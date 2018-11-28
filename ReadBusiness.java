package Populate;
import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// This class is used to populate the businessJSONfile
public class ReadBusiness {
 
    public void run_businessfile() throws SQLException
    {
        // TODO Auto-generated method stub
    	Connection dbConnection = null;
    	PreparedStatement ps1 = null;
    	PreparedStatement ps2 = null;
    	PreparedStatement ps3 = null;
    	PreparedStatement ps4 = null;
    	PreparedStatement ps5 = null;
    	PreparedStatement ps6 = null;
 
        String query1 = "INSERT INTO BUSINESS"
        + "(bid, full_address, open_now, city, review_count, b_name, longitude, state, stars, latitude, b_type) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO b_hours" + "(d_o_w, from_h, to_h, bid) VALUES" + "(?,?,?,?)";
        String query3 = "INSERT INTO b_main_category" + "(c_name, bid) VALUES" + "(?,?)";
        String query4 = "INSERT INTO b_sub_category" + "(c_name, bid) VALUES" + "(?,?)";
        String query5 = "INSERT INTO neighborhoods" + "(n_name, bid) VALUES" + "(?,?)";
        String query6 = "INSERT INTO b_attributes" + "(a_name, bid) VALUES" + "(?,?)";
         
        JSONParser parser = new JSONParser();
         
        try
        {
            dbConnection = getDBConnection();
            ps1 = dbConnection.prepareStatement(query1);
            ps2 = dbConnection.prepareStatement(query2);
            ps3 = dbConnection.prepareStatement(query3);
            ps4 = dbConnection.prepareStatement(query4);
            ps5 = dbConnection.prepareStatement(query5);
            ps6 = dbConnection.prepareStatement(query6);
            FileReader filereader = new FileReader("C:\\Users\\Anusha\\eclipse-workspace\\DataBase\\Json Files\\yelp_business.json");
            BufferedReader bufferedReader = new BufferedReader(filereader);
            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
            	Object o = parser.parse(line);
                JSONObject j = (JSONObject) o;
                 
                String business_id = (String) j.get("business_id");
                ps1.setString(1, business_id);
                 
                String b_address = (String) j.get("full_address");
                ps1.setString(2, b_address);
                 
                boolean open_now = (Boolean) j.get("open");
                int o_now;
                if (open_now)
                {
                    o_now = 1;
                }
                else
                {
                    o_now = 0;
                }
                ps1.setInt(3, o_now);
                 
                String city = (String) j.get("city");
                ps1.setString(4, city);
                 
                int review_count = ((Long) j.get("review_count")).intValue();
                ps1.setInt(5, review_count);
                 
                String b_name = (String) j.get("name");
                ps1.setString(6, b_name);
                 
                float longitude = ((Double) j.get("longitude")).floatValue();
                ps1.setFloat(7, longitude);
                 
                String state = (String) j.get("state");
                ps1.setString(8, state);
                 
                float stars = ((Double) j.get("stars")).floatValue();
                ps1.setFloat(9, stars);
                 
                float latitude = ((Double) j.get("latitude")).floatValue();
                ps1.setFloat(10, latitude);
                 
                String type = (String) j.get("type");
                ps1.setString(11, type);
                 
                ps1.executeUpdate();
                                
                if(j.get("neighborhoods")!=null)
                {
                    JSONArray nei_array = (JSONArray) j.get("neighborhoods");
                    Iterator<String> iterator = nei_array.iterator();
                    String n_name;
             
                    while(iterator.hasNext())
                    {
                        n_name = iterator.next();
                        ps5.setString(1, n_name);
                        ps5.setString(2, business_id);
                        ps5.executeUpdate();
                    }
                }
                 
                JSONArray cat_array = (JSONArray) j.get("categories");
                Iterator<String> iterator = cat_array.iterator();
                String cat;
         
                while(iterator.hasNext())
                {
                    cat = iterator.next();
                    if(cat.equals("Active Life") || cat.equals("Arts & Entertainment") || cat.equals("Automotive") || 
                            cat.equals("Car Rental") || cat.equals("Cafes") || cat.equals("Beauty & Spas") || 
                            cat.equals("Convenience Stores") || cat.equals("Dentists") || cat.equals("Doctors") ||
                            cat.equals("Drugstores") || cat.equals("Department Stores") || cat.equals("Education") ||
                            cat.equals("Event Planning & Services") || cat.equals("Flowers & Gifts") || 
                            cat.equals("Food") || cat.equals("Health & Medical") || cat.equals("Home Services") ||
                            cat.equals("Home & Garden") || cat.equals("Hospitals") || cat.equals("Hotels & Travel") ||
                            cat.equals("Hardware Stores") || cat.equals("Grocery") || cat.equals("Medical Centers") ||
                            cat.equals("Nurseries & Gardening") || cat.equals("Nightlife") || cat.equals("Restaurants") ||
                            cat.equals("Shopping") || cat.equals("Transportation"))
                    {
                        ps3.setString(1, cat);
                        ps3.setString(2, business_id);
                        ps3.executeUpdate();
                    }
                    else
                    {
                        ps4.setString(1, cat);
                        ps4.setString(2, business_id);
                        ps4.executeUpdate();
                    }
                     
                }
                 
                if(j.get("attributes")!=null)
                {
                    JSONObject jsonObject4 = (JSONObject) j.get("attributes");
                    for (Object key : jsonObject4.keySet()) 
                    {
                        String keyStr = (String)key;
                        Object keyvalue = jsonObject4.get(keyStr);                    
 
                        if (keyvalue instanceof JSONObject)
                        {
                            JSONObject jsonObject5 = (JSONObject) jsonObject4.get(key);
                            for (Object key2 : jsonObject5.keySet())
                            {
                                String keyStr2 = (String)key2;
                                Object keyvalue2 = jsonObject5.get(keyStr2);
                                if (keyvalue2 instanceof Integer)
                                {
                                    String a_value = ((Long) jsonObject5.get(keyStr2)).toString();
                                    keyStr2 = keyStr2 + "_" + a_value;
                                    ps6.setString(1, keyStr2);
                                    ps6.setString(2, business_id);
                                    ps6.executeUpdate();
                                }
                                else if (keyvalue2 instanceof String)
                                {
                                    String a_value = (String) jsonObject5.get(keyStr2);
                                    keyStr2 = keyStr2 + "_" + a_value;
                                    ps6.setString(1, keyStr2);
                                    ps6.setString(2, business_id);
                                    ps6.executeUpdate();
                                }
                                else if (keyvalue2 instanceof Boolean)
                                {
                                    boolean a = (Boolean) jsonObject5.get(keyStr2);
                                    String a_value = String.valueOf(a);
                                    keyStr2 = keyStr2 + "_" + a_value;
                                    ps6.setString(1, keyStr2);
                                    ps6.setString(2, business_id);
                                    ps6.executeUpdate();
                                }
                            }
                        }
                        else
                        {
                            if (keyvalue instanceof Integer)
                            {
                                String a_value = ((Long) jsonObject4.get(keyStr)).toString();
                                keyStr = keyStr + "_" + a_value;
                                ps6.setString(1, keyStr);
                                ps6.setString(2, business_id);
                                ps6.executeUpdate();
                            }
                            else if (keyvalue instanceof String)
                            {
                                String a_value = (String) jsonObject4.get(keyStr);
                                keyStr = keyStr + "_" + a_value;
                                ps6.setString(1, keyStr);
                                ps6.setString(2, business_id);
                                ps6.executeUpdate();
                            }
                            else if (keyvalue instanceof Boolean)
                            {
                                boolean a = (Boolean) jsonObject4.get(keyStr);
                                String a_value = String.valueOf(a);
                                keyStr = keyStr + "_" + a_value;
                                ps6.setString(1, keyStr);
                                ps6.setString(2, business_id);
                                ps6.executeUpdate();
                            } 
                        }
                         
                    }
                }
                 
                if(j.get("hours")!=null)
                {
                    JSONObject jsonObject2 = (JSONObject) j.get("hours");
                    for (Object key : jsonObject2.keySet()) 
                    {
                        String keyStr = (String)key;                        
                        JSONObject jsonObject3 = (JSONObject) jsonObject2.get(keyStr);
                        String open_h = (String) jsonObject3.get("open");
                        Float o_h = convert_hour(open_h);
                        String close_h = (String) jsonObject3.get("close");
                        Float c_h = convert_hour(close_h);
                        ps2.setString(1, keyStr);
                        ps2.setFloat(2, o_h);
                        ps2.setFloat(3, c_h);
                        ps2.setString(4, business_id);
                        ps2.executeUpdate();                                     
                    }
                }
                 
            }
            filereader.close();
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
            if (ps1 != null) 
            {
                ps1.close();
            }
            if (ps2 != null) 
            {
                ps2.close();
            }
            if (ps3 != null) 
            {
                ps3.close();
            }
            if (ps4 != null) 
            {
                ps4.close();
            }
            if (ps5 != null) 
            {
                ps5.close();
            }
            if (ps6 != null) 
            {
                ps6.close();
            }
 
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
 
    }
     
    public static Float convert_hour(String a)
    {
        String[] b = a.split(":");
        float c = Float.parseFloat(b[0]);
        float d = Float.parseFloat(b[1]);
        d = d / 100;
        c = c + d;
        return c;
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


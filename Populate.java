package Populate;

import java.sql.SQLException;

public class Populate {

	public static void main(String[] args) throws SQLException {
		ReadYelpUser a = new ReadYelpUser();
		System.out.println("POPULATING USER!!!");
		a.run_yelpuserfile();
		System.out.println("DONE POPULATING");
		ReadBusiness b =new ReadBusiness();
		System.out.println("POPULATING BUSINESS NOW!!!");
		b.run_businessfile();
		System.out.println("DONE POPULATING BUSINESS NOW!!!");
		ReadCheckin c = new ReadCheckin();
		System.out.println("POPULATING CHECKIN NOW!!!");
		c.run_checkinfile();
		System.out.println("DONE POPULATING CHECKIN NOW!!!");

		ReadReview d = new ReadReview();
		System.out.println("POPULATING REVIEW NOW!!!");
		d.run_reviewfile();
		System.out.println("DONE POPULATING REVIEW NOW!!!");
   }
}

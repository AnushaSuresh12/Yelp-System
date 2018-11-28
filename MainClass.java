import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class MainClass {

	// The Main Class
	public static void main(String[] args) throws IOException {
		JFrame frame = new initialize();
		frame.setTitle("YELP SYSTEM");
		frame.setVisible(true);
		frame.setSize(1500, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
	}
}

@SuppressWarnings("serial")
class initialize extends JFrame {
	// Declaration of various swing components and variables used
	private static String select[]= {"Select AND,OR Between Attributes","AND","OR"};
	private static String review[]= {"=, <, >","=","<",">"};
	String heading = "#ADD8E6", heading2="#FF00FF";
	JList<String> cat_list, sub_cat_list, attri_list, result_list, user_list;
	String con_str,Query;
	JButton Business_btn, User_btn, Exit_btn;
	JTable business_table, user_table, review_JTable, userreview_JTable;
	JSplitPane result_pane, userresult_pane;
	JPanel query_panel, user_panel;
	DefaultTableModel result_table, userresult_table, review_table, userreview_table;
	JComboBox <String> from_box, choice_box, combo_box, to_box, time1_box, num_box, start_box,votes_box, review_countbox, frnds_countbox, and_box, star_box;
	JTextField value_1;
	JTextField date_1;
	JTextField date_2, value_2, vote_value, count_value, friend_count, member_since, start_value;
	JSpinner.DateEditor member_since_date;
	JSpinner member_since_spinner;
	JSpinner.DateEditor from_date;
	JSpinner from_date_spinner;
	JSpinner.DateEditor to_date;
	JSpinner to_date_spinner;
	JTextArea query_field;
	DefaultListModel<String> add_mainList = new DefaultListModel<String>();
	DefaultListModel<String> add_subList = new DefaultListModel<String>();
	DefaultListModel<String> add_bidlist = new DefaultListModel<String>();
	DefaultListModel<String> add_list = new DefaultListModel<String>();
	DefaultListModel<String> Add_attriList = new DefaultListModel<String>();
	DefaultListModel<String> add_res_list = new DefaultListModel<String>();
	DefaultListModel<String> add_userres_list = new DefaultListModel<String>();
	ArrayList<String> main_list = new ArrayList<String>();
	ArrayList<String> sub_list = new ArrayList<String>();
	ArrayList<String> attribute_list = new ArrayList<String>();
	ArrayList<String> res_list = new ArrayList<String>();
	static Connection con = null;
	PreparedStatement PreState = null;
	HashMap<Integer,String> Business_map;
	HashMap<Integer,String> user_map;
	String main_query = "", cat_query = "",attribute_query = "";
	
	//Class to establish data connection
	public static void connect() 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "scott", "tiger");
			if(con != null)
			{
				System.out.println("Database connected Successfully");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
// The method which builds the GUI
	@SuppressWarnings("unchecked")
	initialize() throws IOException {

		Business_map = new HashMap<Integer,String>();
		user_map = new HashMap<Integer,String>();

		// Pane for MainCategory
		cat_list = new JList<String>();
		JLabel mainCategoryLabel = new JLabel("Main Categories");
		Font f = mainCategoryLabel.getFont();
		mainCategoryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel mainCategoryTitle = new JPanel();
		mainCategoryTitle.setBackground(Color.decode(heading));
		mainCategoryTitle.add(mainCategoryLabel);
		JScrollPane mainCategoryContent = new JScrollPane();
		JSplitPane mainCategoryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainCategoryTitle, mainCategoryContent);
		mainCategoryPane.setEnabled(false);
		mainCategoryContent.setViewportView(cat_list);

		//Pane for Sub Category  
		sub_cat_list = new JList<>();
		JLabel subCategoryLabel = new JLabel("Sub-Categories");
		subCategoryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel subCategoryTitle = new JPanel();
		subCategoryTitle.setBackground(Color.decode(heading));
		subCategoryTitle.add(subCategoryLabel);
		JScrollPane subCategoryContents = new JScrollPane();
		JSplitPane subCategoryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, subCategoryTitle, subCategoryContents);
		subCategoryPane.setEnabled(false);
		subCategoryContents.setViewportView(sub_cat_list);

		//Pane for Attribute 
		attri_list = new JList<>();
		JLabel attributeLabel = new JLabel("Attributes");
		attributeLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel attributeTitle = new JPanel();
		attributeTitle.setBackground(Color.decode(heading));
		attributeTitle.add(attributeLabel);
		JScrollPane attributeContents = new JScrollPane();
		JSplitPane attributePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, attributeTitle, attributeContents);
		attributePane.setEnabled(false);
		attributeContents.setViewportView(attri_list);
		
		
		// Pane for Check-in panel  
		JPanel check_in = new JPanel();
		check_in.setLayout(null);
		JLabel lblSearchFor = new JLabel("SEARCH FOR:");
		lblSearchFor.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSearchFor.setBounds(10, 11, 162, 14);
		check_in.add(lblSearchFor);
		choice_box = new JComboBox(select);
		choice_box.setBounds(74, 27, 252, 20);
		check_in.add(choice_box);
		choice_box.setSelectedIndex(1);


		// Panel for Review
		JPanel reviewPanel = new JPanel();
		reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
		JLabel reviewLabel = new JLabel("Review");
		reviewLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel reviewTitle = new JPanel();
		reviewTitle.setBackground(Color.decode(heading));
		reviewTitle.add(reviewLabel);
		JScrollPane reviewContents = new JScrollPane();
		JSplitPane reviewPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, reviewTitle, reviewContents);
		reviewPane.setEnabled(false);
		reviewContents.setViewportView(reviewPanel);

		//This gets date from Spinner 

		JLabel fromdateLabel = new JLabel("From Date");
		reviewPanel.add(fromdateLabel);
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(1900, 04, 30);
		Calendar toCal = Calendar.getInstance();
		toCal.set(2018, 06, 02);
		Date fromdatedate = fromCal.getTime();
		SpinnerDateModel fromdatesm = new SpinnerDateModel(fromdatedate, null, null, Calendar.HOUR_OF_DAY);
		from_date_spinner = new JSpinner(fromdatesm);
		from_date = new JSpinner.DateEditor(from_date_spinner, "yyyy-MM-dd");
		from_date_spinner.setEditor(from_date);
		reviewPanel.add(from_date_spinner);
		JLabel todateLabel = new JLabel("To Date");
		reviewPanel.add(todateLabel);
		Calendar cal1 = Calendar.getInstance();
		Date todatedate = toCal.getTime();
		System.out.println(todatedate);
		SpinnerDateModel todatesm = new SpinnerDateModel(todatedate, null, null, Calendar.HOUR_OF_DAY);
		to_date_spinner = new JSpinner(todatesm);
		to_date = new JSpinner.DateEditor(to_date_spinner, "yyyy-MM-dd");
		to_date_spinner.setEditor(to_date);		
		reviewPanel.add(to_date_spinner);

		JLabel starsLabel = new JLabel("Stars:");
		reviewPanel.add(starsLabel);

		start_box = new JComboBox(review);
		reviewPanel.add(start_box);

		JLabel startValueLabel = new JLabel("Value");
		reviewPanel.add(startValueLabel);

		value_2 = new JTextField();
		reviewPanel.add(value_2);

		JLabel votesLabel = new JLabel("Votes:");
		reviewPanel.add(votesLabel);

		votes_box = new JComboBox(review);
		reviewPanel.add(votes_box);
		JLabel votesValueLabel = new JLabel("Value");
		reviewPanel.add(votesValueLabel);
		vote_value = new JTextField();
		reviewPanel.add(vote_value);

		// Pane Generation for Business Results
		result_table = new DefaultTableModel();
		business_table = new JTable();
		business_table.setModel(result_table);
		result_table.addColumn("Business Name");
		result_table.addColumn("City");
		result_table.addColumn("State");
		result_table.addColumn("Stars");

		JLabel resultlabel = new JLabel("Business Results");
		resultlabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel resultTitle = new JPanel();
		resultTitle.setBackground(Color.decode(heading));
		resultTitle.add(resultlabel);
		JScrollPane result = new JScrollPane(business_table);
		result_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, resultTitle, result);
		result_pane.setEnabled(false);

		//Pane Generation for user List 
		user_panel = new JPanel();
		user_panel.setLayout(null);
		JLabel userLabel = new JLabel("Users");
		userLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		userLabel.setBackground(Color.decode(heading));
		JPanel userTitle = new JPanel();
		userTitle.add(userLabel);
		userTitle.setBackground(Color.decode(heading));
		JScrollPane userContents = new JScrollPane();
		JSplitPane userPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userTitle, userContents);
		userPane.setEnabled(false);
		userContents.setViewportView(user_panel);

		JLabel lblMemberSince = new JLabel("MEMBER SINCE");
		lblMemberSince.setBounds(20, 52, 91, 14);
		user_panel.add(lblMemberSince);

		member_since = new JTextField();
		member_since.setBounds(120, 49, 80, 20);
		user_panel.add(member_since);

		JLabel lblReviewCount = new JLabel("REVIEW COUNT");
		lblReviewCount.setBounds(20, 100, 91, 14);
		user_panel.add(lblReviewCount);

		review_countbox = new JComboBox(review);
		review_countbox.setBounds(20, 115, 91, 14);
		user_panel.add(review_countbox);

		count_value = new JTextField();
		count_value.setBounds(120, 100, 80, 20);
		user_panel.add(count_value);

		JLabel lblNumberOfFriends = new JLabel("FRNDS_COUNT");
		lblNumberOfFriends.setBounds(20, 140, 91, 14);
		user_panel.add(lblNumberOfFriends);
		
		frnds_countbox = new JComboBox(review);
		frnds_countbox.setBounds(20, 160, 91, 14);
		user_panel.add(frnds_countbox);

		friend_count = new JTextField();
		friend_count.setBounds(120, 140, 80, 20);
		user_panel.add(friend_count);

		JLabel lblAverageStars = new JLabel("AVG_STARS");
		lblAverageStars.setBounds(20, 185, 91, 14);
		user_panel.add(lblAverageStars);

		star_box = new JComboBox(review);
		star_box.setBounds(20, 210, 91, 14);
		user_panel.add(star_box);

		start_value = new JTextField();
		start_value.setBounds(120, 185, 80, 20);
		user_panel.add(start_value);


		JLabel andOrLabel = new JLabel("Select:");
		user_panel.add(andOrLabel);

		and_box = new JComboBox(select);
		user_panel.add(and_box);

		// Pane for Execute Query Box
		query_field = new JTextArea();

		JLabel QueryLabel = new JLabel("Query");
		QueryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel QueryTitle = new JPanel();
		QueryTitle.setBackground(Color.decode(heading));
		QueryTitle.add(QueryLabel);
		JScrollPane QueryContents = new JScrollPane();
		JSplitPane QueryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, QueryTitle, QueryContents);
		QueryPane.setEnabled(false);
		QueryContents.setViewportView(query_field);

		//Pane for "execute Query" button
		JPanel executePane = new JPanel();
		Business_btn = new JButton("Business Query");
		Business_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				executeQueryActionPerformed(evt);
			}
		});
		executePane.add(Business_btn);

		User_btn = new JButton("User Query");
		User_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				userQuerryButtonActionPerformed(evt);
			}
		});
		executePane.add(User_btn);

		//Pane for "Exit" button
		//JPanel closePane = new JPanel();
		Exit_btn = new JButton("Exit");
		Exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		executePane.add(Exit_btn);

		// Pane Generation for User Results
		userresult_table = new DefaultTableModel();
		user_table = new JTable();
		user_table.setModel(userresult_table);
		userresult_table.addColumn("Name");
		userresult_table.addColumn("Yelping Since");
		userresult_table.addColumn("Stars");

		JLabel userresultlabel = new JLabel("User Results");
		userresultlabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel userresultTitle = new JPanel();
		userresultTitle.setBackground(Color.decode(heading));
		userresultTitle.add(userresultlabel);
		JScrollPane userresult = new JScrollPane(user_table);
		userresult_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userresultTitle, userresult);

		userresult_pane.setEnabled(false);

		// Creating Top Panes
		// Creating a Pane for Main and Sub Categories and Attributes
		JSplitPane firstColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainCategoryPane, subCategoryPane);
		firstColumnPane.setDividerLocation(200);
		firstColumnPane.setEnabled(false);

		JSplitPane secondAttriColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, firstColumnPane, attributePane);
		secondAttriColumnPane.setDividerLocation(400);
		secondAttriColumnPane.setEnabled(false);		

		JSplitPane mainTopPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, reviewPane, result_pane );
		mainTopPane.setDividerLocation(300);
		mainTopPane.setEnabled(false);

		// Creating Bottom Panes
		// Creating a Pane for Main and Sub Categories
		JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, QueryPane, executePane);
		buttonSplitPane.setDividerLocation(400);;
		buttonSplitPane.setEnabled(false);

		JSplitPane bottomFirstColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPane, buttonSplitPane);
		bottomFirstColumnPane.setDividerLocation(300);
		bottomFirstColumnPane.setEnabled(false);

		JSplitPane bottomSecondColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bottomFirstColumnPane, userresult_pane);
		bottomSecondColumnPane.setDividerLocation(300);

		JSplitPane mainBottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bottomFirstColumnPane, bottomSecondColumnPane);
		mainBottomPane.setDividerLocation(600);
		mainBottomPane.setEnabled(false);
		
		JSplitPane mainPane= new JSplitPane(JSplitPane.VERTICAL_SPLIT,secondAttriColumnPane,check_in);
		mainPane.setDividerLocation(300);
		JSplitPane secondPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,mainPane,mainTopPane);
		secondPane.setDividerLocation(600);
		JSplitPane MainPane= new JSplitPane(JSplitPane.VERTICAL_SPLIT,secondPane,mainBottomPane);
		MainPane.setDividerLocation(400);
		MainPane.setEnabled(false);
		con_str = "INTERSECT";

		getContentPane().add(MainPane);
		
		// After Creating GUI perform the query operation
		connect();
		action_update();
		populate_main();
		populate_sub();
		populate_attribute();
		mouse_listener_attribute();
		open_review();
		populate_userResult();
		open_user();
	}

	private void populate_main() {
		
		value_2.setText("");
		vote_value.setText("");
		
		main_query = "";
		main_query = "SELECT DISTINCT C_NAME FROM B_MAIN_CATEGORY ORDER BY C_NAME\n";
		try {
			ResultSet rs11 = null;
			PreState=con.prepareStatement(main_query);
			rs11 = PreState.executeQuery(main_query);
			int i = 0;
			while(rs11.next())
			{
				if(!add_mainList.contains(rs11.getString("C_NAME")))
				{
					add_mainList.addElement(rs11.getString("C_NAME"));
				}
			}
			PreState.close();
			rs11.close();
		} catch(Exception ex) {
			System.out.println(ex);
		}
		cat_list.setModel(add_mainList);
	}
	
	
	private void populate_sub() {
		MouseListener mainCategoryMouseListener = new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {
				value_2.setText("");
				vote_value.setText("");

				if (business_table.getRowCount() > 0) {
					for (int i = business_table.getRowCount() - 1; i > -1; i--) {
						result_table.removeRow(i);
					}
				}
				
				cat_query = "";
				add_subList.clear();
				Add_attriList.clear();
				main_list = (ArrayList<String>) cat_list.getSelectedValuesList();
				
				cat_query = "SELECT DISTINCT BSC.C_NAME from b_main_category bmc\r\n" + 
						"inner join b_sub_category bsc on bmc.bid=bsc.bid\r\n" + 
						"where BMC.C_NAME = '" + main_list.get(0)+ "' ";
				
				if (con_str == "INTERSECT") {
					for(int i=1;i<main_list.size();i++) {
						cat_query = cat_query + " INTERSECT SELECT DISTINCT BSC.C_NAME from b_main_category bmc\r\n" + 
														"inner join b_sub_category bsc on bmc.bid=bsc.bid \r\n" + 
														"where BMC.C_NAME = '" + main_list.get(i) + "'";
					}
				}
				else if (con_str == "UNION") {
					for(int i=1;i<main_list.size();i++) {
						cat_query = cat_query + " UNION SELECT DISTINCT BSC.C_NAME from b_main_category bmc\r\n" + 
														"inner join b_sub_category bsc on bmc.bid=bsc.bid \r\n" + 
														"where BMC.C_NAME = '" + main_list.get(i) + "'";
					}
				} 	
				try {
				
					ResultSet rs12 = null;
					PreState=con.prepareStatement(cat_query);
					rs12 = PreState.executeQuery(cat_query);
					
					while(rs12.next())
					{
						if(!add_subList.contains(rs12.getString("C_NAME"))){
							add_subList.addElement(rs12.getString("C_NAME"));
						}
					}
					PreState.close();
					rs12.close();
				}catch(Exception ex) {
						System.out.println(ex);
				}
				sub_cat_list.setModel(add_subList);				
			}
		};
		cat_list.addMouseListener(mainCategoryMouseListener);
	}

	
	
	private void populate_attribute() {
		MouseListener subCategoryMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				value_2.setText("");
				vote_value.setText("");

				attribute_query= "";
				Add_attriList.clear();
				
				sub_list = (ArrayList<String>) sub_cat_list.getSelectedValuesList();
				main_list = (ArrayList<String>) cat_list.getSelectedValuesList();
				
				attribute_query = "select distinct ba.a_name from b_main_category bmc\r\n" + 
						"inner join b_sub_category bsc on bmc.bid=bsc.bid\r\n" +
						"inner join b_attributes ba on bmc.bid=ba.bid\r\n "
						+ "Where bmc.c_name = '" + main_list.get(0) 
						+ "' and "
						+ "bsc.c_name = '" + sub_list.get(0) + "'\n";
				if (sub_list.size() > 0) {
				for(int i=0;i<main_list.size();i++) {
					for(int j=1;j<sub_list.size();j++) {
						attribute_query = attribute_query + con_str + " select distinct ba.a_name from b_main_category bmc\r\n"
									+ "inner join b_sub_category bsc on bmc.bid=bsc.bid\r\n"
									+ "inner join b_attributes ba on bmc.bid=ba.bid\r\n "
									+ "Where bmc.c_name = '" + main_list.get(i) 
									+ "' and "
									+ "bsc.c_name = '" + sub_list.get(j) + "' \n";
					}
					break;
				}
				}
				for(int i=1;i<main_list.size();i++) {
					for(int j=0;j<sub_list.size();j++) {
						attribute_query = attribute_query + con_str + " select distinct ba.a_name from b_main_category bmc\r\n"
									+ "inner join b_sub_category bsc on bmc.bid=bsc.bid\r\n"
									+ "inner join b_attributes ba on bmc.bid=ba.bid\r\n "
									+ "Where bmc.c_name = '" + main_list.get(i) 
									+ "' and "
									+ "bsc.c_name = '" + sub_list.get(j) + "' \n";
					}
				}
				
				try {
					ResultSet rs12 = null;
					PreState=con.prepareStatement(attribute_query);
					rs12 = PreState.executeQuery(attribute_query);
					
					while(rs12.next())
					{
						if(!Add_attriList.contains(rs12.getString("A_NAME"))){
							Add_attriList.addElement(rs12.getString("A_NAME"));
						}
					}
					PreState.close();
					rs12.close();
				}catch(Exception ex) {
						System.out.println(ex);
				}
				attri_list.setModel(Add_attriList);				
			}
		};
		sub_cat_list.addMouseListener(subCategoryMouseListener);
	}
	
	
	
	private void mouse_listener_attribute() {
		MouseListener attributeMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				value_2.setText("");
				vote_value.setText("");
				attribute_list = (ArrayList<String>) attri_list.getSelectedValuesList();
			}
		};
		attri_list.addMouseListener(attributeMouseListener);		
	}
	
	
	
	@SuppressWarnings("unlikely-arg-type")
	private void executeQueryActionPerformed(ActionEvent e) {

		if (business_table.getRowCount() > 0) {
			for (int i = business_table.getRowCount() - 1; i > -1; i--) {
				result_table.removeRow(i);
			}
		}

		if(!main_list.isEmpty()) {
		
			add_res_list.clear();
			String firstFinalQuery = "";
			
			firstFinalQuery = "SELECT distinct BS.B_NAME, BS.CITY, BS.STATE, BS.STARS, BS.BID from business BS\r\n";
			
			if(!main_list.isEmpty()) {
				firstFinalQuery += "inner join b_main_category bms on bs.bid=bms.bid\r\n";
				if(!sub_list.isEmpty()) {
					firstFinalQuery += "inner join b_sub_category bsc on bsc.bid=bms.bid\r\n";
					if(!attribute_list.isEmpty()) 
						firstFinalQuery += "inner join b_attributes ba on ba.bid=bsc.bid\r\n";
				}
			}
			
			firstFinalQuery += " where \r\n";
			String subQuery = firstFinalQuery;
			
			if(!main_list.isEmpty()) {
				for(int i=0; i<main_list.size();i++) {	
					if(!sub_list.isEmpty()) {
						for(int j=0;j<sub_list.size();j++) {
							
							//if attribute is selected along with sub and cate
							if(!attribute_list.isEmpty()) {
								for(int k=0;k<attribute_list.size(); k++) {
									firstFinalQuery += " bms.c_name= '" +main_list.get(i)+ "' and \r\n"+ 
											"bsc.c_name= '" +sub_list.get(j)+ "' and \r\n"+
											"ba.a_name= '" +attribute_list.get(k)+ "' \r\n";
									if((i+1 < main_list.size()) || 
											(j+1 < sub_list.size()) || 
											(k+1 < attribute_list.size())) {
										firstFinalQuery += con_str +" "+ subQuery;
									}
								}	
							}
							
							//if just sub and main is selected
							if(attribute_list.isEmpty()) {
								firstFinalQuery += " bms.c_name= '" +main_list.get(i)+ "' and \r\n"+ 
										"bsc.c_name= '" +sub_list.get(j)+ "' \r\n";
								if((i+1 < main_list.size()) || 
										(j+1 < sub_list.size())) {
									firstFinalQuery += con_str +" "+ subQuery;
								}
							}
						}
					}
					
					//if its just main category
					if(sub_list.isEmpty()) {
						firstFinalQuery += " bms.c_name= '" +main_list.get(i)+ "' \r\n"; 
						if((i+1 < main_list.size()))
							firstFinalQuery += con_str +" "+ subQuery;
					}
				}
			}
					
			int tracker=0;
			String finalQuery="";
			
			//the below Query
			
			if(!value_2.getText().isEmpty() || !vote_value.getText().isEmpty() ||
					(!from_date.getTextField().getText().toString().equals("1900-05-30")) ||
					(!to_date.getTextField().getText().toString().equals("2018-07-02"))) {
				int votesTracker=0;
				if (!vote_value.getText().isEmpty()) {
					tracker=1;
					finalQuery = "select * from (" + firstFinalQuery + ") abc where abc.bid in (select r.bid from reviews r where r.FUNNY_VOTE + "
							+ "r.USEFUL_VOTE + r.COOL_VOTE " + votes_box.getSelectedItem() + vote_value.getText() + ") \n";
					System.out.println("inside votes yes");
					votesTracker=1;
				}
			
				int fromDateFlag=0;	
				System.out.println("de ..."+to_date);
				
				if (!from_date.getTextField().getText().toString().equals("1900-05-30")) {
					fromDateFlag=1;
					tracker=1;
					if(votesTracker==1) {
						System.out.println("inside votes and from date yes");
						finalQuery = "select * from (" + firstFinalQuery + ") abc where abc.bid in (select r.bid from reviews r where r.FUNNY_VOTE + "
							+ "r.USEFUL_VOTE + r.COOL_VOTE " + votes_box.getSelectedItem() + vote_value.getText() 
							+ " and r.R_DATE>to_date('"+ from_date.getTextField().getText() +"','yyyy-MM-dd')) \n";
				}
				else {
					System.out.println("inside votes no fromdate yes");
					tracker=1;
				finalQuery += "select * from (" + firstFinalQuery + ") abc "
						+ "where abc.bid in (select r.bid from reviews r "
						+ "where R.R_DATE > to_date('"+ from_date.getTextField().getText() +"','yyyy-MM-dd') \n";
				}
					fromDateFlag=1;
			}
			
			if(!to_date.getTextField().getText().toString().equals("2018-07-02")) {
				if(votesTracker==1 && fromDateFlag==1) {
					tracker=1;
					System.out.println("inside vote and fromdate yes and to date yes");
					finalQuery = "select * from (" + firstFinalQuery + ") abc where abc.bid in (select r.bid from reviews r where (r.FUNNY_VOTE "
							+ "+ r.USEFUL_VOTE + r.COOL_VOTE) " + votes_box.getSelectedItem() + vote_value.getText()
							+ " and r.R_DATE>to_date('"+ from_date.getTextField().getText() +"','yyyy-MM-dd') \n"
							+ "and r.R_DATE<to_date('"+ to_date.getTextField().getText() +"','yyyy-MM-dd')) \n";
				}
				else if(votesTracker==1 && fromDateFlag==0) {
					System.out.println("inside votes yes fromdate no to date yes");
					tracker=1;
					finalQuery = "select * from (" + firstFinalQuery + ") abc where abc.bid in (select r.bid from reviews r where (r.FUNNY_VOTE "
							+ "+ r.USEFUL_VOTE + r.COOL_VOTE) " + votes_box.getSelectedItem() + vote_value.getText()
							+ " and r.R_DATE < to_date('"+ to_date.getTextField().getText() +"','yyyy-MM-dd')) \n ";
				}
				else if(votesTracker==0 && fromDateFlag==1) {
					System.out.println("inside votes no fromdate yes to date yes");
					tracker=1;
					finalQuery = "select * from (" + firstFinalQuery + ") abc "
							+ "where abc.bid in (select r.bid from reviews r "
							+ "where R.R_DATE > to_date('"+ from_date.getTextField().getText() +"','yyyy-MM-dd') \n"
							+ "and r.R_DATE < to_date('"+ to_date.getTextField().getText() +"','yyyy-MM-dd')) \n";
				}
				else {
					System.out.println("insdie todate yes");
					tracker=1;
					finalQuery = "select * from (" + firstFinalQuery + ") abc "
							+ "where abc.bid in (select r.bid from reviews r "
							+ "where R.R_DATE < to_date('"+ to_date.getTextField().getText() +"','yyyy-MM-dd')) \n";
				}
				tracker=1;
			}
			
			if (!value_2.getText().isEmpty()) {
				float starsCount = Float.parseFloat((String) value_2.getText());
				if(tracker==1) {
					finalQuery += " and abc.stars " +start_box.getSelectedItem() + starsCount +" \n"  ;
				}
				else
					finalQuery = "select * from (" + firstFinalQuery + ") abc where stars" + 
					 start_box.getSelectedItem() + starsCount +" \n" ;
				tracker=1;
			}
			
			}	
			if(finalQuery != "") {
				firstFinalQuery = finalQuery;
			}
			
			System.out.println(firstFinalQuery);
			
			try {
				ResultSet rs13 = null;
				PreState=con.prepareStatement(firstFinalQuery);
				rs13 = PreState.executeQuery(firstFinalQuery);
				int i =0;
				String[] rowObj = new String[4];
				while(rs13.next())
				{
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					result_table.addRow(rowObj);
					Business_map.put(i++, rs13.getString("BID"));
				}
				query_field.setText(firstFinalQuery);
				PreState.close();
				rs13.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}	
	
	private void open_review() { 
		business_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JFrame frame = new JFrame("JFrame Example");

					JPanel panel = new JPanel();
					panel.setLayout(new FlowLayout());
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					System.out.println("Inside Query");
					String reviewFrameQuery = "";
					reviewFrameQuery = "SELECT R.R_DATE, R.STARS, R.R_TEXT, Y.USER_NAME, R.USEFUL_VOTE, R.FUNNY_VOTE, R.COOL_VOTE\r\n" + 
							"FROM REVIEWS R, BUSINESS B, YELP_USER Y\r\n" + 
							"WHERE R.USER_ID = Y.USER_ID AND B.BID = R.BID AND B.BID = '"+Business_map.get(row)+"'"; 
					
					if(!vote_value.getText().isEmpty()) {
						reviewFrameQuery += " and ((r.FUNNY_VOTE + r.USEFUL_VOTE + r.COOL_VOTE) "
								+ votes_box.getSelectedItem() + vote_value.getText()+" )";
					}
					if(!value_2.getText().isEmpty()) {
						reviewFrameQuery += " and r.stars "+ start_box.getSelectedItem() 
						+ value_2.getText() +" \n"  ;
					}
									
					review_JTable = new JTable();
					review_JTable.setBounds(300,400,1000,1000);

					review_table = new DefaultTableModel();
					review_JTable.setModel(review_table);
					review_table.addColumn("Review Date");
					review_table.addColumn("Stars");
					review_table.addColumn("Review");
					review_table.addColumn("User Name");
					review_table.addColumn("Useful Vote");
					review_table.addColumn("Funny Vote");
					review_table.addColumn("Cool Vote");

					JScrollPane reviewResultPane = new JScrollPane(review_JTable);
			
					panel.add(reviewResultPane);
			
					String[] rowObj = new String[7];
					try {
						ResultSet rs13 = null;
						System.out.println(reviewFrameQuery);
						PreState=con.prepareStatement(reviewFrameQuery);
						rs13 = PreState.executeQuery(reviewFrameQuery);

						while(rs13.next())
						{

							rowObj = new String[] {rs13.getString("R_DATE"), rs13.getString("STARS"), rs13.getString("R_TEXT"), rs13.getString("USER_NAME"), rs13.getString("USEFUL_VOTE"), rs13.getString("FUNNY_VOTE"), rs13.getString("COOL_VOTE")};
				
							review_table.addRow(rowObj);


						}
						PreState.close();
						rs13.close();
					} catch(Exception ex) {
						System.out.println(ex);
					}
					frame.add(panel);
					frame.setSize(1800, 1200);
					frame.setLocationRelativeTo(null);
			
					frame.setVisible(true);
				}
			}
		});

	}

	private void populate_userResult() {
		MouseListener userMouseListener = new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{

				if (e.getClickCount() == 1) {
					if (business_table.getRowCount() > 0) {
						for (int i = business_table.getRowCount() - 1; i > -1; i--) {
							result_table.removeRow(i);
						}
					}
				}

				System.out.println("value of user field:" + count_value.getText());
				if((Integer.parseInt((String) count_value.getText())) !=0) {
					query_field.setText(" ");
					if (business_table.getRowCount() > 0) {
						for (int i = business_table.getRowCount() - 1; i > -1; i--) {
							result_table.removeRow(i);
						}
					}

					String FinalSubQuery = "";
					int reviewCount = Integer.parseInt((String) count_value.getText());

					System.out.println("Inside 4th Query");
					FinalSubQuery += "SELECT DISTINCT YU.USER_NAME, YU.YELPING_SINCE, YU.AVERAGE_STARS \r\n" + 
							"FROM YELP_USER YU \r\n" + 
							"WHERE YU.REVIEW_COUNT " + review_countbox.getSelectedItem() +
							" " + reviewCount;


					System.out.println("--------");
					System.out.println(FinalSubQuery);
					System.out.println("--------");

					query_field.setText(FinalSubQuery);

					try {
						ResultSet rs13 = null;
						PreState=con.prepareStatement(FinalSubQuery);
						rs13 = PreState.executeQuery(FinalSubQuery);
						int i =0;
						String[] rowObj = new String[4];

						while(rs13.next()){
							rowObj = new String[] {rs13.getString("USER_NAME"), rs13.getString("YELPING_SINCE"), rs13.getString("AVERAGE_STARS")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							result_table.addRow(rowObj);
							Business_map.put(i++, rs13.getString("BID"));
						}
						PreState.close();
						rs13.close();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}	
				}	
			}
		};
		user_panel.addMouseListener(userMouseListener);
	}

	private void userQuerryButtonActionPerformed(ActionEvent evt) {


		int tracker=0;
		
		if (userresult_table.getRowCount() > 0) {
			for (int i = userresult_table.getRowCount() - 1; i > -1; i--) {
				userresult_table.removeRow(i);
			}
		}
		
		String UserQuery= "select distinct y.user_name, y.yelping_since, y.average_stars from yelp_user y"
				+ " where\r\n";

		if (!member_since.getText().isEmpty()) {
			UserQuery += " y.yelping_since > to_date('"  + member_since.getText() +"','dd-mm-yyyy') \n";
			tracker=1;
			
		}
		
		if(!count_value.getText().isEmpty()) {
			int reviewCount = Integer.parseInt((String) count_value.getText());
			if(tracker == 1)
				UserQuery += " "+and_box.getSelectedItem()+" ";
			UserQuery += " y.review_count "+ review_countbox.getSelectedItem() +" "+ reviewCount;
			tracker=1;
		}
		
		if(!friend_count.getText().isEmpty()) {
			if(tracker == 1)
				UserQuery += " "+and_box.getSelectedItem()+" ";
			UserQuery += " y.user_id in (SELECT F.USER_ID FROM  FRIENDS F WHERE F.USER_ID = Y.USER_ID \r\n" + 
					" group by f.user_id having count(F.user_id) " + frnds_countbox.getSelectedItem() 
			 + friend_count.getText() +") ";
		}
		
		if(!start_value.getText().isEmpty()) {
			if(tracker == 1)
				UserQuery += " "+and_box.getSelectedItem()+" ";
			UserQuery += " Y.average_stars " + star_box.getSelectedItem() +
					" " + start_value.getText();
		}

		System.out.println("___________");
		System.out.println(UserQuery);
		System.out.println("___________");
		
		

			String FinalSubQuery = "";

			System.out.println("Inside 4th Query");

			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			query_field.setText(UserQuery);

			try {
				ResultSet rs13 = null;
				PreState=con.prepareStatement(UserQuery);
				rs13 = PreState.executeQuery(UserQuery);
				int i = 0;
				String[] rowObj = new String[3];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("USER_NAME"), rs13.getString("YELPING_SINCE"), rs13.getString("AVERAGE_STARS")};
					userresult_table.addRow(rowObj);
					user_map.put(i++, rs13.getString("USER_NAME"));
				}
				PreState.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}	
		}	
	

	private void open_user() { 
		user_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JFrame userframe = new JFrame("User JFrame");

					JPanel userpanel = new JPanel();
					userpanel.setLayout(new FlowLayout());
					JTable usertarget = (JTable)e.getSource();
					int row = usertarget.getSelectedRow();
					System.out.println("Inside Query");
					System.out.println(user_map.get(row));

					String userquerry = "SELECT R.R_DATE, R.STARS, R.R_TEXT, Y.USER_NAME, Y.USER_ID, R.USEFUL_VOTE, R.FUNNY_VOTE, R.COOL_VOTE\r\n" + 
							"FROM REVIEWS R, YELP_USER Y\r\n" + 
							"WHERE R.USER_ID = Y.USER_ID AND Y.USER_NAME LIKE '" + user_map.get(row)+"'";

					userreview_JTable = new JTable();
					userreview_JTable.setBounds(300,400,1000,1000);

					userreview_table = new DefaultTableModel();
					userreview_JTable.setModel(userreview_table);
					userreview_table.addColumn("Review Date");
					userreview_table.addColumn("Stars");
					userreview_table.addColumn("Review");
					userreview_table.addColumn("User Name");
					userreview_table.addColumn("Useful Vote");
					userreview_table.addColumn("Funny Vote");
					userreview_table.addColumn("Cool Vote");

					JScrollPane userreviewResultPane = new JScrollPane(userreview_JTable);
				
					System.out.println("--------");
					System.out.println(userquerry);
					System.out.println("--------");

					userpanel.add(userreviewResultPane);
					//  panel.add(button);
					String[] rowObj = new String[7];

					try {
						ResultSet rs13 = null;
						PreState=con.prepareStatement(userquerry);
						rs13 = PreState.executeQuery(userquerry);

						while(rs13.next())
						{

							rowObj = new String[] {rs13.getString("R_DATE"), rs13.getString("STARS"), rs13.getString("R_TEXT"), rs13.getString("USER_NAME"), rs13.getString("USEFUL_VOTE"), rs13.getString("FUNNY_VOTE"), rs13.getString("COOL_VOTE")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							userreview_table.addRow(rowObj);

						}
						PreState.close();
						rs13.close();
					} catch(Exception ex) {
						System.out.println(ex);
					}
					userframe.add(userpanel);
					userframe.setSize(1800, 1200);
					userframe.setLocationRelativeTo(null);
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					userframe.setVisible(true);
				}
			}
		});

	}

	private void action_update() {
		choice_box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				cat_list.clearSelection();
				add_subList.clear();
				Add_attriList.clear();
				add_res_list.clear();
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedBook = (String) combo.getSelectedItem();

				if (selectedBook.equals("AND")) {
					con_str = "INTERSECT";
				} else if (selectedBook.equals("OR")) {
					con_str = "UNION";
				}
			}
		});

	}
}

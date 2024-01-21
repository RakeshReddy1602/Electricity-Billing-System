package billingsystemgui;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
// import com.mysql.cj.protocol.Resultset;

public class DataBaseClass {
	
	WarningMessage warn = new WarningMessage();
	
	// used to detect if user has paid extra amount than bill
	static double extraPaid = 0;
	
	// creates a static Connection reference variable
	private static Connection connection = null;
	
	// database URL for connection
	String dbUrl = "jdbc:mysql://localhost:3306/billrecords";
	
	//user name of the connection
	String userName = "root";
	
	// password for the connection
	String password = "Rakesh6305393112";
	Border border = BorderFactory.createLineBorder(Color.black,2);
	
	
	/*
	 * This method setData validates the user id entered in user id field 
	 * It fetches the user data if user exists in the database
	 * or else displays appropriate warning message or error message to the user
	 */
	void setData(JTextField id,JTextField name,JTextField units,JTextField bill,JButton pay,JFrame frame) {
//		System.out.println("hi from here");
		boolean valid = true,userFound = false;
		try {
		
		// creates a connection through static method getConnection from DriverManager Class 
		connection = DriverManager.getConnection(dbUrl, userName, password);
//		System.out.println(connection == null);
		
		///SQL query for retrieving data form database
		String selectQuery = "select *from bill_data_records where user_id =";
		int userid = 0;
		try {
		userid = Integer.parseInt(id.getText());
		}catch(Exception e) {
			valid = false;
			warn.invalidId(frame);
			id.setText("Enter 6 digit User Id");
		}
		selectQuery += String.valueOf(userid);

// using rowset to hold the result from the database
		JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
		rowSet.setUrl("jdbc:mysql://localhost:3306/billrecords");
		rowSet.setUsername("root"); 
		rowSet.setPassword("Rakesh6305393112");
		rowSet.setCommand(selectQuery);
		rowSet.execute();
		
		// if the user exists in the records,
		  if(rowSet.next()) {
			  
			 //making the usefFound = true
			  userFound = true;
			  
			 // assume id is valid
			  valid = true;
			  
			// sets the detail of the user in the respective text fields
			name.setText(rowSet.getString(2));
			units.setText(rowSet.getString(3));
			bill.setText(rowSet.getString(4));
			
			//getting the extra paid amount paid by the user if he has already paid it
			DataBaseClass.extraPaid = Double.parseDouble(rowSet.getString(5));
		  }
		  
		  // if user id is not valid or user does not exists, does not enable payment option
		  id.setEditable(false);
		  if(valid && userFound) {
			  
			  // enables payment option if valid data is given and id given found in the records
			  pay.setEnabled(true);
		  }
		  if((!userFound)&&(valid)) {
			  
			  //if valid data is given but user is not found,display user not found message
			  warn.userNotFound(frame);
			  id.setText("Enter 6 digit User Id");
		  }
	
		}catch(Exception ex) {
			System.out.println(ex);
		}

}
	
	
	/*
	 * This method makes the payment and updates the bill fields and extra_paid fields in the database
	 */
	void updatePayment(double due,double extra,JTextField userid) {
		
		try {
		String dueAmount = String.valueOf(due);
		
		// updating extra_paid amount
		extra += DataBaseClass.extraPaid;
		
		String id = userid.getText();
		
		
		//query to update bill field
		String updateQuery = "update bill_data_records set bill ="+dueAmount+" where user_id = "+id;
		
		//query to update extra_paid field
		String updateQuery2 = "update bill_data_records set extra_paid = ? where user_id = "+id;
		
		//using prepared statement to eliminate parsing,compiling of the query each time database operation is performed
		PreparedStatement statement = connection.prepareStatement(updateQuery);
		PreparedStatement statement2 = connection.prepareStatement(updateQuery2);
		statement2.setDouble(1, extra);
		
		// using executeUpdate() method to execute query,
		//it will returns number of rows effected by the query
 		int rows = statement.executeUpdate();
		int rows2  = statement2.executeUpdate();
		if(rows> 0 || rows2 > 0) {
			System.out.println("SUCCESS");
		}
		
		
		}catch(Exception ex) {
			
		}
	}
	
	
	/*
	 * The method fetchImage() retrieves photo id of the user form from the database
	 *  It will sets the label fit to the image and returns the label
	 */
	JLabel fetchImage(JPanel paymentPanel,String userid,JLabel img) {
		
		img = new JLabel();
		
		//creates a new label for holding the image
		img = new TextLabels().createLabel("",225,90,125,125, paymentPanel);
		img.setBackground(Color.BLACK);
		img.setBorder(border);
		img.setOpaque(true);
		try {
		int id = Integer.parseInt(userid);
//		System.out.println("id  " + id);
		String imageQuery = "select owner_photo from bill_data_records where user_id = ? ";
		PreparedStatement imgSt = connection.prepareStatement(imageQuery);
		imgSt.setInt(1, id);
		System.out.println(imgSt);
		
		// obtain the path where images are stored in the disk
		ResultSet set = imgSt.executeQuery();
		set.next();
		
		System.out.println(set == null);
		
		// obtain the path of the image to set the image to the label
		String path = set.getString(1);
		
		// creates a new image icon with path obtained from the database
		ImageIcon icon = new ImageIcon(path);
		System.out.println(path);
		
		
		//  making the image to fit with the size of the label created
		icon = new ImageIcon(icon.getImage().getScaledInstance(150,150,Image.SCALE_DEFAULT));
//		imageLabel.setIcon(icon);
		
		
		// sets the icon (photo id ) to the label
		img.setIcon(icon);
		
				
				System.out.println("calld");
		}catch(Exception e) {System.out.println(e+"\nexception catched");}
		
		// returns the label with the icon
		return img;
	}
	
}
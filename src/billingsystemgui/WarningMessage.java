package billingsystemgui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * This class contains Warning Message to be displayed based upon data entered by the user while using application
 */
public class WarningMessage {

	
	// display if valid data is entered in user id field but is is not found in the database
	public void userNotFound(JFrame frame) {
		JOptionPane.showMessageDialog(frame,"Contact +91 6305393112","User Not Found",JOptionPane.WARNING_MESSAGE);

	}
	
	// displays error message if invalid id is given by the user
	public void invalidId(JFrame frame) {
		JOptionPane.showMessageDialog(frame,"Invalid ID","ERROR",JOptionPane.ERROR_MESSAGE);
	}
	
	public void amountNotEntered(JFrame frame) {
		JOptionPane.showMessageDialog(frame,"Enter Amount First","WARNING",JOptionPane.WARNING_MESSAGE);
	}
	
	// displays error message if invalid id is given by the users
	public void invalidData(JFrame frame) {
		JOptionPane.showMessageDialog(frame,"Invalid Data","ERROR",JOptionPane.ERROR_MESSAGE);
	}
}
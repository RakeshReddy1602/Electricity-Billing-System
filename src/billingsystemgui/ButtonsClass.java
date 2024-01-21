package billingsystemgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;

public class ButtonsClass {
	
	// creates a panel for the payment
	JPanel paymentPanel = new JPanel();
	
	// labels to label user id, payment amount, bill amount in the payment panel
	JLabel payTitle, userDetails, billDetails, amountLabel,idLabel;
	
	// label to display the photo id to the user
	JLabel imageLabel = new JLabel();
	
	// buttons in the payment panel
	JButton payBill, cancelButton, homeButton;
	
	// text field to enter the amount
	JTextField amount;
	Font myFont = new Font("Times New Roman", Font.PLAIN, 20);
	
	// creating DataBaseClass object necessary database operations
	DataBaseClass dataObj = new DataBaseClass();
	
	 // to create text labels
	TextLabels labObj = new TextLabels(); 
	
	// to create text fields
	TextFields textObj = new TextFields(); 
	
	// creating a new customized color object for panel
	Color col = new Color(192, 195, 90);
	
	// customize3d border
	Border border = BorderFactory.createLineBorder(Color.black, 1);
	boolean success = true;  // assign false if payment operation is false by user fault(invalid user id)
	String s, s2; // display messages to the user

	
	/*
	 *  The method createButton has the parameters
	 *  s - text for the button
	 *  x,y,width,height  - bounds for the button
	 *  panel - panel to which text field be added 
	 *  
	 *  It creates button with above properties and returns the instance of button
	 */
	public JButton createButton(String s, int x, int y, int width, int height, JPanel panel) {
		JButton button = new JButton(s);
		button.setBounds(x, y, width, height);
		button.setFocusable(false);
		button.setVisible(true);
		button.setFont(myFont);
		button.setBorder(border);
		button.setBackground(col);
		panel.add(button);
//		System.out.println(button == null);
		return button;
	}

	/*
	 * This method adds the action listener to the reset button 
	 * and defines actions to be performed when reset button is clicked
	 */
	void clickReset(JButton button, JButton pay, JTextField user, JTextField owner, JTextField units, JTextField bill) {
		
		// implemented through Anonymous class
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				// resets the text fields to the default texts 
				user.setText("Enter 6 digit User Id");
				user.setEditable(false);
				owner.setText("Owner");
				units.setText("Units Consumed");
				bill.setText("Bill");
				pay.setEnabled(false);

			}

		});
	}

	
	/*
	 * This method adds the action listener to the exit button 
	 * and defines actions to be performed when exit button is clicked
	 */
	void clickExit(JButton button, JFrame frame) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				/// dispose the frame
				frame.dispose();

			}

		});

	}

	/*
	 * This method adds the action listener to the fetch button 
	 * and defines actions to be performed when reset button is clicked
	 */
	void clickFetch(JFrame frame, JButton button, JButton pay, JTextField user, JTextField owner, JTextField units,
			JTextField bill) {
		WarningMessage warnObj = new WarningMessage();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DataBaseClass obj = new DataBaseClass();
				try {
					
					// tries to fetch data from database based upon user id entered by the user
					obj.setData(user, owner, units, bill, pay, frame);
					
					// retrieves image for the user
					imageLabel = dataObj.fetchImage(paymentPanel,user.getText(),imageLabel);
					
					// adds the photo id or imageLabel to the payment panel
					paymentPanel.add(imageLabel);
				} catch (Exception ex) {
					
					// display the invalid data if input is not matched
					warnObj.invalidData(frame);
					return;
				}

			}
		});
	}

	
	/*
	 * This method adds the action listener to the Pay Now button 
	 * and defines actions to be performed when Pay Now button is clicked
	 * This method creates a payment panel for the payment process
	 */
	void clickPayNow(JPanel main, JPanel btp, JButton button, JTextField userField, JTextField bill, JFrame frame) {

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// creates a payment panel
				paymentPanel.setLayout(null);
				
				// sets the bound according the frame
				paymentPanel.setBounds(0, 0, 400, 400);
				paymentPanel.setBackground(Color.pink);

				
				// adds the necessary text labels to the field
				// uses createLabel method in the text labels class
				payTitle = labObj.createLabel("PAYMENT", 100, 50, 200, 30, paymentPanel);

				s = "User Id : " + userField.getText();
				userDetails = labObj.createLabel(s, 50, 100, 200, 30, paymentPanel);

				s2 = "Bill : " + bill.getText();
				billDetails = labObj.createLabel(s2, 50, 150, 200, 30, paymentPanel);
				amountLabel = labObj.createLabel("Amount :", 25, 225, 100, 25, paymentPanel);
				idLabel = labObj.createLabel("Photo id", 250,220,100,20,paymentPanel);

				
				// creates payment button,cancel button and home button
				payBill = createButton("Pay Now", 40, 300, 125, 30, paymentPanel);
				cancelButton = createButton("Cancel", 170, 300, 100, 30, paymentPanel);
				homeButton = createButton("Home", 100, 350, 100, 30, paymentPanel);

				// creates amount text field for user input for bill payment
				amount = textObj.createField("0", 50, 250, 200, 30, 15, paymentPanel);
				amount.setEditable(false);

				
				// adds the action listeners to the cancel,cancel and home buttons
				clickAmount(amount);
				clickCancelHome(frame, main, btp);
				clickPayBill(payBill, frame, bill, userField);
//				dataObj.fetchImage(paymentPanel,userField.getText());
				paymentPanel.add(amount);
				paymentPanel.setVisible(true);
				frame.add(paymentPanel);

				// makes previous panels mainPanel and buttonPanel false
				btp.setVisible(false);
				main.setVisible(false);
				System.out.println("called next panel : " + amount.getText());

			}
		});

	}

	/*
	 *This method adds the action listener to the Pay Bill button 
	 * and defines actions to be performed when Pay Bill button is clicked 
	 */
	void clickPayBill(JButton button, JFrame frame, JTextField bill, JTextField userid) {
		WarningMessage warnObj = new WarningMessage();
		class MyPayListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				double deposit = 1;
				try {
					System.out.println("pay : " + amount.getText());
					
					// obtain amount entered by the user
					deposit = Double.parseDouble(amount.getText());

				} catch (NumberFormatException n) {
					
					// assign false to success indicating payment is not successful
					success = false;
					System.out.println("text exception: " + amount.getText());
					amount.setText("0");
//					amount = textO
					amount.setEditable(false);
					
					// display invalid data to user
					warnObj.invalidData(frame);

//					paymentPanel.remove(payBill);
//					payBill = createButton("Pay Now",40,300,125,30,paymentPanel);
					MyPayListener pobj = new MyPayListener();
					payBill.addActionListener(pobj);
					System.out.println("after setting text : " + amount.getText());
//					return;

				}
				if (success) {
					
					// if user pays amount less 0, display error message to user 
					if (deposit < 0) { 
						amount.setEditable(false);
						System.out.println("text : " + amount.getText());
						amount.setText("0");

						System.out.println("here");
						warnObj.invalidData(frame);
						success = false;
					} else {
						double due = Double.parseDouble(bill.getText());
						
						// if user pays amount less than or equal to bill...
						if (due >= deposit) {
							due -= deposit;

							
							// updates bill field and extra_paid in the database
							dataObj.updatePayment(due, 0, userid);
							
							// message for the user
							String msg = "PAYMENT SUCCESS";
							success = true;

							if (success) {
								
								// show success message
								JOptionPane.showMessageDialog(frame, msg, "INFORMATION",
										JOptionPane.INFORMATION_MESSAGE);
								success = false;
								amount.setText("0");

								amount.setEditable(false);
								return;
							}
						} 
						
						// if user pays amount more than due bill
						else {
							double extra = Math.abs(due - deposit);
//							
							// updates the bill and extra_field in database
							// pass the bill field as 0 since
							// bill has to be set zero in database
							// extra amount paid is calculated here
							dataObj.updatePayment(0, extra, userid);
							String extraMsg = "Extra Amount Paid added to Account";
							success = true;
							if (success) {
								
								// success message to the user
								JOptionPane.showMessageDialog(frame, extraMsg, "INFORMATION",
										JOptionPane.INFORMATION_MESSAGE);
								success = false;
								amount.setText("0");

								amount.setEditable(false);
								return;
							}
						}
					}
					
					// reset the amount text field
					amount.setText("0");

					amount.setEditable(false);
				}
				success = true;
			}
		}

		
		// creating instance of the implementer class 
		MyPayListener payObj = new MyPayListener();
		
		// adding instance of MyPayListener to the payBill button
		payBill.addActionListener(payObj);

	}

	
	/*
	 * This method adds the mouse listener to the amount text field
	 * and defines actions to be performed when amount field is clicked
	 */
	void clickAmount(JTextField field) {
		class MyListener extends MouseAdapter {
			public void mouseClicked(MouseEvent mv) {
				
				//sets text to "" when user clicks on it
				field.setText("");
				
				// makes it editable so that user can enter data
				field.setEditable(true);
				amount.setText("");
//				System.out.println("am field : "+amount.getText());
			}
		}
		
//		adds the instance of MyListener class that implements MouseAdapter class
		MyListener obj = new MyListener();
		field.addMouseListener(obj);
	}


/*
 * This method adds the action listener to the cancel and home buttons
	 * and defines actions to be performed when reset button is clicked
 */
	void clickCancelHome(JFrame frame, JPanel btp, JPanel main) {
		class MyButtonListener implements ActionListener {
			
			// as of now same actions will be performed for home and cancel button
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(cancelButton) || (e.getSource() == homeButton)) {
					
					// makes the mainPanel and buttonPanel visible
					btp.setVisible(true);
					main.setVisible(true);
					
					// makes current payment panel invisible
					paymentPanel.setVisible(false);
					
					// sets empty text to the labels
					billDetails.setText("");
					userDetails.setText("");
					
					// removes the amount text field from the payment panel
					paymentPanel.remove(amount);
//					amount.setText("0");
//					amount.setEditable(false);
					
					// removes all the buttons and photo id from the payment panel
					paymentPanel.remove(cancelButton);
					paymentPanel.remove(homeButton);
					paymentPanel.remove(payBill);
					paymentPanel.remove(imageLabel);
				}
				System.out.println("can and home : " + amount.getText());
			}
		}

		MyButtonListener obj = new MyButtonListener();
		
		// adds the instance of implementer class to the cancel and exit button
		cancelButton.addActionListener(obj);
		homeButton.addActionListener(obj);
	}

}
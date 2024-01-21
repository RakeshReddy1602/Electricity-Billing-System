package billingsystemgui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
public class MainFrame {
	JFrame frame;  //to display the main frame
	
	//text fields to display the userid ,owner name, number of units consumed and bill accumulated
	public JTextField userIdField,ownerField,unitsConsumedField,billField;
	
	//labels to display the labels for each text field
	public JLabel userLabel,ownerLabel,unitsLabel,billLabel;
	
	//title for the application
	public JLabel title;
	
	//fetch,reset,payment and exit buttons for necessary operations
	public JButton fetchButton,resetButton,payButton,exitButton;

	//mainPanel holds all text fields and labels
	//buttonPanel holds all the buttons
	public JPanel mainPanel,buttonPanel;

	//crreating instance of each class to use methods
	TextFields textObj = new TextFields();
	ButtonsClass buttonObj = new ButtonsClass();
	
	//creating new color object and customized border objcts
	Color col = new Color(150,220,170);
	Border border = BorderFactory.createLineBorder(Color.black,2);
	
	public MainFrame(String s){
		
		frame = new JFrame(s);
		mainPanel = new JPanel();
		mainPanel.setBounds(0,0,400,425);
		
		buttonPanel = new JPanel();
		mainPanel.setLayout(null);
		buttonPanel.setLayout(null);
		mainPanel.setBackground(col);
		buttonPanel.setBackground(col);
		title = new JLabel();
		title.setText("  Electricity Billing System");
		title.setBorder(border);
		title.setBackground(col);
		title.setOpaque(true);
		title.setFont(new Font("Constantia",2,33));
		title.setBounds(0,0,497,48);
		title.setHorizontalAlignment(SwingConstants.LEFT);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		frame.add(title);
		
		//adds all the text fields to the mainPanel
		addTextFields();
		
		//adds all the buttons to the buttonPanel
		addButtons();
		
		//adds the text labels to the mainPanel
		addLabels();
		
		//calls method to add actionlistener to the buttons
		addListenerToButtons();
		
		// setting a icon to the application
		ImageIcon icon  = new ImageIcon("eleclogo.jpg");
		frame.setIconImage(icon.getImage());
		
		frame.setLocation(300,50); 
		frame.setResizable(false);
		
		frame.add(mainPanel);
		frame.add(buttonPanel);
//		frame.add(loginSignupPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,600);
		frame.setVisible(true);
	}
	
	void addTextFields() {
		
		userIdField = textObj.createField("Enter 6 digit User Id",50,150,300,40,20,mainPanel);
		ownerField = textObj.createField("Owner",50,225,300,40,15,mainPanel);
		unitsConsumedField = textObj.createField("Units Consumed",50,300,300,40,15,mainPanel);
		billField = textObj.createField("Bill",50,375,300,40,15,mainPanel);
		
		
		// adds the mouse listener to the text field userIdField
		//implemented through local class
		class MyListener extends MouseAdapter{
			public void mouseClicked(MouseEvent e) {
				if(userIdField.getText().equals("Enter 6 digit User Id")) {
				userIdField.setText(null);
				userIdField.setEditable(true);
				}
			}
		}
		MyListener mouseObj = new MyListener();
		userIdField.addMouseListener(mouseObj);
	}
	void addButtons() {
		fetchButton = buttonObj.createButton("Fetch",75,450,100,35,buttonPanel);
		resetButton = buttonObj.createButton("Reset",225,450,100,35,buttonPanel);
		payButton = buttonObj.createButton("Pay Now",75,500,125,35,buttonPanel);
		payButton.setEnabled(false);
		exitButton = buttonObj.createButton("Exit",225,500,100,35,buttonPanel);
		
	}
	void addListenerToButtons() {
		buttonObj.clickReset(resetButton,payButton,userIdField,ownerField,unitsConsumedField,billField);
		buttonObj.clickExit(exitButton,frame);
		buttonObj.clickFetch(frame,fetchButton,payButton,userIdField,ownerField,unitsConsumedField,billField);
		buttonObj.clickPayNow(buttonPanel,mainPanel,payButton,userIdField,billField,frame);
//		buttonObj.clickRegister()
		
	}
	
	void addLabels() {
		TextLabels obj = new TextLabels();
		billLabel = obj.createLabel("Bill ",50,350,200, 25,mainPanel);
		unitsLabel = obj.createLabel("Units ",50,275,200,25,mainPanel);
		ownerLabel = obj.createLabel("Owner", 50,200,200,25, mainPanel);
		userLabel = obj.createLabel("House ID",50,125,200,25, mainPanel);
	}
}
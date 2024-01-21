package billingsystemgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
public class TextFields{
	Border border = BorderFactory.createLineBorder(Color.black,1);
	
	/*
	 *  The method createField has the parameters
	 *  s - text for the text field
	 *  x,y,width,height  - bounds for the text field
	 *  size - size of the font for the text field
	 *  panel - panel to which text field be added 
	 *  
	 *  It creates text field with above properties and returns the instance of text field
	 */
	public JTextField createField(String s,int x,int y,int width,int height,int size,JPanel panel) {
		JTextField field = new JTextField(s);
		field.setFont(new Font("Constantia",Font.PLAIN,size));
		field.setBounds(x,y,width,height);
		
		//sets the position of the text in the text field to the right
		field.setHorizontalAlignment(SwingConstants.RIGHT);
		field.setBorder(border);
		panel.add(field);
		field.setEditable(false);
		return field;
		
	}
	

	
}
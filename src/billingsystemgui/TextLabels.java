package billingsystemgui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class TextLabels {
	Font myFont = new Font("Times New Roman",Font.BOLD,20);
	
	/*
	 * This createLabel method has following parameters
	 * s- sets text for the label
	 * x,y,widht.height - bounds for the label
	 * panel - panel to which label is to be added
	 */
	JLabel createLabel(String s,int x,int y,int width,int height,JPanel panel) {
		JLabel label = new JLabel();
		label.setText(s);
		label.setBounds(x,y,width,height);
		label.setFocusable(false);
		label.setFont(myFont);
		panel.add(label);
		return label;
		
	}
}
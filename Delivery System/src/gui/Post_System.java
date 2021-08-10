package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Post_System - create a mail system with branch packages and hub.
 */


import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Post_System extends JOptionPane implements ChangeListener
{
	/**
	 * @param slider - represent the 3 sliders.
	 * @param dialog - represent dialog window.
	 */
	private static final long serialVersionUID = 1L;
	private JSlider[] slider;
	private JDialog dialog;
	
	/**
	 * @function - ctor of the class.
	 */
	public Post_System()
	{
		super();
		
	    this.setPreferredSize(new Dimension(600, 400));        //set size
	    
	    slider = new JSlider[3];
	    
	    for(int i=0; i<slider.length -1; i++ )                   //first to sliders creation
	    {
		 slider[i] = new JSlider(1,10,1);
		 slider[i].setPaintTicks(true);
		 slider[i].setMajorTickSpacing(1);
		 slider[i].setPaintTrack(true);
		 slider[i].setPaintLabels(true);
		 slider[i].addChangeListener(this);
	    }
	    
	     slider[2] = new JSlider(2,20,2);	                     //last slider creation.
		 slider[2].setPaintTicks(true);
		 slider[2].setMinorTickSpacing(1);
		 slider[2].setMajorTickSpacing(2);
		 slider[2].setPaintTrack(true);
		 slider[2].setPaintLabels(true);
		 slider[2].addChangeListener(this);
		 
		 
		 /***** create the JOptionPane *******/
	    this.setMessage(new Object[] 
	    		{(new JLabel("Number of branches",SwingConstants.CENTER)),slider[0] ,
	    		(new JLabel("Number of trucks per branch", SwingConstants.CENTER)) ,slider[1],
	    		(new JLabel("Number of packages", SwingConstants.CENTER)) ,slider[2]});
	    
	    this.setMessageType(JOptionPane.PLAIN_MESSAGE);
	    this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	   
	    
	    
	    
	    dialog = this.createDialog(null, "Create post system");         //create dialog.
	    dialog.setVisible(true);
	}
	
	
	
	/**
	 * @function - action listener for all the sliders.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {

		if(e.getSource().equals(slider[0]))
		{
			Visual_Panel.setNumber_of_branches(slider[0].getValue());
		}
		
		else if(e.getSource().equals(slider[1]))
		{
			Visual_Panel.setNumber_of_trucks(slider[1].getValue());
		}
		
		else
		{
			Visual_Panel.setNumber_of_packages(slider[2].getValue());
		}
	}
	
	
}

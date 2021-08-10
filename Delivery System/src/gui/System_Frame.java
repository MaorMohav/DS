package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class System_Frame - create the main frame of the program.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class System_Frame extends JFrame
{
	/**
	 * @param visual_panel - represent the visual panel, shows all packages and trucks
	 * @param button_panel - represent the buttons of the gui interface.
	 */
	private static final long serialVersionUID = 1L;
	private Visual_Panel visual_panel = new Visual_Panel();
	private Button_Panel button_panel = new Button_Panel();
	
	
	
	/**
	 * @function - ctor of the class.
	 */
	public System_Frame()
	{
		super("Post tracking system");
		this.setSize(1200, 700);
		set_in_middle();
		
		this.add(visual_panel);
		
		
		this.add(button_panel,  BorderLayout.SOUTH);

		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible(true);
		
	}
	
	/**
	 * @function - set window in the center of the screen.
	 */
	public void set_in_middle()
	{
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2), (int) ((dimension.getHeight() - this.getHeight()) / 2));
	}


}

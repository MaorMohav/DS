package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Button_Panel - represent panel of buttons - buttom panel of the main frames.
 */


import java.awt.GridLayout;
import javax.swing.JPanel;

public class Button_Panel extends JPanel 
{
	/**
	 * @param buttons - array of buttons.
	 * @param  Button_names - array of names of each button.
	 */
	private static final long serialVersionUID = 1L;
	private Button_panel_Buttons[] buttons;
	private String[] Button_names = {"Create system", "Start", "Stop", "Resume", "All packages info", "Branch info"};
	
	
	/**
	 * @function  Button_Panel - ctor of the class.
	 */
	public Button_Panel()
	{
		this.setBounds(0, 600, 1200, 100);               //set size
		this.setSize(1200,100); 
		
		Add_Buttons();
		
		this.setLayout(new GridLayout(0,6));
	}
	
	/**
	 * @function - create new button and add to panel.
	 */
	public void Add_Buttons()
	{
		buttons = new Button_panel_Buttons[Button_names.length];
		
		for(int i=0; i<Button_names.length; i++)
		{
			buttons[i] = new Button_panel_Buttons(Button_names[i], i);
			this.add(buttons[i]);
		}
	}
}

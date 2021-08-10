package gui;


/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Button_panel_Buttons - represent the panel buttons.
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import components.Branch;
import components.MainOffice;



public class Button_panel_Buttons extends JButton implements ActionListener
{
	/**
	 * @param button_index - represent the button number.
	 * @param show - represent the package table status - show or hide.
	 * @param table - represent the package table.
	 */
	private static final long serialVersionUID = 1L;
	private int button_index;
	private boolean show = false;
	private static Package_Table table;
	
	
	
	
	/**
	 * 
	 * @param name - button name
	 * @param index - index of the button.
	 * 
	 * @function - ctor of the class.
	 */
	public Button_panel_Buttons(String name, int index)
	{
		super(name);
		button_index = index;
		this.setBackground(Color.LIGHT_GRAY);
		addActionListener(this);
	}

	/**
	 * @function - add every button action listener
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(button_index)
		{
		
		case 0:     if(Gui_system.getMain_office() == null) {                                                                                                       //create new system.
						new Post_System();	
						Gui_system.setMain_office(new MainOffice(Visual_Panel.getNumber_of_branches(), Visual_Panel.getNumber_of_trucks()));
					}
						
					break;
		
		case 1:     if(!Gui_system.getMainOffice().isAlive())                                                                                                         // start system.
						Gui_system.getMainOffice().start();
					break;
		
		case 2:     Gui_system.getMainOffice().Suspend();	                                                                                                      // stop system.
					Gui_system.getMainOffice().getCopy_hub().Suspend();
					
					for(Branch i:Gui_system.getMainOffice().getCopy_hub().getBranches())
						i.Suspend();
					break;

		case 3:		Gui_system.getMainOffice().Resume();                                                                                                     // resume system.
					Gui_system.getMainOffice().getCopy_hub().Resume(); 	
					
					for(Branch i:Gui_system.getMainOffice().getCopy_hub().getBranches())
						i.Resume();
					
					break;
					
		case 4:		                                                                                                                                      // show all packages
					if(show == false)
					{
						
						table =new Package_Table(true, null);
						show = true;


					}
					else
					{
						show = false;
						table =new Package_Table(false, null);

					}
		
					break;
		
		case 5:     new Branch_info();                                                                                                                      //show packages of a given branch/ hub.
					break;
		}
		
		
	}
	
	/**
	 * 
	 * @param state - true or false, show the package table or hide it.
	 */
	public void set_show(boolean state)                            
	{
		show = state;
	}
	

}

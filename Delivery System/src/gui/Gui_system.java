package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Gui_system - represent the main system Gui.
 */

import components.MainOffice;

public class Gui_system extends Thread
{
	/**
	 * @param frame - main frame of the program.
	 * @param frame_copy - represent frame copy.
	 * @param main_office - represent main thread.
	 * @param mainoffice - represent the mainoffice of the mail. 
	 */
	private System_Frame frame;
	private static System_Frame frame_copy;
	private static Thread main_office = null;
	private static MainOffice mainoffice;
	
	/**
	 * @function Gui_system - ctor of the class.
	 */
	public Gui_system()
	{
		
		frame = new System_Frame();
		frame_copy = frame;	
				
				
	}
	
	/**
	 * @function run - thread function.
	 */
	public void run()
	{
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**************************************************Getters and Setters************************************************/
	/**
	 * 
	 * @description - all the functions are getters and setters for the class veriables.
	 */
	public System_Frame getFrame() {
		return frame;
	}

	public void setFrame(System_Frame frame) {
		this.frame = frame;
	}

	public static Thread getMain_office() {
		return main_office;
	}

	public static void setMain_office(MainOffice main_office) {
		mainoffice = main_office;
		Gui_system.main_office = new Thread(main_office);
	}
	
	public static MainOffice getMainOffice()
	{
		return mainoffice;
	}

	public static System_Frame getFrame_copy() {
		return frame_copy;
	}

	public static void setFrame_copy(System_Frame frame_copy) {
		Gui_system.frame_copy = frame_copy;
	}
	
}

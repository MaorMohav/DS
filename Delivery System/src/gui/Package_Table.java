package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Package_Table - create a table of packages information for a hub or branch.
 */

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;

import components.Branch;
import components.Hub;
import components.MainOffice;



public class Package_Table extends JPanel
{
	/**
	 * @param sp - represent the scrolling pane.
	 * @param frame - represent the window
	 * @param j - represent the table.
	 * @param show - if to show the table or hide it.
	 */
	private static final long serialVersionUID = 1L;
	private static JScrollPane sp;
	private static JWindow frame = new JWindow();
	private static JTable j;
	private  boolean show;

	
	/**
	 * 
	 * @param show - set visiable of the table
	 * @param obj - branch or hub or all packages.
	 * 
	 * @function - ctor of the class.
	 */
	public Package_Table(boolean show, Object obj)
	{
		String[] columnNames = { "Package ID", "Sender", "Destination", "Priority", "Status" };                       //columns of the table.
		Object[][] data = new Object[MainOffice.getPackageList().size()][5];
		



		if(obj == null)              //create table of all packages.
		{
			for(int i=0; i< MainOffice.getPackageList().size(); i++)
			{
				Object[] temp = {MainOffice.getPackageList().get(i).getPackageID(), MainOffice.getPackageList().get(i).getSenderAddress().getZip() + "-" + MainOffice.getPackageList().get(i).getSenderAddress().getStreet() ,  MainOffice.getPackageList().get(i).getDestinationAddress().getZip() + "-" + MainOffice.getPackageList().get(i).getDestinationAddress().getStreet() , MainOffice.getPackageList().get(i).getPriority(), MainOffice.getPackageList().get(i).getStatus()};	
				data[i] = temp;
			}
		}
		
		else
		{
			if(obj instanceof Hub)       //create table of hub packages
			{
				for(int i=0; i< ((Hub)obj).getPackages().size(); i++)
				{
					Object[] temp = {((Hub)obj).getPackages().get(i).getPackageID(), ((Hub)obj).getPackages().get(i).getSenderAddress(), ((Hub)obj).getPackages().get(i).getDestinationAddress(), ((Hub)obj).getPackages().get(i).getPriority(),((Hub)obj).getPackages().get(i).getStatus()};	
					data[i] = temp;
				}
			}
			
			else if(obj instanceof Branch)         //create table of branch packages.
			{
				for(int i=0; i< ((Branch)obj).getPackages().size(); i++)
				{
					Object[] temp = {((Branch)obj).getPackages().get(i).getPackageID(), ((Branch)obj).getPackages().get(i).getSenderAddress().getZip() + 1 + "-" + ((Branch)obj).getPackages().get(i).getSenderAddress().getStreet(), ((Branch)obj).getPackages().get(i).getDestinationAddress().getZip() + 1 + "-" + ((Branch)obj).getPackages().get(i).getDestinationAddress().getStreet(), ((Branch)obj).getPackages().get(i).getPriority(), ((Branch)obj).getPackages().get(i).getStatus()};	
					data[i] = temp;
				}
			}
		}

		
		j = new JTable(data, columnNames);                //set size.
		j.setBounds(30, 40, 200, 300);
		            	  
		j.setVisible(true);
		
		this.setBounds(30, 40, 200, 300);
		sp = new JScrollPane(j);
		sp.setVisible(true);
		this.repaint();
		
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		frame.add(sp);
		frame.setBackground(Color.WHITE);
		frame.setSize(500,18 + MainOffice.getPackageList().size()* 17 );
		frame.setLocation(Gui_system.getFrame_copy().getX() + 7,Gui_system.getFrame_copy().getY() + 28);
		
		frame.setVisible(show);
	}
		
	
	/**************************************************Getters and Setters************************************************/
	/**
	 * 
	 * @description - all the functions are getters and setters for the class veriables.
	 */
	public JWindow getFrame()
	{
		return frame;
	}
	public JScrollPane getSp() {
		return sp;
	}
	
	public void setSp(JScrollPane sp) {
		this.sp = sp;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		frame.setVisible(show);
	}

}

package gui;

/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Branch_info - shows the branch'es and hub's packages in real time.
 */


import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import components.MainOffice;

public class Branch_info extends JOptionPane 
{
	
	/**
	 * @param choice - list of hub and branches for the combo box.
	 * @param branch_choice - represent the combo box of the branches / hub.
	 * @param show - if to show the table or no - initialize to true.
	 * @param table - represent list of packages of selected branch/ hub.
	 */
	private static final long serialVersionUID = 1L;
	final static String[] choice = { "Sorting center", "Branch 1", "Branch 2", 
				 "Branch 3", "Branch 4", "Branch 5"};
	
	private JComboBox<String> branch_choice;
	private boolean show = true;
	private Package_Table table;

	
	/**
	 * @function Branch_info - ctor of the class.
	 */
	public Branch_info() {
    
		
		String[] branches = new String[MainOffice.getHub().getBranches().size() + 1];
		branches[0] = "Sorting center";
		
		
		
		for(int i=1; i<MainOffice.getHub().getBranches().size() + 1; i++)                            //create branches.
		{
				branches[i] = "Branch " + String.valueOf(i);
		}
		
		branch_choice = new JComboBox<String>(branches);                                               // create combo-box.
		
		
		String branch = (String) JOptionPane.showInputDialog(Gui_system.getFrame_copy(),null,"Branch info", JOptionPane.PLAIN_MESSAGE, icon,branches,branches[0]);                //create dialog.
		


		
		if(branch != null)                                                                                     // if user choose sorting center.
		{
			
			
			switch(branch)
			{
				case "Sorting center":    	  table = new Package_Table(show, MainOffice.getHub());
								              break;
			}
			
			for(int i=1; i < branches.length; i++)                                                           // if user choose branch.
			{

				if(branches[i] == branch)
				{
					table = new Package_Table(show, MainOffice.getHub().getBranches().get(i-1));
				}
			}
		}
		
	}
}


package gui;


/**
 * 
 * @authors Name: Maor Mohav , ID: 316142363 , Name: Ariel Epshtein , ID: 316509504
 * 			
 *
 *@class Visual_Panel - create all the output visualization of the program.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import components.Branch;
import components.Hub;
import components.MainOffice;
import components.NonStandardPackage;
import components.NonStandardTruck;
import components.StandardTruck;
import components.Status;
import components.Truck;
import components.Van;


public class Visual_Panel extends JPanel 
{

	/**
	 * @param Number_of_branches - number of branches in the program.
	 * @param Number_of_trucks - number of trucks in the program.
	 * @param Number_of_packages - number of packages in the program.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int Number_of_branches = 0;
	private static int Number_of_trucks = 0;
	private static int Number_of_packages = 0;
	
	
	
	/**
	 * @function - ctor of the class.
	 */
	public Visual_Panel()
	{
		super();
		this.setBounds(0, 0, 1200, 620);
		this.setSize(1200, 620);
		
	}
	
	
	/**
	 * @function - paint the panel.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
					
		if(Number_of_branches != 0)
		{		
			Create_Branches(g);	
		}
		
		Draw_packages(g);
		
		
		this.repaint();
	}
	
	
	/**
	 * @param g - graphic component
	 * 
	 * @function - draw branches on the panel (by user inputs).
	 */
	public void Create_Branches(Graphics g)
	{	
		int space_y = 620 / (Number_of_branches + 1);
		int space_on_hub = 200 / (Number_of_branches + 1);
		
		
		g.setColor(new Color(0,150,0));
		g.fillRect(1120, 200, 40, 200);                    //draw hub
		
		for(int i=1; i <= Number_of_branches; i++)                                                    
		{
			if(MainOffice.getHub() != null && MainOffice.getHub().getBranches().get(i-1).getPackages().isEmpty() == true)
				g.setColor(new Color(0, 255, 255));
			
			else
				g.setColor(Color.BLUE);
			
			g.fillRect(30, space_y * i, 40, 30);                                            //draw branch and line to the hub.
			g.setColor(new Color(0,150,0));
			g.drawLine(68, space_y * i + 12, 1120,  200 + space_on_hub * i);
			

			
			
			if(MainOffice.getHub() != null)
				get_standard_truck(g, i);           //set standard trucks.
			
		}
	}
	
	
	/**
	 * 
	 * @param g - graphic component
	 * 
	 * @function - draw packages on the panel (by user inputs).
	 */
	public void Draw_packages(Graphics g)
	{
		int space_packages = 1200/ (Visual_Panel.getNumber_of_packages() +1);
		int space_y = 620 / (Number_of_branches + 1);
		int space_on_hub = 200 / (Number_of_branches + 1);
		
		for(int i=1; i<=MainOffice.getPackageList().size(); i++ )                      //run all the packges in the system
		{
			if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.CREATION || MainOffice.getPackageList().get(i- 1).getStatus() ==  Status.COLLECTION)                  //if status creation or collection 
				g.setColor(Color.RED); 
			else
				g.setColor(Color.PINK);
				
			g.fillOval(space_packages*i , 30, 30, 30);                                          //draw package(sender)
							
			if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.DELIVERED)                     //draw package(destination)
				g.setColor(Color.RED);
			else
				g.setColor(Color.PINK);
			
			g.fillOval(space_packages *i , 540, 30, 30);
			
			if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.COLLECTION)            //set package cordinate.
			{
				MainOffice.getPackageList().get(i- 1).setX(space_packages*i + 14);
				MainOffice.getPackageList().get(i- 1).setY(60);
			}
			else
			{
				MainOffice.getPackageList().get(i- 1).setX(space_packages*i + 14);
				MainOffice.getPackageList().get(i- 1).setY(540);
			}
		}
		
		for(int i=1; i<= MainOffice.getPackageList().size(); i++)                                          //draw connactions line
		{
			if(MainOffice.getPackageList().get(i- 1) instanceof NonStandardPackage )                           // if non standard package
			{
				g.setColor(Color.RED);
				g.drawLine(space_packages*i + 14, 60, space_packages*i + 14, 540);
				
				g.setColor(Color.RED);
				g.drawLine(space_packages*i + 14, 60, 1140, 200);
				
				if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.COLLECTION) {
					MainOffice.getPackageList().get(i- 1).setX(space_packages*i + 14);
					MainOffice.getPackageList().get(i- 1).setY(60);
				}
				
				
				else if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.DISTRIBUTION) {
					MainOffice.getPackageList().get(i- 1).setX( space_packages*i + 14);
					MainOffice.getPackageList().get(i- 1).setY(540);
				}
				
				Set_Non_Standard_Truck(g, i);
				
			}			
			else
			{
				g.setColor(Color.BLUE);
				g.drawLine(space_packages*i + 14, 60, 30 + 14, space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1));
				
				g.setColor(Color.BLUE);
				g.drawLine(space_packages*i + 14, 540, 30 + 14, (space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1)) + 30);
				
				if(MainOffice.getPackageList().get(i- 1).getStatus() == Status.COLLECTION || MainOffice.getPackageList().get(i- 1).getStatus() == Status.DISTRIBUTION)
					Set_Vans(g, i);         																																				 //set vans
				
			}
			
		}
	}
	
	
	/**
	 * 
	 * @param g - graphic component
	 * @param i - package index.
	 * 
	 * @function - draw non standard trucks on the panel.
	 */
	public void Set_Non_Standard_Truck(Graphics g, int i)
	{
		int space_packages = 1200/ (Visual_Panel.getNumber_of_packages() +1);
		
		NonStandardTruck truck = get_NonStandardTruck(i);                                   //get truck
		int timeleft = 0;
		
		if(truck != null)
		{
			timeleft = truck.getTimeLeft();		
		
			if(MainOffice.getPackageList().get(i-1).getStatus() == Status.COLLECTION && truck.getTimeLeft() == truck.getTms())           //if package status is collection.
			{	

				
				truck.setPrevious(truck.getTimeLeft());
				
				truck.setX(1140);                                 //set truck cordinates.
				truck.setY(200);
				
				
				float dx =  Math.abs((MainOffice.getPackageList().get(i-1).getX() - truck.getX()) / timeleft);                     //calculate dx and dy (length of the road).
				float dy =  Math.abs((MainOffice.getPackageList().get(i-1).getY() - truck.getY())/ timeleft);
						
				truck.setDx(dx);
				truck.setDy(dy);
				
				
				g.setColor(Color.PINK);                                                               //draw truck
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);

			}
			
			

			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.COLLECTION  && truck.getX() > MainOffice.getPackageList().get(i-1).getX())
			{

				if( truck.getTimeLeft() !=  truck.getPrevious()) {
					truck.setPrevious(truck.getTimeLeft());
					truck.setX(truck.getX() - truck.getDx());
					truck.setY(truck.getY() - truck.getDy());
					}

				
				g.setColor(Color.PINK);
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);
				
			}
			
			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.DISTRIBUTION && truck.getTimeLeft() == truck.getTms())                //if status is distribution.
			{	
				
				truck.setPrevious(truck.getTimeLeft());
				
				truck.setX(space_packages*i + 14);
				truck.setY(60);
				
				float dx =  Math.abs((MainOffice.getPackageList().get(i-1).getX() - truck.getX()) / timeleft);                                         //calculate dx and dy (length of the road).
				float dy =  Math.abs((MainOffice.getPackageList().get(i-1).getY() - truck.getY())/ timeleft);
						
				truck.setDx(dx);
				truck.setDy(dy);
				            
				g.setColor(Color.RED);                                                                                                                 //draw truck
				g.fillRect((int)truck.getX(),(int) truck.getY(), 16, 16);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);

			}
			
			
			
			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.DISTRIBUTION && truck.getY() < MainOffice.getPackageList().get(i-1).getY())
			{
				
				if( truck.getTimeLeft() !=  truck.getPrevious()) {
					truck.setPrevious(truck.getTimeLeft());
					truck.setX(truck.getX() - truck.getDx());
					truck.setY(truck.getY() + truck.getDy());
					}
				
				g.setColor(Color.RED);
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);
				
				
			}
		}
			
	}
	
	
	/**
	 * 
	 * @param index index of the package
	 * @return truck.
	 * 
	 * @function - check who is the truck that conatins given package and returns it.
	 */
	public NonStandardTruck get_NonStandardTruck(int index)
	{
		for(Truck i: MainOffice.getHub().getTruckList())
		{
			if(i instanceof NonStandardTruck && i.isAvailable() == false && i.getPackages().isEmpty() == false  && i.getPackages().get(0).getPackageID() == MainOffice.getPackageList().get(index - 1).getPackageID())
			{
				return (NonStandardTruck) i;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param g - graphic component
	 * @param truck - represent standard truck.
	 * @param i - index of the package.
	 * 
	 * @function - draw non standard trucks on the panel.
	 */
	public void set_standard_trucks(Graphics g, StandardTruck truck, int i)
	{
		int space_y = 620 / (Number_of_branches + 1);
		int space_on_hub = 200 / (Number_of_branches + 1);
		
		
		if(truck.getDestination() instanceof Hub && truck.getTimeLeft() == truck.getTms() && truck.isAvailable() == false)                         //if destination is hub
			{	
				
			truck.setX(68);                                                                                                                      //set truck cordinate
			truck.setY((float)((space_y *(truck.getOrigin().getBranchId() + 1)) + 12));
			
			
			truck.getDestination().setX(1120);                                                                                                 //set destination cordinate.
			truck.getDestination().setY((float)(200 + (space_on_hub * (truck.getOrigin().getBranchId() + 1))));	
			
			
			truck.setPrevious(truck.getTimeLeft());		

				float dx =  (truck.getDestination().getX() - truck.getX()) / truck.getTms();                                                    //calc road length of the truck
				float dy = (truck.getDestination().getY() - truck.getY())/ truck.getTms();
						
				truck.setDx(dx);
				truck.setDy(dy * (-1));
				
				
				
				if(truck.getPackages().isEmpty())                  //draw truck
					g.setColor(Color.GREEN);
				
				else
				{
					g.setColor(new Color(0,150,0));
					Graphics2D g2d = (Graphics2D)g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(String.valueOf(truck.getPackages().size()), (int)truck.getX() + 4 ,(int)truck.getY() - 13 );

				}
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);

			}
			
			

			
			else if(truck.getDestination() instanceof Hub && truck.getX() < truck.getDestination().getX() && truck.isAvailable() == false)
			{

				if( truck.getTimeLeft() !=  truck.getPrevious()) {
					truck.setPrevious(truck.getTimeLeft());
					truck.setX(truck.getX() + truck.getDx());
					truck.setY(truck.getY() - truck.getDy());
					
					
					}

				if(truck.getPackages().isEmpty())
					g.setColor(Color.GREEN);
				else
				{
					g.setColor(new Color(0,150,0));
					Graphics2D g2d = (Graphics2D)g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(String.valueOf(truck.getPackages().size()), (int)truck.getX() +4 ,(int)truck.getY() - 13 );

				}
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);
				
				
			}
				
			
			else if(truck.getDestination() instanceof Branch && truck.getTimeLeft() == truck.getTms() && truck.isAvailable() == false )                      //if destination is branchי
			{	
				
				truck.setX(1120);                                                                                                                             //set truck cordinate
				truck.setY((float)(200 + (space_on_hub * (truck.getDestination().getBranchId() + 1))));
				
				truck.getDestination().setX(68);																												//set destination cordinate
				truck.getDestination().setY((float)((space_y * (truck.getDestination().getBranchId() + 1)) + 12));
				
				
				
				truck.setPrevious(truck.getTimeLeft());			
				
				
				float dx =  (truck.getDestination().getX() - truck.getX()) / truck.getTms(); 																	 //calc road length of the truck
				float dy = (truck.getDestination().getY() - truck.getY())/ truck.getTms();
						
				truck.setDx(dx);
				truck.setDy(dy);
				
				if(truck.getPackages().isEmpty())                                                                                                                  //draw truck.
					g.setColor(Color.GREEN);
				else
				{
					g.setColor(new Color(0,150,0));
					Graphics2D g2d = (Graphics2D)g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(String.valueOf(truck.getPackages().size()), (int)truck.getX() + 4 ,(int)truck.getY() - 13 );

				}
				
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);

			}
			
			else if(truck.getDestination() instanceof Branch && truck.getX() > truck.getDestination().getX() && truck.isAvailable() == false)
			{
				
				if( truck.getTimeLeft() !=  truck.getPrevious()) {
					truck.setPrevious(truck.getTimeLeft());
					truck.setX(truck.getX() + truck.getDx());
					truck.setY(truck.getY() + truck.getDy());
					}

				if(truck.getPackages().isEmpty())
					g.setColor(Color.GREEN);
				
				else
				{
					g.setColor(new Color(0,150,0));
					Graphics2D g2d = (Graphics2D)g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(String.valueOf(truck.getPackages().size()), (int)truck.getX() + 4 ,(int)truck.getY() - 13 );

				}
				
				
				g.fillRect((int)truck.getX() ,(int)truck.getY(), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() + 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() - 4), (int)(truck.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(truck.getX() + 11), (int)(truck.getY() - 4), 10, 10);
				
			}
			
	}
	
	
	
	/**
	 * @param branch - branch that contain the vans.
	 * @param p index of the package
	 * @return van.
	 * 
	 * @function - check who is the van that conatins given package and returns it.
	 */
	public Van get_van(Branch branch, int p)
	{
		int space_y = 620 / (Number_of_branches + 1);
		
		for(Truck i:  branch.getTruckList())
		{
			if(i instanceof Van && i.isAvailable() == false &&i.getPackages().isEmpty() == false && (i.getPackages().get(0)).getPackageID() == MainOffice.getPackageList().get(p-1).getPackageID())
			{
				return (Van) i;
			}
		}
		return null;
	}
	
	
	
	/**
	 * @param g - graphic component.
	 * @param index - index of the package
	 * 
	 * @function - check who is the standard that conatins given package.
	 */
	public void get_standard_truck(Graphics g, int index)
	{	
		
		for(Truck i: MainOffice.getHub().getTruckList())
		{
			if(i instanceof StandardTruck && i.isAvailable() == false && ((StandardTruck) i).getDestination() != null)
			{
				if(((StandardTruck) i).getDestination() instanceof Hub)
				{

					set_standard_trucks(g, (StandardTruck)i, index);
				}
				else if(((StandardTruck) i).getDestination() instanceof Branch)
				{
					
					set_standard_trucks(g, (StandardTruck)i, index);
				}
				
			}
		}
	}
	
	/**
	 * @param g - graphic component
	 * @param i - index of the package
	 * 
	 * @function - draw all the vans on the panel.
	 */
	public void Set_Vans(Graphics g, int i)
	{	
		int space_packages = 1200/ (Visual_Panel.getNumber_of_packages() +1);
		int space_y = 620 / (Number_of_branches + 1);
		int timeleft = 0;
		Van van = null;
		
		if(MainOffice.getPackageList().get(i-1).getStatus() == Status.COLLECTION )                  //get van
			van = get_van(MainOffice.getPackageList().get(i- 1).getSenderBranch(), i);
		
		if(MainOffice.getPackageList().get(i-1).getStatus() == Status.DISTRIBUTION)                 //get van
			van = get_van(MainOffice.getPackageList().get(i- 1).getDestBranch(), i);
		
		if(van != null)
		{
			timeleft = van.getTimeLeft();		
		
			if( van.getTimeLeft() >= van.getTms() - 1 && MainOffice.getPackageList().get(i-1).getStatus() == Status.COLLECTION )                                     //if status is collection
			{				
				van.setPrevious(van.getTimeLeft());
				
				van.setX(30 + 14);                                                                                                               //set van cordinate
				van.setY(space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1));
				
			
				float dx =  (MainOffice.getPackageList().get(i-1).getX() - van.getX()) / timeleft;                                               //calculate dx and dy - length of the road.
				float dy =  (MainOffice.getPackageList().get(i-1).getY() - van.getY())/ timeleft;
						
				van.setDx(dx);
				van.setDy(dy);
			
				
				g.setColor(Color.BLUE);                                                                                                                   //draw van
				g.fillRect(30 + 14 ,(int)space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 - 4), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1) + 9), 10, 10);
				
			    g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 + 11), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1)+ 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 - 4), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1) - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 + 11), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1) - 4), 10, 10);

			}
			
			

			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.COLLECTION  && van.getX() < MainOffice.getPackageList().get(i-1).getX())
			{
			
				
				if( van.getTimeLeft() !=  van.getPrevious()) {
					van.setPrevious(van.getTimeLeft());
					van.setX(van.getX() + van.getDx());
					van.setY(van.getY() + van.getDy());
					}


				
				
				g.setColor(Color.BLUE);
				g.fillRect((int)van.getX(),(int) van.getY(), 16, 16);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() - 4), (int)(van.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() + 11), (int)(van.getY() + 9) , 10, 10);
				
    			g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() - 4), (int)(van.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() + 11), (int)(van.getY() - 4), 10, 10);
				
				
			}
				
			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.DISTRIBUTION && van.getTimeLeft() >= van.getTms()-1)                          //if status is collection
			{	
				
				van.setPrevious(van.getTimeLeft());
				
				van.setX(30 + 14);                                                                                                                           //set van cordinate
				van.setY(space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1) + 30);
				
				float dx =  (MainOffice.getPackageList().get(i-1).getX() - van.getX()) / timeleft;															 //calculate dx and dy - length of the road.
				float dy =  (MainOffice.getPackageList().get(i-1).getY() - van.getY())/ timeleft;
						
				van.setDx(dx);
				van.setDy(dy);
				
				
				
				g.setColor(Color.BLUE);                            																						      //draw van
				g.fillRect(30 + 14 ,(int)space_y*(MainOffice.getPackageList().get(i- 1).getSenderAddress().getZip() + 1), 16, 16);
						
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 - 4), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1) + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 + 11), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1)+ 9) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 - 4), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1) - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(30 + 14 + 11), (int)(space_y*(MainOffice.getPackageList().get(i- 1).getDestinationAddress().getZip() + 1) - 4), 10, 10);

			}
			
			else if(MainOffice.getPackageList().get(i-1).getStatus() == Status.DISTRIBUTION && van.getX() < MainOffice.getPackageList().get(i-1).getX())
			{
				
				if( van.getTimeLeft() !=  van.getPrevious()) {
					van.setPrevious(van.getTimeLeft());
					van.setX(van.getX() + van.getDx());
					van.setY(van.getY() + van.getDy());
					}
				
				g.setColor(Color.BLUE);
				g.fillRect((int)van.getX(),(int) van.getY(), 16, 16);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() - 4), (int)(van.getY() + 9), 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() + 11), (int)(van.getY() + 9) , 10, 10);
				
    			g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() - 4), (int)(van.getY() - 4) , 10, 10);
				
				g.setColor(Color.BLACK);
				g.fillOval((int)(van.getX() + 11), (int)(van.getY() - 4), 10, 10);
				
			}
			
		}
	
	}
	
	
	
	/**************************************************Getters and Setters************************************************/
	/**
	 * 
	 * @description - all the functions are getters and setters for the class veriables.
	 */
	public static int getNumber_of_branches() {
		return Number_of_branches;
	}
	public static void setNumber_of_branches(int number_of_branches) {
		Number_of_branches = number_of_branches;
	}
	public static int getNumber_of_trucks() {
		return Number_of_trucks;
	}
	public static void setNumber_of_trucks(int number_of_trucks) {
		Number_of_trucks = number_of_trucks;
	}
	public static int getNumber_of_packages() {
		return Number_of_packages;
	}
	public static void setNumber_of_packages(int number_of_packages) {
		Number_of_packages = number_of_packages;
	}
}

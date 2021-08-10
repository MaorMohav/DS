package components;

import java.util.ArrayList;
import java.util.Random;

import gui.Gui_system;
import gui.Visual_Panel;

public class MainOffice extends Thread {
	private static int clock=0;
	private static Hub hub;
	private static int number_of_packages = 0;                                
	private static ArrayList<Package> packages=new ArrayList<Package>();   
	
	
	
	
	
	/**
	 * @param suspendFlag - stop and resume the thread
	 */
	
	private static boolean suspendFlag;
	private Hub copy_hub;
	public MainOffice(int branches, int trucksForBranch) {
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		System.out.println("\n\n========================== START ==========================");

	}
	public static ArrayList<Package> getPackageList()                     
	{
		return packages;
	}
	
	public static int getMainOfficePackages()                             
	{
		return number_of_packages;
	}
	
	
	/**
	 * 
	 * @return true if all packages are deliverd - else return false.
	 */
	public static boolean isDone()
	{
		
		if(packages.isEmpty())
		{
			return false;
		}
		
		for(int i=0; i<packages.size(); i++)
		{
			if(packages.get(i).getStatus() != Status.DELIVERED)
				return false;
		}
		return true;
	}
	
	
	/**
	 * @function - run the thread
	 */
	public void run()
	{
		tick();
		
		while(!isDone()) {

			System.out.println(clockString());
			if (clock%5==0  && number_of_packages < Visual_Panel.getNumber_of_packages())                                                    
				addPackage();
			
			clock++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (this) {
				
			
				while(suspendFlag)                                          //stop thread.
					try {
						wait();
						
						for (Branch b: hub.getBranches()) {
							b.Suspend();
						}
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		System.out.println("\n========================== STOP ==========================\n\n");
		printReport();
	}
	
	public static Hub getHub() {
		return hub;
	}


	public static int getClock() {
		return clock;
	}

	
	public void play(int playTime) {	
		for (int i=0; i<playTime; i++) {
			tick();
		}
		System.out.println("\n========================== STOP ==========================\n\n");
		printReport();
	}
	
	
	public void printReport() {
		for (Package p: packages) {
			System.out.println("\nTRACKING " +p);
			for (Tracking t: p.getTracking())
				System.out.println(t);
		}
	}
	
	public String clockString() {
		String s="";
		int minutes=clock/60;
		int seconds=clock%60;
		s+=(minutes<10) ? "0" + minutes : minutes;
		s+=":";
		s+=(seconds<10) ? "0" + seconds : seconds;
		return s;
	}
	
	
	public void tick() {
		System.out.println(clockString());
		if (clock%5==0  && number_of_packages < Visual_Panel.getNumber_of_packages())                                                     //package dagfdgdfgdfgfdgfdgsdas
			addPackage();
		hub.start();
		for (Branch b: hub.getBranches()) {
			b.start();
		}
		clock++;
	}
		
	
	public void addHub(int trucksForBranch) {
		hub=new Hub();
		for (int i=0; i<trucksForBranch; i++) {
			hub.addTruck(new StandardTruck());
		}
		hub.addTruck(new NonStandardTruck());
		setCopy_hub(hub);
	}
	
	
	public void addBranches(int branches, int trucks) {
		for (int i=0; i<branches; i++) {
			Branch branch=new Branch();
			for (int j=0; j<trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);		
		}
	}
	
	
	public void addPackage() {
		
		number_of_packages++;                                            
		
		Random r = new Random();
		Package p;
		Priority priority=Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);

		switch (r.nextInt(3)){
		case 0:
			p = new SmallPackage(priority,  sender, dest, r.nextBoolean() );
			p.getSenderBranch().addPackage(p);
			break;
		case 1:
			p = new StandardPackage(priority,  sender, dest, r.nextFloat()+(r.nextInt(9)+1));
			p.getSenderBranch().addPackage(p);
			break;
		case 2:
			p=new NonStandardPackage(priority,  sender, dest,  r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			hub.addPackage(p);
			break;
		default:
			p=null;
			return;
		}
		
		packages.add(p);
		
	}
	
	
	/**
	 * @function - change flag.
	 */
	public void Suspend() {
		synchronized (this)
		{
		suspendFlag =true;
		} 
	}
	
	/**
	 * @function - resume the thread.
	 */
	public synchronized void Resume()  {
		suspendFlag = false;
		
		for (Branch b: hub.getBranches()) {
			b.Resume();
		}
		notify();
	}
	
	public static boolean getSuspendFlag()
	{
		return suspendFlag;
	}
	
	public void setSuspendFlag(boolean flag)
	{
		suspendFlag = flag;
	}
	public Hub getCopy_hub() {
		return copy_hub;
	}
	public void setCopy_hub(Hub copy_hub) {
		this.copy_hub = copy_hub;
	}
	
}

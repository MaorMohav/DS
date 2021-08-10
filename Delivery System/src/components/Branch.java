package components;

import java.util.ArrayList;

import gui.Gui_system;

public class Branch extends Thread implements Node {
	private static int counter=-1;
	private int branchId;
	private String branchName;
	protected ArrayList <Package> packages = new ArrayList<Package>();
	protected ArrayList <Truck> listTrucks = new ArrayList<Truck>();
	
	
	
	/**
	 * @param suspendFlag - stop and resume the thread
	 * @param x - x cordinate of branch
	 * @param y - y cordinate of branch
	 * 
	 */
	private  boolean suspendFlag = false;
	private float x;
	private float y;
	private int index;
	
	
	public ArrayList <Truck> getTruckList()
	{
		return listTrucks;
	}
	
	
	public Branch() {
		this("Branch "+counter);
	}
	
	public Branch(String branchName) {
		this.branchId=counter++;
		this.branchName=branchName;
		System.out.println("\nCreating "+ this);
	}
	
	public Branch(String branchName, Package[] plist, Truck[] tlist) {
		this.branchId=counter++;
		this.branchName=branchName;
		addPackages(plist);
		addTrucks(tlist);
	}
	
	public void run()
	{
		for (Truck t: listTrucks) {
			t.start();
		}
		
		while(MainOffice.isDone() != true)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(suspendFlag) {                                         //stop the thread.
			synchronized (this) {
				
					for(Truck b: listTrucks)
					{
						if(b instanceof StandardTruck)
							((StandardTruck) b).Suspend();
						else if(b instanceof NonStandardTruck)
							((NonStandardTruck) b).Suspend();
						else if(b instanceof Van)
							((Van) b).Suspend();
					}
			
					try {
						wait();
					
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			localWork();
		}
	}
	
	public void printBranch() {
		System.out.println("\nBranch name: "+branchName);
		System.out.println("Packages list:");
		for (Package pack: packages)
			System.out.println(pack);
		System.out.println("Trucks list:");
		for (Truck trk: listTrucks)
			System.out.println(trk);
	}
	
	
	public void addPackage(Package pack) {
		packages.add(pack);
	}
	
	
	public void addTruck(Truck trk) {
		listTrucks.add(trk);
	}
	
	
	public void addPackages(Package[] plist) {
		for (Package pack: plist)
			packages.add(pack);
	}
	
	
	public void addTrucks(Truck[] tlist) {
		for (Truck trk: tlist)
			listTrucks.add(trk);
	}

	
	public int getBranchId() {
		return branchId;
	}
	
	
	public String GetName() {
		return branchName;
	}

	
	@Override
	public String toString() {
		return "Branch " + branchId + ", branch name:" + branchName + ", packages: " + packages.size()
				+ ", trucks: " + listTrucks.size();
	}

	
	@Override
	public void collectPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				v.collectPackage(p);
				return;
			}
		}
	}

	@Override
	public void deliverPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				v.deliverPackage(p);
				return;
			}
		}	
	}

	@Override
	public void work() {
		for (Truck t: listTrucks) {
			t.start();
		}
		localWork();
	}
	
	
	public void localWork() {
		for (Package p: packages) {
			if (p.getStatus()==Status.CREATION) {
				collectPackage(p);
			}
			if (p.getStatus()==Status.DELIVERY) {
				deliverPackage(p);
			}
		}
	}
	
	
	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void removePackage(Package p) {
		packages.remove(p);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
	/**
	 * @function - change flag.
	 */
	public synchronized void Suspend() {
		suspendFlag =true;
	}
	
	
	/**
	 * @function - resume the thread.
	 */
	public synchronized void Resume()  {
		suspendFlag = false;
		
		
		for(Truck b: listTrucks)
		{
			if(b instanceof StandardTruck)
				((StandardTruck) b).Resume();
			else if(b instanceof NonStandardTruck)
				((NonStandardTruck) b).Resume();
			else if(b instanceof Van)
				((Van) b).Resume();
		}
		
		notify();
	}
}

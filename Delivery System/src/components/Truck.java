package components;

import java.util.ArrayList;
import java.util.Random;


public  abstract class Truck extends Thread implements Node {
	private static int countID=2000;
	final private int truckID;
	private String licensePlate;
	private String truckModel;
	private boolean available=true;
	private int timeLeft=0;
	private ArrayList<Package> packages=new ArrayList<Package>();
	
	
	
	
	
	/**

	 * @param x - x cordinate of truck
	 * @param y - y cordinate of truck
	 * @param tms - save initialize of time left
	 * @param previous - save previous time of the truck.
	 * @param dx - save x length.
	 * @param dy - saves y length.
	 */
	private float x;
	private float y;
	private int tms;
	private int previous;
	private float dx;
	private float dy;
	
	//default random constructor
	public Truck() {
		truckID=countID++;
		Random r= new Random();
		licensePlate=(r.nextInt(900)+100)+"-"+(r.nextInt(90)+10)+"-"+(r.nextInt(900)+100);
		truckModel="M"+r.nextInt(5);
		System.out.print("Creating ");
	}

	
	public Truck(String licensePlate,String truckModel) {
		truckID=countID++;
		this.licensePlate=licensePlate;
		this.truckModel=truckModel;
		System.out.print("Creating ");
	}
	
	public abstract void run();
	
	public ArrayList<Package> getPackages() {
		return packages;
	}


	public int getTimeLeft() {
		return timeLeft;
	}

	
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}


	@Override
	public String toString() {
		return "truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel + ", available= " + available ;
	}


	@Override
	public void collectPackage(Package p) {
		setAvailable(false);
		int time= (p.getSenderAddress().getStreet()%10+1)* 10;
		this.setTms(time);																									//set Tms
		this.setTimeLeft(time);
		this.packages.add(p);
		p.setStatus(Status.COLLECTION);
		p.addTracking(new Tracking(MainOffice.getClock(), this, p.getStatus()));
		System.out.println(GetName() + " is collecting package " + p.getPackageID() + ", time to arrive: "+ getTimeLeft()  );
	}


	public boolean isAvailable() {
		return available;
	}
	

	public int getTruckID() {
		return truckID;
	}

	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	public void addPackage(Package p) {
		this.packages.add(p);
	}
	
	
	public String GetName() {
		return this.getClass().getSimpleName()+" "+ getTruckID();
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}

	public int getTms() {
		return tms;
	}


	public void setTms(int tms) {
		this.tms = tms;
	}


	public float getDx() {
		return dx;
	}


	public void setDx(float dx2) {
		this.dx = dx2;
	}


	public float getDy() {
		return dy;
	}


	public void setDy(float dy) {
		this.dy = dy;
	}


	public int getPrevious() {
		return previous;
	}


	public void setPrevious(int previous) {
		this.previous = previous;
	}
	
}

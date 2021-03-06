package components;

import java.util.Random;

public class NonStandardTruck extends Truck{
	private int width, length, height;	
	
	
	/**
	 * @param suspendFlag - stop and resume the thread.
	 */
	private  boolean suspendFlag = false;


	public NonStandardTruck() {
		super();
		Random r=new Random();
		width=(r.nextInt(3)+2)*100;
		length=(r.nextInt(6)+10)*100;
		height=(r.nextInt(2)+3)*100;
		System.out.println(this);
	}
	
	
	public NonStandardTruck(String licensePlate,String truckModel, int length, int width, int height) {
		super(licensePlate,truckModel);
		this.width=width;
		this.length=length;
		this.height=height;
		System.out.println(this);
	}

	/**
	 * @function - run thread 
	 */
	public void run()
	{
		while(MainOffice.isDone() != true)
		{
			this.work();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(suspendFlag) {                             //stop thread.
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				}
			}
		}
	}
	
	public int getWidth() {
		return width;
	}

	
	public void setWidth(int width) {
		this.width = width;
	}

	
	public int getLength() {
		return length;
	}

	
	public void setLength(int length) {
		this.length = length;
	}

	
	public int getHeight() {
		return height;
	}

	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	@Override
	public void work() {
		if (!this.isAvailable()) {
			Package p=this.getPackages().get(0);
			this.setTimeLeft(this.getTimeLeft()-1);
			if (this.getTimeLeft()==0) {
				if (p.getStatus()==Status.COLLECTION) {
					System.out.println(GetName() + " has collected "+p.getName());
					deliverPackage(p);
				}
					
				else {
					System.out.println(GetName() + " has delivered "+p.getName() + " to the destination");
					this.getPackages().remove(p);
					p.addRecords(Status.DELIVERED, null);
					setAvailable(true);
				}
			}
		}
	}
	
	
	@Override
	public void deliverPackage (Package p)  {
		int time=(Math.abs(p.getDestinationAddress().getStreet()-p.getSenderAddress().getStreet())%10+1) * 10;
		this.setTimeLeft(time);
		this.setTms(time);                                                                                                               //set Tms
		p.addRecords(Status.DISTRIBUTION, this);
		System.out.println(GetName() + " is delivering " + p.getName() + ", time left: "+ this.getTimeLeft()  );
	}
	
	
	@Override
	public String toString() {
		return "NonStandardTruck ["+ super.toString() + ", length=" + length +  ", width=" + width + ", height="
				+ height + "]";
	}
	
	/**
	 * @function - change suspandFlag
	 */
	public synchronized void Suspend() {
		this.suspendFlag = true;
	}

	/**
	 * @function - resume the thread.
	 */
	public synchronized void Resume() {
		this.suspendFlag = false;
		notify();
	}
}


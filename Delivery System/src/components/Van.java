package components;

public class Van extends Truck{
	/**
	 * @param suspendFlag - stop and resume the thread.
	 */
	private  boolean suspendFlag = false;
	
	public Van() {
		super();
		System.out.println(this);
	}
	
	/**
	 * @function - run the thread
	 */
	public void run()
	{
		while(MainOffice.isDone() != true)
		{
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while(suspendFlag) {                                    //stop thread
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					}
				}
			
			this.work();
		}
			
	}
	
	public Van(String licensePlate,String truckModel) {
		super(licensePlate,truckModel);
		System.out.println(this);
	}
	

	@Override
	public String toString() {
		return "Van ["+ super.toString() + "]";
	}
	
	
	@Override
	public void deliverPackage(Package p) {
		addPackage(p);
		setAvailable(false);
		int time=(p.getDestinationAddress().getStreet()%10+1)* 10;
		this.setTms(time);                                                    //set tms  
		this.setTimeLeft(time);                                                                                
		p.addRecords(Status.DISTRIBUTION, this);
		System.out.println(GetName() + " is delivering " + p.getName() + ", time left: "+ getTimeLeft()  );
	}
	
	
	@Override
	public  void work() {
		if (!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			if (this.getTimeLeft()==0){
				for (Package p : this.getPackages()) {
					if (p.getStatus()==Status.COLLECTION) {
						p.addRecords(Status.BRANCH_STORAGE,p.getSenderBranch());
						System.out.println(GetName() + " has collected " +p.getName()+" and arrived back to " + p.getSenderBranch().GetName());
					}
					else {
						p.addRecords(Status.DELIVERED, null);
						p.getDestBranch().removePackage(p);
						System.out.println(GetName() + " has delivered "+p.getName() + " to the destination");
						if (p instanceof SmallPackage && ((SmallPackage)p).isAcknowledge()) {
							System.out.println("Acknowledge sent for "+p.getName());
						}
					}
				}
				this.getPackages().removeAll(getPackages());
				this.setAvailable(true);
			}
		}
	}
	
	
	/**
	 * @function - change flag
	 */
	public synchronized void Suspend() {
		this.suspendFlag = true;
	}

/**
 * @function - resume thread.
 */
	public synchronized void Resume() {
		this.suspendFlag = false;
		notify();
	}
}

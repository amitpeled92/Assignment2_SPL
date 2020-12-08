package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	/**
	 * this class was build as a Semaphore
	 */
	int serialNumber;
	boolean available;
	int counter;

	public Ewok(){
	    serialNumber=counter;
	    available = true;
	    counter++;
    }
  
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire() {
		while(available==false){
			try {
				wait();
			}
			catch (InterruptedException e){}
		}
    	available = true;
    }

    /**
     * release an Ewok
     */
    public void release() {
    	if(available==true){
    		available = false;
    		notifyAll();
		}
    }
}

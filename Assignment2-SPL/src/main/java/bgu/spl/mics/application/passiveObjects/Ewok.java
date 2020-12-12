package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
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
    public void acquire() {
		available = false;
    }

    /**
     * release an Ewok
     */
    public void release() {
    	available = true;
    }

	/**
	 * checking if the specific ewok is available
	 * @return available variable
	 */
	public boolean isAvailable() {
		return available;
	}
}

package interpretationFichierXML;

import java.util.Collection;


public class Interchange {

	/**
	 * @uml.property  name="Start"
	 * @uml.associationEnd  inverse="interchange:interpretationFichierXML.Couple"
	 */
	private Couple start;

	/**
	 * Getter of the property <tt>Start</tt>
	 * @return  Returns the start.
	 * @uml.property  name="Start"
	 */
	public Couple getStart() {
		return start;
	}

	/**
	 * Setter of the property <tt>Start</tt>
	 * @param Start  The start to set.
	 * @uml.property  name="Start"
	 */
	public void setStart(Couple start) {
		this.start = start;
	}

	/**
	 * @uml.property  name="Endlist"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="interchange:interpretationFichierXML.Couple"
	 */
	private Collection endlist;

	/**
	 * Getter of the property <tt>Endlist</tt>
	 * @return  Returns the endlist.
	 * @uml.property  name="Endlist"
	 */
	public Collection getEndlist() {
		return endlist;
	}

	/**
	 * Setter of the property <tt>Endlist</tt>
	 * @param Endlist  The endlist to set.
	 * @uml.property  name="Endlist"
	 */
	public void setEndlist(Collection endlist) {
		this.endlist = endlist;
	}

	/**
	 * @uml.property  name="Free"
	 */
	private boolean free;

	/**
	 * Getter of the property <tt>Free</tt>
	 * @return  Returns the free.
	 * @uml.property  name="Free"
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * Setter of the property <tt>Free</tt>
	 * @param Free  The free to set.
	 * @uml.property  name="Free"
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * @uml.property  name="Pedestrian"
	 */
	private boolean pedestrian;

	/**
	 * Getter of the property <tt>Pedestrian</tt>
	 * @return  Returns the pedestrian.
	 * @uml.property  name="Pedestrian"
	 */
	public boolean isPedestrian() {
		return pedestrian;
	}

	/**
	 * Setter of the property <tt>Pedestrian</tt>
	 * @param Pedestrian  The pedestrian to set.
	 * @uml.property  name="Pedestrian"
	 */
	public void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	/**
	 * @uml.property  name="Time"
	 */
	private byte time;

	/**
	 * Getter of the property <tt>Time</tt>
	 * @return  Returns the time.
	 * @uml.property  name="Time"
	 */
	public byte getTime() {
		return time;
	}

	/**
	 * Setter of the property <tt>Time</tt>
	 * @param Time  The time to set.
	 * @uml.property  name="Time"
	 */
	public void setTime(byte time) {
		this.time = time;
	}

}

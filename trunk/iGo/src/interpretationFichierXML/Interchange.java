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

}

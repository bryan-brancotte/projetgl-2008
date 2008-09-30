package graphNetwork;

import java.util.LinkedList;


public interface PathInGraph {

	/**
	 * @return  Returns the inter.
	 * @uml.property  name="inter"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" container="java.util.LinkedList" inverse="pathInGraph:graphNetwork.Inter"
	 */
	public LinkedList<Inter> getInter();

	/**
	 * Setter of the property <tt>inter</tt>
	 * @param inter  The inter to set.
	 * @uml.property  name="inter"
	 */
	public void setInter(LinkedList<Inter> inter);

}

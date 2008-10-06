package graphNetwork;

import java.util.LinkedList;

public class PathInGraph implements PathInGraphBuilder {

	@Override
	public InterReader[] getInterR() {
		// TODO Auto-generated method stub
		return inter.toArray(new Inter[0]);
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addLast(Inter inter) {
		this.inter.addLast(inter);
	}

	public void addFront(InterReader inter) {
	}

	public void flush() {
	}

	public String exportPath() {
		return "";
	}

	public void importPath(String pathInString) {
	}

	public PathInGraph(GraphNetworkBuilder graph) {
	}

	@SuppressWarnings("unused")
	private PathInGraph() {
	}

	public String toString() {
		return "";
	}

	@Override
	public GraphNetworkReader getGraphR() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="pathInGraph:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

	/**
	 * Getter of the property <tt>inter</tt>
	 * 
	 * @return Returns the inter.
	 * @uml.property name="inter"
	 */
	public LinkedList<Inter> getInter() {
		return inter;
	}

	/**
	 * Setter of the property <tt>inter</tt>
	 * 
	 * @param inter
	 *            The inter to set.
	 * @uml.property name="inter"
	 */
	public void setInter(LinkedList<Inter> inter) {
		this.inter = inter;
	}

}

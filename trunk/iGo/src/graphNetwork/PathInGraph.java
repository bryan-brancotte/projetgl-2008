package graphNetwork;

import java.util.LinkedList;

public class PathInGraph implements PathInGraphBuilder {

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="pathInGraph:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

	@SuppressWarnings("unused")
	private PathInGraph() {
	}

	public PathInGraph(GraphNetworkBuilder graph) {
	}

	@Override
	public void addFront(InterReader inter) {
	}

	@Override
	public void addLast(Inter inter) {
		this.inter.addLast(inter);
	}

	public String exportPath() {
		return "";
	}

	@Override
	public void flush() {
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GraphNetworkReader getGraphR() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter of the property <tt>inter</tt>
	 * 
	 * @return Returns the inter.
	 * @uml.property name="inter"
	 */
	public LinkedList<Inter> getInter() {
		return inter;
	}

	@Override
	public InterReader[] getInterR() {
		// TODO Auto-generated method stub
		return inter.toArray(new Inter[0]);
	}

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void importPath(String pathInString) {
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

	@Override
	public String toString() {
		return "";
	}

	@Override
	public InterReader getFirstInterInTheLastAvaiblePart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStillAvaible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStillAvaible(InterReader inter) {
		// TODO Auto-generated method stub
		return false;
	}

}

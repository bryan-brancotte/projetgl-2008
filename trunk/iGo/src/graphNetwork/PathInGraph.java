package graphNetwork;

import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class PathInGraph {

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="pathInGraph:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

	@SuppressWarnings("unused")
	private PathInGraph() {
	}

	protected PathInGraph(GraphNetwork graph) {
	}

	protected void addFront(Inter inter) {
	}

	protected void addLast(Inter inter) {
		this.inter.addLast(inter);
	}

	public String exportPath() {
		return "";
	}

	protected void flush() {
	}

	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	public GraphNetworkBuilder getGraph() {
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

	public int getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

	public String toString() {
		return "";
	}

	public Inter getFirstInterInTheLastAvaiblePart() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isStillAvaible() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStillAvaible(Inter inter) {
		// TODO Auto-generated method stub
		return false;
	}

}

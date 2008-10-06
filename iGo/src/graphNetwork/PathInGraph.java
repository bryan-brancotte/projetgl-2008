package graphNetwork;

import GraphNetworkBuilder;
import java.util.LinkedList;

public class PathInGraph implements PathInGraphBuilder {

	protected LinkedList<Inter> inter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#getInter()
	 */
	@Override
	public InterReader[] getInter() {
		// TODO Auto-generated method stub
		return inter.toArray(new Inter[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#getCost()
	 */
	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#getTime()
	 */
	@Override
	public byte getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#add(graphNetwork.Inter)
	 */
	public void add(Inter inter) {
		this.inter.addLast(inter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#addFront(graphNetwork.InterReader)
	 */
	public void addFront(InterReader inter) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#flush()
	 */
	public void flush() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#exportPath()
	 */
	public String exportPath() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.PathInGraphBuilder#importPath(java.lang.String)
	 */
	public void importPath(String pathInString) {
	}

	public PathInGraph(GraphNetworkBuilder graph) {
	}

	private PathInGraph() {
	}

}

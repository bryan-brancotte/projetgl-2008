package iGoMaster;

import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetwork;
import graphNetwork.PathInGraph;
import graphNetwork.Station;

import java.util.Observable;
import java.util.Observer;

public abstract class Algo extends Observable {

	public PathInGraph findPath(Station origine,
			Station destination, GraphNetwork graph,
			PathInGraph pathInGraphBuilder,
			CriteriousForTheLowerPath criterious) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
		 */
	public void setChanged() {
	}

	/**
			 */
	public void addObserver(Observer o) {
	}

	/**
				 */
	public void notifyObserver() {
	}

	/**
	 * @uml.property name="iGoMaster"
	 * @uml.associationEnd inverse="algo1:iGoMaster.IGoMaster"
	 */
	private IGoMaster goMaster;

	/**
	 * Getter of the property <tt>iGoMaster</tt>
	 * 
	 * @return Returns the goMaster.
	 * @uml.property name="iGoMaster"
	 */
	public IGoMaster getIGoMaster() {
		return goMaster;
	}

	/**
	 * Setter of the property <tt>iGoMaster</tt>
	 * 
	 * @param iGoMaster
	 *            The goMaster to set.
	 * @uml.property name="iGoMaster"
	 */
	public void setIGoMaster(IGoMaster goMaster) {
		this.goMaster = goMaster;
	}

}

package iGoMaster;

import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetwork;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.Station;

import java.util.Observable;
import java.util.Observer;

public abstract class Algo extends Observable {

	/**
	 * fonction pour demander la résolution d'un nouveau chemin. On spécifie le départ et l'arrivé, on fournit le
	 * réseau, et le monteur de PathInGraph avec un PathInGraph vide à l'interrieur.
	 * 
	 * @param _origine
	 * @param _destination
	 * @param _graph
	 * @param _path
	 * @param _criterious
	 * @return
	 */
	public PathInGraph findPath(Station _origine, Station _destination, GraphNetwork _graph, PathInGraphBuilder _path,
			CriteriousForTheLowerPath _criterious) {
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

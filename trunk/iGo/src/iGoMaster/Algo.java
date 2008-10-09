package iGoMaster;

import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;

public interface Algo {

	/**
	 * Trouve le meilleur chemin dans le graph pass� en param�tre en l'origine et la destination. Il construit le chemin
	 * dans l'objet pathInGraphBuilder pass� en param�tre et le retourne sous la forme d'un objet passeInGraphReader
	 * 
	 * @param origine
	 *            la station de d�part
	 * @param destination
	 *            la station de fin
	 * @param graph
	 *            le graph o� l'on �volut
	 * @param pathInGraphBuilder
	 *            l'objet qui va contenir le chemin.
	 * @return l'objet contenant le chemin
	 */
	public abstract PathInGraphReader findPath(StationReader origine, StationReader destination,
			GraphNetworkReader graph, PathInGraphBuilder pathInGraphBuilder);

}

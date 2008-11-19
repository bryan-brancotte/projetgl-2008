package iGoMaster;

import java.util.Observable;

import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;
/**
 *  
 * @author iGo
 */
public interface Algo {

	
	/**
	 * Trouve le meilleur chemin dans le graph passé en paramètre en l'origine et la destination. Il construit le chemin
	 * dans l'objet pathInGraphBuilder passé en paramètre et le retourne sous la forme d'un objet passeInGraphReader
	 * 
	 * @param origine
	 *            la station de départ
	 * @param destination
	 *            la station de fin
	 * @param graph
	 *            le graph où l'on évolut
	 * @param pathInGraphBuilder
	 *            l'objet qui va contenir le chemin.
	 * @param criterious
	 *            le critère pour la comparaison à utiliser
	 * @return l'objet contenant le chemin
	 */
	public abstract PathInGraphReader findPath(StationReader origine, StationReader destination,
			GraphNetworkReader graph, PathInGraphBuilder pathInGraphBuilder, CriteriousForTheLowerPath criterious);
}

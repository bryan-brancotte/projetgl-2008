package iGoMaster.exception;

import graphNetwork.Station;

/**
 * Exception signifiant que la station s ne fait pas partie des stations atteignables du graph.
 * Ceci en partant du point origine défini dans le pathInGraph.
 * 
 * @author iGo
 *
 */
public class StationNotAccessibleException extends Exception {

	private static final long serialVersionUID = -7576722538422121815L;
	private Station s;

	public StationNotAccessibleException(Station _s) {
		s = _s;
	}

	public Station getStation() {
		return s;
	}
}

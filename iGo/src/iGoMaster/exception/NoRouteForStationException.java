package iGoMaster.exception;

import graphNetwork.Station;


/**
 * Erreur indiquant que la station s ne se trouve sur aucune route et n'est donc pas liée au réseau physiquement.
 * 
 * @author iGo
 *
 */
public class NoRouteForStationException extends Exception {
	
	Station s;
	
	public NoRouteForStationException(Station _s) {
		s=_s;
	}
	
	public Station getStation() {return s;}
	
	private static final long serialVersionUID = 1L;

}

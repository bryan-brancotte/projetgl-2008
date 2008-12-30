package algorithm.exception;

import graphNetwork.Route;
import graphNetwork.Station;

/**
 * Le couple (station,route) n'existe pas dans le graph de l'algo.
 * 
 * @author tony
 *
 */
public class NodeNotFoundException extends Exception {

	private Station s;
	private Route r;
	private static final long serialVersionUID = 4292128266811768021L;

	public NodeNotFoundException(Station _s,Route _r) {
		s=_s;
		r=_r;
	}
	
	public Station getStation() {return s;}
	public Route getRoute() {return r;}
}

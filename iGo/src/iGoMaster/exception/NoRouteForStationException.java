package iGoMaster.exception;

import graphNetwork.Route;
import graphNetwork.Station;

public class NoRouteForStationException extends Exception {
	
	Station s;
	
	public NoRouteForStationException(Station _s) {
		s=_s;
	}
	
	public Station getStation() {return s;}
	
	private static final long serialVersionUID = 1L;

}

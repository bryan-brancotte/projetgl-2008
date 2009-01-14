package algorithm.exception;

import graphNetwork.Station;

/**
 * Exception générique pour toutes les intérruptions dus à un service.
 * 
 * @author iGo
 *
 */
public class NullStationException extends Exception{

	private static final long serialVersionUID = 1275671165380556767L;
	Station s;
	
	public NullStationException() {}
}
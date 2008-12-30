package algorithm.exception;

import graphNetwork.Service;

import java.util.ArrayList;

/**
 * Exception générique pour toutes les intérruptions dus à un service.
 * 
 * @author tony
 *
 */
public class NonValidServicesException extends NonValidPathException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1275671165380556767L;
	ArrayList<Service> s;
	
	public NonValidServicesException(ArrayList<Service> _s) {
		s = _s;
	}
}

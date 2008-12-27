package algorithm.exception;

import graphNetwork.Service;

import java.util.ArrayList;


public class NonValidDestinationException extends NonValidServicesException {

	public NonValidDestinationException(ArrayList<Service> _s) {
		super(_s);
	}

	private static final long serialVersionUID = 7482936428544459978L;
	

}

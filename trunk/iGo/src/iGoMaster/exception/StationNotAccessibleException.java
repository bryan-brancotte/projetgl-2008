package iGoMaster.exception;

import graphNetwork.Station;

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

package iGoMaster.exception;

import graphNetwork.Service;

public class ServiceNotAccessibleException extends Exception {

	private Service s;
	private static final long serialVersionUID = 6167780731136787284L;

	public ServiceNotAccessibleException(Service _s) {
		s = _s;
	}

	public Service getService() {
		return s;
	}
}

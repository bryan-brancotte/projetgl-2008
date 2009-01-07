package iGoMaster.exception;

import graphNetwork.Service;

/**
 * Exception signifiant que le service s, n'est pas accessible.
 * Ceci en partant du point origine défini dans le pathInGraph en parcourant toutes les stations accessibles du graph.
 * 
 * @author iGo
 *
 */
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

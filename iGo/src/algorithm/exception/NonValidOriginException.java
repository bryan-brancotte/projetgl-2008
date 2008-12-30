package algorithm.exception;

import graphNetwork.Service;

import java.util.ArrayList;

/**
 * L'origine ne possede pas le service s qui est un critere obligatoire à tous les changements dans la recherche.
 * 
 * @author tony
 *
 */
public class NonValidOriginException extends NonValidServicesException {

	public NonValidOriginException(ArrayList<Service> _s) {
		super(_s);
	}

	private static final long serialVersionUID = 7482936428544459978L;
	

}

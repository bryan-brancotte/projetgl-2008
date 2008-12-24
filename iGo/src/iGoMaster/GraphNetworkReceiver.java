package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;

import java.util.Iterator;

/**
 * 
 * @author iGo
 */
public interface GraphNetworkReceiver {

	/**
	 * Construit un GraphNetwork a partir d'un fichier reseau
	 * 
	 * @param graph Le GraphNetwork qui va etre ajoute au reseau
	 * @param networkChosen le reseau que nous voulons importer
	 * @param costReceiver Interface qui donne le cout de chaque changement : changements entre les lignes...
	 * @throws GraphReceptionException Si la reception du reseau a ete impossible, par exemple si le fichier n'existe pas
	 * @throws GraphConstructionException Si la construction du graphe a ete impossible
	 */
	public void buildNewGraphNetwork(GraphNetworkBuilder graph, String networkChosen,
			GraphNetworkCostReceiver costReceiver) throws GraphReceptionException, GraphConstructionException;
	
	/**
	 * Donne tous les reseaux disponibles
	 * 
	 * @return Returns the collection of the avaibleNetwork.
	 */
	public Iterator<AvailableNetwork> getAvaibleNetwork();

}

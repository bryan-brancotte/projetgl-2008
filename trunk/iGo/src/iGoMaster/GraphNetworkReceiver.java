package iGoMaster;

import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import graphNetwork.GraphNetworkBuilder;
import java.util.Collection;

public interface GraphNetworkReceiver {

	/**
	 * Build a new graphNetwork from an avaible network.
	 * 
	 * @param graph
	 *            the graphNetwork where we'll put the network.
	 * @param networkChosen
	 *            the network we want to import
	 * @return the thread where the loading will be done.
	 * @throws GraphReceptionException
	 *             if the reception of the network was impossible. for exemple it's not where it's supposed to be
	 * @throws GraphConstructionException
	 *             if we couldn't build the graph.
	 */
	public Thread buildNewGraphNetwork(GraphNetworkBuilder graph, AvaibleNetwork networkChosen)
			throws GraphReceptionException, GraphConstructionException;

	/**
	 * get all the avaible network we can load.
	 * @return Returns the collection of the avaibleNetwork.
	 * @uml.property name="avaibleNetwork"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" inverse="graphNetworkReceiver:iGoMaster.AvaibleNetwork"
	 */
	public Collection<AvaibleNetwork> getAvaibleNetwork();

}
package ihm.smartPhone;

import graphNetwork.GraphNetworkReader;
import ihm.JNetwork.JNetworkModel;


public class JNetworkModelImpl implements JNetworkModel {

	/**
	 * @uml.property   name="graphNetwork"
	 * @uml.associationEnd   inverse="jNetworkModelImpl:graphNetwork.GraphNetworkReader"
	 */
	private GraphNetworkReader graphNetwork;

	/**
	 * Getter of the property <tt>graphNetwork</tt>
	 * @return  Returns the graphNetwork.
	 * @uml.property  name="graphNetwork"
	 */
	public GraphNetworkReader getGraphNetwork() {
		return graphNetwork;
	}

	/**
	 * Setter of the property <tt>graphNetwork</tt>
	 * @param graphNetwork  The graphNetwork to set.
	 * @uml.property  name="graphNetwork"
	 */
	public void setGraphNetwork(GraphNetworkReader graphNetwork) {
		this.graphNetwork = graphNetwork;
	}

}

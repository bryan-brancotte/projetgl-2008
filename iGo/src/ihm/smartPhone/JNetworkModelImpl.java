package ihm.smartPhone;

import ihm.JNetwork.JNetworkModel;
import main.GraphNetwork;


public class JNetworkModelImpl implements JNetworkModel {

	/**
	 * @uml.property  name="graphNetwork"
	 * @uml.associationEnd  inverse="jNetworkModelImpl:main.GraphNetwork"
	 */
	private GraphNetwork graphNetwork;

	/**
	 * Getter of the property <tt>graphNetwork</tt>
	 * @return  Returns the graphNetwork.
	 * @uml.property  name="graphNetwork"
	 */
	public GraphNetwork getGraphNetwork() {
		return graphNetwork;
	}

	/**
	 * Setter of the property <tt>graphNetwork</tt>
	 * @param graphNetwork  The graphNetwork to set.
	 * @uml.property  name="graphNetwork"
	 */
	public void setGraphNetwork(GraphNetwork graphNetwork) {
		this.graphNetwork = graphNetwork;
	}

}

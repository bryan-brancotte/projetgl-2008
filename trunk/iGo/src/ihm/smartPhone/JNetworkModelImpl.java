package ihm.smartPhone;

import ihm.JNetwork.JNetworkModel;
import graphNetwork.IGraphNetwork;
import main.GraphNetwork;


public class JNetworkModelImpl implements JNetworkModel {

	/**
	 * @uml.property   name="graphReseau"
	 * @uml.associationEnd   inverse="jReseauModelImpl:graphNetwork.IGraphReseau"
	 */
	private IGraphNetwork graphReseau;

	/**
	 * Getter of the property <tt>graphReseau</tt>
	 * @return  Returns the graphReseau.
	 * @uml.property  name="graphReseau"
	 */
	public IGraphNetwork getGraphReseau() {
		return graphReseau;
	}

	/**
	 * Setter of the property <tt>graphReseau</tt>
	 * @param graphReseau  The graphReseau to set.
	 * @uml.property  name="graphReseau"
	 */
	public void setGraphReseau(IGraphNetwork graphReseau) {
		this.graphReseau = graphReseau;
	}

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

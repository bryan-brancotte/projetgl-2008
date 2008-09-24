package ihm.smartPhone;

import ihm.JReseau.JNetworkModel;
import graphReseau.IGraphReseau;


public class JNetworkModelImpl implements JNetworkModel {

	/**
	 * @uml.property  name="graphReseau"
	 * @uml.associationEnd  inverse="jReseauModelImpl:graphReseau.IGraphReseau"
	 */
	private IGraphReseau graphReseau;

	/**
	 * Getter of the property <tt>graphReseau</tt>
	 * @return  Returns the graphReseau.
	 * @uml.property  name="graphReseau"
	 */
	public IGraphReseau getGraphReseau() {
		return graphReseau;
	}

	/**
	 * Setter of the property <tt>graphReseau</tt>
	 * @param graphReseau  The graphReseau to set.
	 * @uml.property  name="graphReseau"
	 */
	public void setGraphReseau(IGraphReseau graphReseau) {
		this.graphReseau = graphReseau;
	}

}

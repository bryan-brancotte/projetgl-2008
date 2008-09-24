package iGoMaster;

import java.util.Observable;
import java.util.Observer;

import graphNetwork.IGraphNetwork;
import graphNetwork.PathInGraph;
import ihm.smartPhone.Master;
import main.GraphReader;
import main.GraphNetwork;

public class IGoMaster implements Master, Observer{

	/**
	 * @uml.property   name="ihm"
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.IHM"
	 */
	private IHM ihm;

	/**
	 * Getter of the property <tt>ihm</tt>
	 * 
	 * @return Returns the ihm.
	 * @uml.property name="ihm"
	 */
	public IHM getIhm() {
		return ihm;
	}

	/**
	 * Setter of the property <tt>ihm</tt>
	 * 
	 * @param ihm
	 *            The ihm to set.
	 * @uml.property name="ihm"
	 */
	public void setIhm(IHM ihm) {
		this.ihm = ihm;
	}

	/**
	 */
	public void start() {
	}

	/**
	 * @uml.property   name="graphReader"
	 * @uml.associationEnd   inverse="iGoMaster:main.GraphReader"
	 */
	private GraphReader graphReader;

	/**
	 * Getter of the property <tt>graphReader</tt>
	 * @return  Returns the graphReader.
	 * @uml.property  name="graphReader"
	 */
	public GraphReader getGraphReader() {
		return graphReader;
	}

	/**
	 * Setter of the property <tt>graphReader</tt>
	 * @param graphReader  The graphReader to set.
	 * @uml.property  name="graphReader"
	 */
	public void setGraphReader(GraphReader graphReader) {
		this.graphReader = graphReader;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @uml.property   name="config"
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.Configuration"
	 */
	private Configuration config;

	/**
	 * Getter of the property <tt>config</tt>
	 * @return  Returns the config.
	 * @uml.property  name="config"
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Setter of the property <tt>config</tt>
	 * @param config  The config to set.
	 * @uml.property  name="config"
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}

	/**
	 * @uml.property   name="mesParcours"
	 * @uml.associationEnd   inverse="iGoMaster:graphNetwork.PathInGraph"
	 */
	private PathInGraph mesParcours;

	/**
	 * Getter of the property <tt>mesParcours</tt>
	 * @return  Returns the mesParcours.
	 * @uml.property  name="mesParcours"
	 */
	public PathInGraph getMesParcours() {
		return mesParcours;
	}

	/**
	 * Setter of the property <tt>mesParcours</tt>
	 * @param mesParcours  The mesParcours to set.
	 * @uml.property  name="mesParcours"
	 */
	public void setMesParcours(PathInGraph mesParcours) {
		this.mesParcours = mesParcours;
	}

	/**
	 * @uml.property  name="graphNetwork"
	 * @uml.associationEnd  inverse="iGoMaster:main.GraphNetwork"
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

	/**
	 * @uml.property  name="lang"
	 * @uml.associationEnd  inverse="iGoMaster:iGoMaster.Language"
	 */
	private Language lang;

	/**
	 * Getter of the property <tt>lang</tt>
	 * @return  Returns the lang.
	 * @uml.property  name="lang"
	 */
	public Language getLang() {
		return lang;
	}

	/**
	 * Setter of the property <tt>lang</tt>
	 * @param lang  The lang to set.
	 * @uml.property  name="lang"
	 */
	public void setLang(Language lang) {
		this.lang = lang;
	}

	/**
	 * @uml.property  name="algo"
	 * @uml.associationEnd  inverse="iGoMaster:iGoMaster.Algo"
	 */
	private Algo algo;

	/**
	 * Getter of the property <tt>algo</tt>
	 * @return  Returns the algo.
	 * @uml.property  name="algo"
	 */
	public Algo getAlgo() {
		return algo;
	}

	/**
	 * Setter of the property <tt>algo</tt>
	 * @param algo  The algo to set.
	 * @uml.property  name="algo"
	 */
	public void setAlgo(Algo algo) {
		this.algo = algo;
	}

}

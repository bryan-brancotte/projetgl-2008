package iGoMaster;

import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphReader;

import ihm.smartPhone.IHM;

import java.util.Observable;
import java.util.Observer;

import graphNetwork.GraphNetworkBuilder;

public class IGoMaster implements Master, Observer {

	/**
	 * @uml.property   name="ihm"
	 * @uml.associationEnd   inverse="iGoMaster:ihm.smartPhone.IHM"
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
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.GraphNetworkReceiver"
	 */
	private GraphNetworkReceiver graphReader;

	/**
	 * Getter of the property <tt>graphReader</tt>
	 * 
	 * @return Returns the graphReader.
	 * @uml.property name="graphReader"
	 */
	public GraphNetworkReceiver getGraphReader() {
		return graphReader;
	}

	/**
	 * Setter of the property <tt>graphReader</tt>
	 * 
	 * @param graphReader
	 *            The graphReader to set.
	 * @uml.property name="graphReader"
	 */
	public void setGraphReader(GraphNetworkReceiver graphReader) {
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
	 * 
	 * @return Returns the config.
	 * @uml.property name="config"
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Setter of the property <tt>config</tt>
	 * 
	 * @param config
	 *            The config to set.
	 * @uml.property name="config"
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}

	/**
	 * @uml.property   name="graphNetwork"
	 * @uml.associationEnd   inverse="iGoMaster:graphNetwork.GraphNetworkReader"
	 */
	private GraphNetworkReader graphNetwork;

	/**
	 * Getter of the property <tt>graphNetwork</tt>
	 * 
	 * @return Returns the graphNetwork.
	 * @uml.property name="graphNetwork"
	 */
	public GraphNetworkReader getGraphNetwork() {
		return graphNetwork;
	}

	/**
	 * Setter of the property <tt>graphNetwork</tt>
	 * 
	 * @param graphNetwork
	 *            The graphNetwork to set.
	 * @uml.property name="graphNetwork"
	 */
	public void setGraphNetwork(GraphNetworkReader graphNetwork) {
		this.graphNetwork = graphNetwork;
	}

	/**
	 * @uml.property   name="lang"
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.Language"
	 */
	private Language lang;

	/**
	 * Getter of the property <tt>lang</tt>
	 * 
	 * @return Returns the lang.
	 * @uml.property name="lang"
	 */
	public Language getLang() {
		return lang;
	}

	/**
	 * Setter of the property <tt>lang</tt>
	 * 
	 * @param lang
	 *            The lang to set.
	 * @uml.property name="lang"
	 */
	public void setLang(Language lang) {
		this.lang = lang;
	}

	/**
	 * @uml.property   name="algo"
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.Algo"
	 */
	private Algo algo;

	/**
	 * Getter of the property <tt>algo</tt>
	 * 
	 * @return Returns the algo.
	 * @uml.property name="algo"
	 */
	public Algo getAlgo() {
		return algo;
	}

	/**
	 * Setter of the property <tt>algo</tt>
	 * 
	 * @param algo
	 *            The algo to set.
	 * @uml.property name="algo"
	 */
	public void setAlgo(Algo algo) {
		this.algo = algo;
	}

	/**
	 * @uml.property   name="pathInGraph"
	 * @uml.associationEnd   inverse="iGoMaster:graphNetwork.PathInGraphReader"
	 */
	private PathInGraphReader pathInGraph;

	/**
	 * Getter of the property <tt>pathInGraph</tt>
	 * 
	 * @return Returns the pathInGraph.
	 * @uml.property name="pathInGraph"
	 */
	public PathInGraphReader getPathInGraph() {
		return pathInGraph;
	}

	/**
	 * Setter of the property <tt>pathInGraph</tt>
	 * 
	 * @param pathInGraph
	 *            The pathInGraph to set.
	 * @uml.property name="pathInGraph"
	 */
	public void setPathInGraph(PathInGraphReader pathInGraph) {
		this.pathInGraph = pathInGraph;
	}

	/**
	 * @uml.property   name="graphNetworkBuilder"
	 * @uml.associationEnd   inverse="iGoMaster:graphNetwork.GraphNetworkBuilder"
	 */
	private GraphNetworkBuilder graphNetworkBuilder;

	/**
	 * Getter of the property <tt>graphNetworkBuilder</tt>
	 * 
	 * @return Returns the graphNetworkBuilder.
	 * @uml.property name="graphNetworkBuilder"
	 */
	public GraphNetworkBuilder getGraphNetworkBuilder() {
		return graphNetworkBuilder;
	}

	/**
	 * Setter of the property <tt>graphNetworkBuilder</tt>
	 * 
	 * @param graphNetworkBuilder
	 *            The graphNetworkBuilder to set.
	 * @uml.property name="graphNetworkBuilder"
	 */
	public void setGraphNetworkBuilder(GraphNetworkBuilder graphNetworkBuilder) {
		this.graphNetworkBuilder = graphNetworkBuilder;
	}

	/**
	 * @uml.property  name="eventInfoNetwork"
	 * @uml.associationEnd  inverse="iGoMaster:iGoMaster.EventInfoNetwork"
	 */
	private EventInfoNetwork eventInfoNetwork;

	/**
	 * Getter of the property <tt>eventInfoNetwork</tt>
	 * @return  Returns the eventInfoNetwork.
	 * @uml.property  name="eventInfoNetwork"
	 */
	public EventInfoNetwork getEventInfoNetwork() {
		return eventInfoNetwork;
	}

	/**
	 * Setter of the property <tt>eventInfoNetwork</tt>
	 * @param eventInfoNetwork  The eventInfoNetwork to set.
	 * @uml.property  name="eventInfoNetwork"
	 */
	public void setEventInfoNetwork(EventInfoNetwork eventInfoNetwork) {
		this.eventInfoNetwork = eventInfoNetwork;
	}

}

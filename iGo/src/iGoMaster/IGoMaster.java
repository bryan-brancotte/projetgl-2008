package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import ihm.smartPhone.IHM;

import java.util.Observable;
import java.util.Observer;
/**
 *  
 * @author iGo
 */
public class IGoMaster implements Master, Observer {

	/**
	 * @uml.property name="ihm"
	 * @uml.associationEnd inverse="iGoMaster:ihm.smartPhone.IHM"
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
	 * @uml.property name="graphReceiver"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.GraphNetworkReceiver"
	 */
	private GraphNetworkReceiver graphReceiver;

	/**
	 * Getter of the property <tt>graphReader</tt>
	 * 
	 * @return Returns the graphReader.
	 * @uml.property name="graphReader"
	 */
	public GraphNetworkReceiver getGraphReader() {
		return graphReceiver;
	}

	/**
	 * Setter of the property <tt>graphReader</tt>
	 * 
	 * @param graphReader
	 *            The graphReader to set.
	 * @uml.property name="graphReader"
	 */
	public void setGraphReader(GraphNetworkReceiver graphReceiver) {
		this.graphReceiver = graphReceiver;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	/**
	 * @uml.property name="config"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.Configuration"
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
	 * @uml.property name="lang"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.Language"
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
	 * @uml.property name="algo"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.Algo"
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
	 * @uml.property name="graphNetworkBuilder"
	 * @uml.associationEnd inverse="iGoMaster:graphNetwork.GraphNetworkBuilder"
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
	 * @uml.property name="eventInfoNetwork"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.EventInfoNetworkWatcher"
	 */
	private EventInfoNetworkWatcher eventInfoNetwork;

	/**
	 * Getter of the property <tt>eventInfoNetwork</tt>
	 * 
	 * @return Returns the eventInfoNetwork.
	 * @uml.property name="eventInfoNetwork"
	 */
	public EventInfoNetworkWatcher getEventInfoNetwork() {
		return eventInfoNetwork;
	}

	/**
	 * Setter of the property <tt>eventInfoNetwork</tt>
	 * 
	 * @param eventInfoNetwork
	 *            The eventInfoNetwork to set.
	 * @uml.property name="eventInfoNetwork"
	 */
	public void setEventInfoNetwork(EventInfoNetworkWatcher eventInfoNetwork) {
		this.eventInfoNetwork = eventInfoNetwork;
	}

	public IGoMaster(IHM ihm, GraphNetworkReceiver graphReceiver, Configuration config, Language lang, Algo algo,
			GraphNetworkBuilder graphNetworkBuilder, EventInfoNetworkWatcher eventInfoNetwork,
			GraphNetworkCostReceiver graphNetworkCostReceiver) {
		super();
		this.ihm = ihm;
		this.graphReceiver = graphReceiver;
		this.config = config;
		this.lang = lang;
		this.algo = algo;
		this.graphNetworkBuilder = graphNetworkBuilder;
		this.eventInfoNetwork = eventInfoNetwork;
		this.graphNetworkCostReceiver = graphNetworkCostReceiver;
	}

	/**
	 * @uml.property name="graphNetworkCostReceiver"
	 * @uml.associationEnd inverse="iGoMaster:iGoMaster.GraphNetworkCostReceiver"
	 */
	private GraphNetworkCostReceiver graphNetworkCostReceiver;

	/**
	 * Getter of the property <tt>graphNetworkCostReceiver</tt>
	 * 
	 * @return Returns the graphNetworkCostReceiver.
	 * @uml.property name="graphNetworkCostReceiver"
	 */
	public GraphNetworkCostReceiver getGraphNetworkCostReceiver() {
		return graphNetworkCostReceiver;
	}

	/**
	 * Setter of the property <tt>graphNetworkCostReceiver</tt>
	 * 
	 * @param graphNetworkCostReceiver
	 *            The graphNetworkCostReceiver to set.
	 * @uml.property name="graphNetworkCostReceiver"
	 */
	public void setGraphNetworkCostReceiver(GraphNetworkCostReceiver graphNetworkCostReceiver) {
		this.graphNetworkCostReceiver = graphNetworkCostReceiver;
	}

	@Override
	public String lg(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}

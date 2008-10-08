package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.AvaibleNetwork;
import iGoMaster.GraphNetworkReceiver;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;

public class GraphNetworkReceiverFolder implements GraphNetworkReceiver {

	public GraphNetworkReceiverFolder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 */
	public boolean updateGraph() {
		return false;
	}

	@Override
	public Thread buildNewGraphNetwork(GraphNetworkBuilder graph, AvaibleNetwork networkChosen)
			throws GraphReceptionException, GraphConstructionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<AvaibleNetwork> getAvaibleNetwork() {
		// TODO Auto-generated method stub
		return null;
	}
}

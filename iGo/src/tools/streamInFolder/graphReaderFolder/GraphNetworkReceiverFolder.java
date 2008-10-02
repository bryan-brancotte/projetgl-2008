package tools.streamInFolder.graphReaderFolder;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.GraphNetworkReceiver;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;

public class GraphNetworkReceiverFolder implements GraphNetworkReceiver {

	/**
	 */
	public boolean updateGraph() {
		return false;
	}

	@Override
	public Thread buildNewGraphNetwork(GraphNetworkBuilder graph) throws GraphReceptionException,
			GraphConstructionException {
		// TODO Auto-generated method stub
		return null;
	}
}

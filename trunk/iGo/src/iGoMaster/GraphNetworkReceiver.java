package iGoMaster;

import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import graphNetwork.GraphNetworkBuilder;

public interface GraphNetworkReceiver {

	public Thread buildNewGraphNetwork(GraphNetworkBuilder graph) throws GraphReceptionException,
			GraphConstructionException;

}

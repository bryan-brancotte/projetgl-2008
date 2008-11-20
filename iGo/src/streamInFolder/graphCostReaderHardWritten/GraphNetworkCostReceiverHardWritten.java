package streamInFolder.graphCostReaderHardWritten;

import iGoMaster.GraphNetworkCostReceiver;
import graphNetwork.KindRouteReader;

/**
 * 
 * @author iGo
 */
public class GraphNetworkCostReceiverHardWritten implements GraphNetworkCostReceiver {

	@Override
	public float getCost(KindRouteReader to) {
		if (KindRouteReader.getKindFromString("Train") == to)
			return 5F;
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCost(KindRouteReader from, KindRouteReader to) {
		// TODO Auto-generated method stub
		return 0;
	}

}

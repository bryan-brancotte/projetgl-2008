package streamInFolder.graphCostReaderHardWritten;

import graphNetwork.KindRoute;
import iGoMaster.GraphNetworkCostReceiver;

/**
 * 
 * @author iGo
 */
public class GraphNetworkCostReceiverHardWritten implements GraphNetworkCostReceiver {

	@Override
	public float getCost(KindRoute to) {
		if (KindRoute.getKindFromString("Train") == to)
			return 5F;
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCost(KindRoute from, KindRoute to) {
		// TODO Auto-generated method stub
		return 0;
	}

}

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
		else if (KindRoute.getKindFromString("Regional Rail") == to)
			return 5F;
		else if (KindRoute.getKindFromString("Metro") == to)
			return 2F;
		else if (KindRoute.getKindFromString("Trolley") == to)
			return 2F;
		else if (KindRoute.getKindFromString("High Speed Rail") == to)
			return 5F;
		return 0;
	}

	@Override
	public float getCost(KindRoute from, KindRoute to) {
		// TODO Auto-generated method stub
		return 0;
	}

}

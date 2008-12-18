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
		if (KindRoute.getKindFromString("Train") == from || KindRoute.getKindFromString("Regional Rail") == from
				|| KindRoute.getKindFromString("High Speed Rail") == from) {
			return (getCost(from) + 1F);
		}
		else if (KindRoute.getKindFromString("Metro") == from || KindRoute.getKindFromString("Trolley") == from) {
			if (KindRoute.getKindFromString("Train") == to || KindRoute.getKindFromString("Regional Rail") == to
					|| KindRoute.getKindFromString("High Speed Rail") == to) {
				return (getCost(from) + 5F);
			}
			else if (KindRoute.getKindFromString("Metro") == to || KindRoute.getKindFromString("Trolley") == to) { return (getCost(from) + 0.75F); }
		}
		return 0;
	}
}

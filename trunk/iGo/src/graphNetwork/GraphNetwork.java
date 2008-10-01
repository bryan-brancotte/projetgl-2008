package graphNetwork;

import java.util.Collection;

public class GraphNetwork implements GraphNetworkBuilder {

	@Override
	public void endBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<RouteR> getRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ServiceR> getServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<StationR> getStations() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @uml.property  name="stations"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="graphNetwork:graphNetwork.Station"
	 */
	private Collection stations;


	public Collection<Station> getStationsW() {
		// TODO Auto-generated method stub
		return null;
	}
}

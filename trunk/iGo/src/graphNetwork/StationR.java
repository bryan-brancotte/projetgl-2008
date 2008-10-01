package graphNetwork;


import java.util.Collection;

public interface StationR {

	public abstract RouteR getRouteR();

	public abstract ServiceR getServiceR();

	public abstract Collection<InterR> getInterchangeR();

	public abstract String getName();

	public abstract int getId();

}

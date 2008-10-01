package graphNetwork;



public interface StationR {

	public abstract RouteR getRouteR();

	public abstract ServiceR getServiceR();

	public abstract InterR[] getInterchangeR();

	public abstract String getName();

	public abstract int getId();

}

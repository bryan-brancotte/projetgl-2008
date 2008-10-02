package graphNetwork;

public interface StationReader {

	public abstract RouteReader[] getRoutesR();

	public abstract ServiceReader getServiceR();

	public abstract InterReader[] getInterchangeR();

	public abstract String getName();

	public abstract int getId();

}

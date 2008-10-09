package iGoMaster;

public interface EventInfo  {

	/**
	 */
	public abstract int getId();

	/**
	 */
	public abstract void applyInfo();

	/**
	 */
	public abstract String getMessage();

	/**
	 * @return  Returns the kindEventInfoNetwork.
	 * @uml.property  name="kindEventInfoNetwork"
	 * @uml.associationEnd  readOnly="true" inverse="eventInfo:iGoMaster.KindEventInfoNetwork"
	 */
	public abstract KindEventInfoNetwork getKindEventInfoNetwork();

}

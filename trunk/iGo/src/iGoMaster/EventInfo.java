package iGoMaster;

public interface EventInfo extends EventInfo {

	/**
	 */
	public abstract int getId();

	/**
	 */
	public abstract KindEventInfoNetwork getKind();

	/**
	 */
	public abstract void applyInfo();

	/**
	 */
	public abstract String getMessage();

}

package iGoMaster;

import iGoMaster.exception.ImpossibleStartingException;

import java.util.Observable;

public abstract class EventInfoNetwork extends Observable {

	/**
	 */
	public abstract void startWatching() throws ImpossibleStartingException;

	/**
	 */
	public abstract void stopWatching();

	/**
	 */
	public abstract EventInfoNetWorkStatus getStatus() ;

}

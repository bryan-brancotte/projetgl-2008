package streamInFolder.event;

import graphNetwork.GraphNetwork;
import graphNetwork.Station;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

import java.util.Iterator;

/**
 * Evénement relatif a une station sur une ligne
 * 
 */
public class EventInfoStationOnARoute implements EventInfo {
	/**
	 * @uml.property name="id"
	 */
	private int ids;

	/**
	 * @uml.property name="id"
	 */
	private String idr;
	/**
	 * @uml.property name="kindEventInfoNetwork"
	 */
	private KindEventInfoNetwork kindEventInfoNetwork;
	private boolean applied = false;

	/**
	 * @uml.property name="message"
	 */
	private String message = "";

	private int messageId;

	public EventInfoStationOnARoute(int _ids, String _idr, String _message, int _msgId, KindEventInfoNetwork kein) {
		ids = _ids;
		idr = _idr;
		message = _message;
		messageId = _msgId;
		kindEventInfoNetwork = kein;
	}

	@Override
	public void applyInfo(GraphNetwork graph) {
		
		if (!isApplied()) {
			System.out.println("entering into event info station");
			if (kindEventInfoNetwork.equals(KindEventInfoNetwork.PROBLEM)) {
				Iterator<Station> it = graph.getRoute(idr).getStations();
				while(it.hasNext()){
					Station sta = it.next();
					if (sta.getId() == ids) {
						sta.setEnable(false);
					}
				}
			} else if (kindEventInfoNetwork.equals(KindEventInfoNetwork.SOLUTION)) {
				Iterator<Station> it = graph.getRoute(idr).getStations();
				while(it.hasNext()){
					Station sta = it.next();
					if (sta.getId() == ids) {
						sta.setEnable(true);
					}
				}
			}
			this.applied = true;
		}
		// avec des enables true/false --> Penser a approfondir la coherences avec les MSID, pour pb/solution

	}

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public int getIds() {
		return ids;
	}

	/**
	 * Getter of the property <tt>kindEventInfoNetwork</tt>
	 * 
	 * @return Returns the kindEventInfoNetwork.
	 * @uml.property name="kindEventInfoNetwork"
	 */
	@Override
	public KindEventInfoNetwork getKindEventInfoNetwork() {
		return kindEventInfoNetwork;
	}

	/**
	 * Getter of the property <tt>message</tt>
	 * 
	 * @return Returns the message.
	 * @uml.property name="message"
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	public void setIds(int id) {
		this.ids = id;
	}

	/**
	 * Setter of the property <tt>kindEventInfoNetwork</tt>
	 * 
	 * @param kindEventInfoNetwork
	 *            The kindEventInfoNetwork to set.
	 * @uml.property name="kindEventInfoNetwork"
	 */
	public void setKindEventInfoNetwork(KindEventInfoNetwork kindEventInfoNetwork) {
		this.kindEventInfoNetwork = kindEventInfoNetwork;
	}

	/**
	 * Setter of the property <tt>message</tt>
	 * 
	 * @param message
	 *            The message to set.
	 * @uml.property name="message"
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getId() {
		return this.ids;
	}

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}
}

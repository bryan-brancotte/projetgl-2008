package streamInFolder.event;

import graphNetwork.GraphNetwork;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

/**
 * Evenement relatif a une ligne
 * 
 */
public class EventInfoRoute implements EventInfo {
	/**
	 * @uml.property name="id"
	 */
	private int id;

	private boolean applied = false;

	/**
	 * @uml.property name="kindEventInfoNetwork"
	 */
	private KindEventInfoNetwork kindEventInfoNetwork;

	/**
	 * @uml.property name="message"
	 */
	private String message = "";

	private String idr;
	private int messageId;

	public EventInfoRoute(String _idr, String _message, int _msgId, KindEventInfoNetwork kein) {
		idr = _idr;
		message = _message;
		messageId = _msgId;
		kindEventInfoNetwork = kein;
	}

	@Override
	public void applyInfo(GraphNetwork graph) {
		if (!isApplied()) {
			System.out.println("entering into event info route");
			if (kindEventInfoNetwork.equals(KindEventInfoNetwork.PROBLEM)) {
				graph.getRoute(idr).setEnable(false);
			} 
			else if (kindEventInfoNetwork.equals(KindEventInfoNetwork.SOLUTION)) {
				graph.getRoute(idr).setEnable(true);
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
	@Override
	public int getId() {
		return id;
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
	public void setId(int id) {
		this.id = id;
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

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}

}
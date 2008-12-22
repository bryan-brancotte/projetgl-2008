package streamInFolder.event;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

/**
 * Evénement relatif a une station sur une ligne
 * 
 */
public class EventInfoStationOnARoute implements EventInfo {
	/**
	 * Id de la Station
	 */
	private int ids;

	/**
	 * Id de la Route
	 */
	private String idr;
	
	/**
	 * Type de l'evenement (Probleme, solution)
	 */
	private KindEventInfoNetwork kindEventInfoNetwork;
	
	/**
	 * Evenement deja applique ou non
	 */
	private boolean applied = false;

	/**
	 * message de l'evenement
	 */
	private String message = "";

	/**
	 * Id de l'evenement (deux evenement qui se corrigent ont le meme ID)
	 */
	private int messageId;

	/**
	 * Constructeur d'Evenement qui intervient sur une station sur une ligne precise
	 * @param _ids ID de la station
	 * @param _idr ID de la ligne
	 * @param _message Message d'information
	 * @param _msgId ID de l'evenement
	 * @param kein Type de l'evenement (probleme, solution)
	 */
	public EventInfoStationOnARoute(int _ids, String _idr, String _message, int _msgId, KindEventInfoNetwork kein) {
		ids = _ids;
		idr = _idr;
		message = _message;
		messageId = _msgId;
		kindEventInfoNetwork = kein;
	}

	@Override
	public void applyInfo(GraphNetworkBuilder graph) {
		
		if (!isApplied()) {
			System.out.println("entering into event info station");
			if (kindEventInfoNetwork.equals(KindEventInfoNetwork.PROBLEM)) {
				graph.setEnable(idr, ids, false);
			} else if (kindEventInfoNetwork.equals(KindEventInfoNetwork.SOLUTION)) {
				graph.setEnable(idr, ids, true);
			}
			this.applied = true;
		}
		// avec des enables true/false --> Penser a approfondir la coherences avec les MSID, pour pb/solution

	}

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 */
	public int getIds() {
		return ids;
	}

	/**
	 * Getter of the property <tt>kindEventInfoNetwork</tt>
	 * 
	 * @return Returns the kindEventInfoNetwork.
	 */
	@Override
	public KindEventInfoNetwork getKindEventInfoNetwork() {
		return kindEventInfoNetwork;
	}

	/**
	 * Getter of the property <tt>message</tt>
	 * 
	 * @return Returns the message.
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
	 */
	public void setIds(int id) {
		this.ids = id;
	}

	/**
	 * Setter of the property <tt>kindEventInfoNetwork</tt>
	 * 
	 * @param kindEventInfoNetwork
	 *            The kindEventInfoNetwork to set.
	 */
	public void setKindEventInfoNetwork(KindEventInfoNetwork kindEventInfoNetwork) {
		this.kindEventInfoNetwork = kindEventInfoNetwork;
	}

	/**
	 * Setter of the property <tt>message</tt>
	 * 
	 * @param message
	 *            The message to set.
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

package streamInFolder.event;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

/**
 * Evenement relatif a une ligne
 * 
 * @author iGo
 */
public class EventInfoRoute implements EventInfo {

	/**
	 * Id de l'evenement (deux evenement qui se corrigent ont le meme ID)
	 */
	private int id;

	/**
	 * Message appliqué ou non
	 */
	private boolean applied = false;

	/**
	 * Type de l'evenement (Probleme, solution)
	 */
	private KindEventInfoNetwork kindEventInfoNetwork;

	/**
	 * Contenu du message
	 */
	private String message = "";

	/**
	 * Id de la route relative au message
	 */
	private String idr;

	/**
	 * Constructeur d'evenement survenant sur une route
	 * 
	 * @param _idr
	 *            Id de la route
	 * @param _message
	 *            Message en question
	 * @param _msgId
	 *            ID du message
	 * @param kein
	 *            Type de l'evenement
	 */
	public EventInfoRoute(String _idr, String _message, int _msgId, KindEventInfoNetwork kein) {
		idr = _idr;
		message = _message;
		id = _msgId;
		kindEventInfoNetwork = kein;
	}

	/**
	 * @see EventInfo#applyInfo(GraphNetworkBuilder)
	 */
	@Override
	public void applyInfo(GraphNetworkBuilder graph) {
		if (!isApplied()) {
			if (kindEventInfoNetwork.equals(KindEventInfoNetwork.PROBLEM))
				graph.setEnable(idr, false);
			else if (kindEventInfoNetwork.equals(KindEventInfoNetwork.SOLUTION))
				graph.setEnable(idr, true);
			this.applied = true;
		}
		// avec des enables true/false --> Penser a approfondir la coherences
		// avec les MSID, pour pb/solution
	}

	/**
	 * @see EventInfo#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * @see EventInfo#getKindEventInfoNetwork()
	 */
	@Override
	public KindEventInfoNetwork getKindEventInfoNetwork() {
		return kindEventInfoNetwork;
	}

	/**
	 * @see EventInfo#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @see EventInfo#getIdRoute()
	 */
	@Override
	public String getIdRoute() {
		return idr;
	}

	/**
	 * @see EventInfo#getIdStation()
	 */
	@Override
	public int getIdStation() {
		return -1;
	}

	/**
	 * @see EventInfo#isApplied()
	 */
	@Override
	public boolean isApplied() {
		return applied;
	}

}

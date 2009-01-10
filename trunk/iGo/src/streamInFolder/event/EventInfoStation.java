package streamInFolder.event;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

/**
 * Evénement relatif a une station
 * 
 * @author iGo
 */
public class EventInfoStation implements EventInfo {

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
	 * Id de la station ou survient l'evenement
	 */
	private int ids;

	/**
	 * Constructeur d'evenement survenant sur une station
	 * @param _id Id de la station
	 * @param _message Message en question
	 * @param _msgId ID du message
	 * @param kein Type de l'evenement
	 */
	public EventInfoStation(int _id, String _message, int _msgId, KindEventInfoNetwork kein) {
		ids = _id;
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
//			System.out.println("entering into event info station");
			if (kindEventInfoNetwork.equals(KindEventInfoNetwork.PROBLEM)) {
				graph.setEnable(ids, false);
			} else if (kindEventInfoNetwork.equals(KindEventInfoNetwork.SOLUTION)) {
				graph.setEnable(ids, true);
			}
			this.applied = true;
		}
		// avec des enables true/false --> Penser a approfondir la coherences avec les MSID, pour pb/solution

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
		return null;
	}

	/**
	 * @see EventInfo#getIdStation()
	 */
	@Override
	public int getIdStation() {
		return ids;
	}
	
	
	/**
	 * @see EventInfo#isApplied()
	 */
	@Override
	public boolean isApplied() {
		return applied;
	}
}

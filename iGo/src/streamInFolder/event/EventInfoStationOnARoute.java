package streamInFolder.event;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.KindEventInfoNetwork;

/**
 * Evénement relatif a une station sur une ligne
 * 
 * @author iGo
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
	 * Message de l'evenement
	 */
	private String message = "";

	/**
	 * Id de l'evenement (deux evenement qui se corrigent ont le meme ID)
	 */
	private int id;

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
		id = _msgId;
		kindEventInfoNetwork = kein;
	}
	
	/**
	 * @see EventInfo#applyInfo(GraphNetworkBuilder)
	 */
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
